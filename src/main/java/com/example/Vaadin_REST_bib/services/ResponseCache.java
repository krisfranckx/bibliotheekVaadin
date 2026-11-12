package com.example.Vaadin_REST_bib.services;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/** Korte cache voor ongefilterde REST-lijsten; wordt geleegd zodra er iets gewijzigd wordt. */
@Component
public class ResponseCache {
    private static final Duration TTL = Duration.ofSeconds(30);
    // Deze endpoints wijzigen data maar zijn GET-requests.
    private static final Pattern GET_MUTATION = Pattern.compile("/(lenen|reserveren|binnenleveren|vrijgeven|verlengen)(/|$)");

    private record Entry(Object value, Instant loaded) {}

    private final ConcurrentHashMap<String, Entry> entries = new ConcurrentHashMap<>();

    /** Cachet alleen ongefilterde aanvragen; gefilterde gaan altijd rechtstreeks naar de server. */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, String filter, Supplier<T> loader) {
        if (filter != null && !filter.isBlank()) {
            return loader.get();
        }
        Entry entry = entries.get(key);
        if (entry != null && entry.loaded().plus(TTL).isAfter(Instant.now())) {
            return (T) entry.value();
        }
        T value = loader.get();
        entries.put(key, new Entry(value, Instant.now()));
        return value;
    }

    public void clear() {
        entries.clear();
    }

    public ExchangeFilterFunction invalidateOnWrite() {
        return (request, next) -> {
            boolean write = request.method() != HttpMethod.GET
                    || GET_MUTATION.matcher(request.url().getPath()).find();
            return write ? next.exchange(request).doFinally(signal -> clear()) : next.exchange(request);
        };
    }
}
