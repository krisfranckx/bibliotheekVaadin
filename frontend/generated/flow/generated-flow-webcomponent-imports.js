import { injectGlobalWebcomponentCss } from 'Frontend/generated/jar-resources/theme-util.js';

import { injectGlobalCss } from 'Frontend/generated/jar-resources/theme-util.js';

import { css, unsafeCSS, registerStyles } from '@vaadin/vaadin-themable-mixin';
import $cssFromFile_0 from '@vaadin/vaadin-lumo-styles/lumo.css?inline';
import '@vaadin/vertical-layout/src/vaadin-vertical-layout.js';
import '@vaadin/login/src/vaadin-login-form.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import 'Frontend/generated/jar-resources/ReactRouterOutletElement.tsx';
import 'react-router';
import 'react';

injectGlobalWebcomponentCss($cssFromFile_0.toString());
const loadOnDemand = (key) => {
  const pending = [];
  if (key === '2cd4213c924c4cb0c1729f93ee2e871148e728fcae93612af883311eaa159197') {
    pending.push(import('./chunks/chunk-b0c5d3d7b391eaf38598825a78b638f3e41e32dc09eee58e8c2e983d9ef59e48.js'));
  }
  if (key === '2336b331b450e847c6423158dba06ae65b5dc9f24092cca463046d54f30bd255') {
    pending.push(import('./chunks/chunk-40807507cf489eb093c133e086ee848bb336a2477a7a310f3ce9b1e266359b7d.js'));
  }
  if (key === '5fe55c363c388bbafbc3df0b349bf7d3dcd6b84b8c28d382a4230d641d5e6311') {
    pending.push(import('./chunks/chunk-8054940347e3a1b6baa3a53ae9b24f901b7233cc64640d58c58a641a2f578a6f.js'));
  }
  if (key === '72c3cdbd3c05fe970dd6a7d9e2669c49b30a239533284a3f9a55f29c210cf94a') {
    pending.push(import('./chunks/chunk-548ba0e365bb8b18b2b4fb84504d9d8dd45984ac3f6bae52fd9de75091f6fe1d.js'));
  }
  if (key === 'a628c7edfd67a14d5b262cf067a417160ac3ca56b3d51d5af1682be84c24fa82') {
    pending.push(import('./chunks/chunk-b32b06ca327b2053e8cf74e76073a9fcd7779b5cda84942dd80585eb119c065f.js'));
  }
  if (key === 'a7a6514e511e8397ce4d7ad78b30bdaa9c19aaaddfaecda130dc915eb325ae94') {
    pending.push(import('./chunks/chunk-548ba0e365bb8b18b2b4fb84504d9d8dd45984ac3f6bae52fd9de75091f6fe1d.js'));
  }
  if (key === '5ca898c17c95305e2aa7d0690f71c356f3618715adb67cb06563f891d31e3d07') {
    pending.push(import('./chunks/chunk-6f9d4bb1c1c0e1a2421682b2acc3ecda4a0155a6d62b50a38cf2bd4be485d583.js'));
  }
  if (key === 'dc999231e816a58dff4b91316a84008aa98ed7980a349bd1c46cda62ac783701') {
    pending.push(import('./chunks/chunk-1a3769237d3bc223068255e460d86a6360b9886b18eceeb9986adc744e35d305.js'));
  }
  if (key === 'fc6e8765aca5728fff715f546e4f0841d8c3a75952074187f8ed2b4c2ea98d55') {
    pending.push(import('./chunks/chunk-f894adb8b6e8e95f04ea867351fb264bc5407b00c260f13ccf94b8978d3f6df4.js'));
  }
  if (key === 'd83a5829ff92dffe26f577571782f2089e38c8c683b41c42098ff5a09e1d0e14') {
    pending.push(import('./chunks/chunk-548ba0e365bb8b18b2b4fb84504d9d8dd45984ac3f6bae52fd9de75091f6fe1d.js'));
  }
  if (key === '2da0915e3a2040524d5afb35745fb3c0c534a863960609dc70662e55b4714210') {
    pending.push(import('./chunks/chunk-6f9d4bb1c1c0e1a2421682b2acc3ecda4a0155a6d62b50a38cf2bd4be485d583.js'));
  }
  if (key === '9c61c5c40b7f481a6b12c15e52ce477a9edcea5dd4a727fa5007395dd7a86d65') {
    pending.push(import('./chunks/chunk-b0c5d3d7b391eaf38598825a78b638f3e41e32dc09eee58e8c2e983d9ef59e48.js'));
  }
  if (key === 'e9827700e4a7dfdcae948882298ce96aa61e3e8e2a230dea9e57050a016c8889') {
    pending.push(import('./chunks/chunk-57fdcb6e1d714db2898daf519485e7a317d597f726c29c750f1fb616da1a1afc.js'));
  }
  if (key === '432bc5d36fdfb23dd5d9a0dbabe890b5852010988d3e6205bc3bbd50ea27524e') {
    pending.push(import('./chunks/chunk-79c0ca4800e098291c2c933a9184880eb4f48853719b308d98a1b29f8a378d13.js'));
  }
  if (key === 'e332324d64c59752f3a338ec92ef976d68242ac82e7a5ae0efe2f0e32b050220') {
    pending.push(import('./chunks/chunk-cf1c24ab45f0d835f06088422f86223f11a94944bad27b7bb549963c6e0453a8.js'));
  }
  if (key === '6b540f4a6b0af8000afae4f014cc43dad2c52e1584eb3bdcd133b6d927f4df9b') {
    pending.push(import('./chunks/chunk-7df48eae3185c3212d169067c1e45e4d4cbc51d3a83d0534fcc357a41dd584a9.js'));
  }
  if (key === '0449c858ef041c1b5546328352c20900b2e08e645e841c73fd4bb5045b9c96fd') {
    pending.push(import('./chunks/chunk-8054940347e3a1b6baa3a53ae9b24f901b7233cc64640d58c58a641a2f578a6f.js'));
  }
  if (key === '9c9d9563c6a6da7fd0a414cedd13da28ca29d15bf79d82dfcc0880d06b7c6898') {
    pending.push(import('./chunks/chunk-6f9d4bb1c1c0e1a2421682b2acc3ecda4a0155a6d62b50a38cf2bd4be485d583.js'));
  }
  if (key === 'fe019590fd99b9ab2365e4de290268c36dfde093370c1ef53dce04301e2e03c1') {
    pending.push(import('./chunks/chunk-f894adb8b6e8e95f04ea867351fb264bc5407b00c260f13ccf94b8978d3f6df4.js'));
  }
  if (key === '9d2e45471e46a64c5cc3c376f62d75d618c54b7ab91abea1305fe05cbd937a3e') {
    pending.push(import('./chunks/chunk-7df48eae3185c3212d169067c1e45e4d4cbc51d3a83d0534fcc357a41dd584a9.js'));
  }
  if (key === '424889b2e783d54b35ec2467bba99a36b04123d528365753b0052f1c65b53deb') {
    pending.push(import('./chunks/chunk-b9f59165aad1532a89ffe8d60371e78d98784aaf9225890e0feea7302832e109.js'));
  }
  if (key === 'e512775480bbc0537b18ea435e9309a60430b13d8971be8543954da57a99607c') {
    pending.push(import('./chunks/chunk-7df48eae3185c3212d169067c1e45e4d4cbc51d3a83d0534fcc357a41dd584a9.js'));
  }
  if (key === '439480d2bce3b6a44f0d2777da866cb2d0be8a9fda52f500d9a18b5b80f34b76') {
    pending.push(import('./chunks/chunk-7df48eae3185c3212d169067c1e45e4d4cbc51d3a83d0534fcc357a41dd584a9.js'));
  }
  if (key === '44d02ff52caa5e63c6d2f34abc68688cc655fe3646cf7701971bac751e31cc44') {
    pending.push(import('./chunks/chunk-1a3769237d3bc223068255e460d86a6360b9886b18eceeb9986adc744e35d305.js'));
  }
  if (key === '9f4fb838ca521a2c418d5304b9a43c82b5d462081984daee814fd871c929b05d') {
    pending.push(import('./chunks/chunk-b9f59165aad1532a89ffe8d60371e78d98784aaf9225890e0feea7302832e109.js'));
  }
  if (key === '547b901c1456d2d38c92ae170a6447cce1e554bfb9ffb916338c13ab2bbad096') {
    pending.push(import('./chunks/chunk-548ba0e365bb8b18b2b4fb84504d9d8dd45984ac3f6bae52fd9de75091f6fe1d.js'));
  }
  if (key === '146fea80d8b53581e0201710a295b62e9c956ee8941bb9a02296420746620a73') {
    pending.push(import('./chunks/chunk-548ba0e365bb8b18b2b4fb84504d9d8dd45984ac3f6bae52fd9de75091f6fe1d.js'));
  }
  if (key === '02fde9515de7cf986cdfb8b712a96fbb4500824792eb5d8e9a849309c2989615') {
    pending.push(import('./chunks/chunk-57fdcb6e1d714db2898daf519485e7a317d597f726c29c750f1fb616da1a1afc.js'));
  }
  if (key === 'd97e30aea2bffe3a0bb054f9c2659be6a8e5c66207b6a31c32ec167c456ed749') {
    pending.push(import('./chunks/chunk-79c0ca4800e098291c2c933a9184880eb4f48853719b308d98a1b29f8a378d13.js'));
  }
  if (key === '152bbf42ffe58f29e388190ff9ae21c34634528fd10b19f07ae515c1d0c55757') {
    pending.push(import('./chunks/chunk-bbf24041f3e9490071d8277df9382970c8c8ecdab03dcbe83ac53763613d01af.js'));
  }
  if (key === '6a0f96de015e78d077f84eb13d62526054966ad1da7744229f095f73f7bd5f49') {
    pending.push(import('./chunks/chunk-b6689118629b0191588c41807d6e96903920a52dd45d425d6a682f48dcdb0736.js'));
  }
  return Promise.all(pending);
}
window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;
window.Vaadin.Flow.resetFocus = () => {
 let ae=document.activeElement;
 while(ae&&ae.shadowRoot) ae = ae.shadowRoot.activeElement;
 return !ae || ae.blur() || ae.focus() || true;
}