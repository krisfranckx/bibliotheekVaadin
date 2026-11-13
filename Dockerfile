FROM eclipse-temurin:25-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw && ./mvnw -B -Pproduction -DskipTests clean package

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/Vaadin_REST_bib-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5000
CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT:-5000}"]
