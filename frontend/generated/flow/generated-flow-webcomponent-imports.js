import { injectGlobalWebcomponentCss } from 'Frontend/generated/jar-resources/theme-util.js';

import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/login/theme/lumo/vaadin-login-form.js';
import '@vaadin/vertical-layout/theme/lumo/vaadin-vertical-layout.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import '@vaadin/vaadin-lumo-styles/style.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';

const loadOnDemand = (key) => {
  const pending = [];
  if (key === 'e332324d64c59752f3a338ec92ef976d68242ac82e7a5ae0efe2f0e32b050220') {
    pending.push(import('./chunks/chunk-5cdae0a6170318ca8d1ee1473f5dfd0b729131b8f0c91750a65c3c2a5dd4e1a2.js'));
  }
  if (key === '6b540f4a6b0af8000afae4f014cc43dad2c52e1584eb3bdcd133b6d927f4df9b') {
    pending.push(import('./chunks/chunk-f803df643c07aa37eee57a440b68a91abb3cd3408a72bcc573024c04162131e4.js'));
  }
  if (key === '439480d2bce3b6a44f0d2777da866cb2d0be8a9fda52f500d9a18b5b80f34b76') {
    pending.push(import('./chunks/chunk-f803df643c07aa37eee57a440b68a91abb3cd3408a72bcc573024c04162131e4.js'));
  }
  if (key === '432bc5d36fdfb23dd5d9a0dbabe890b5852010988d3e6205bc3bbd50ea27524e') {
    pending.push(import('./chunks/chunk-2d918c774f92b574593a4eb42c57fbcc9a0d2cb464b91f0bd8e549845ed78b44.js'));
  }
  if (key === '9c9d9563c6a6da7fd0a414cedd13da28ca29d15bf79d82dfcc0880d06b7c6898') {
    pending.push(import('./chunks/chunk-623d5ab6f3efd8d4ee5b28d6678bcc772d3d4f74aef4a1f33625b106d54f060a.js'));
  }
  if (key === '0449c858ef041c1b5546328352c20900b2e08e645e841c73fd4bb5045b9c96fd') {
    pending.push(import('./chunks/chunk-a547a817969d9ad23aac1526b0189acbfae9b4b6d462a2b503158d191668d072.js'));
  }
  if (key === 'fe019590fd99b9ab2365e4de290268c36dfde093370c1ef53dce04301e2e03c1') {
    pending.push(import('./chunks/chunk-5207fdfa1351f0b586eb5a1f2c92c4eb6104b9a66dc8fabf4040b2ab3636c39f.js'));
  }
  if (key === '2da0915e3a2040524d5afb35745fb3c0c534a863960609dc70662e55b4714210') {
    pending.push(import('./chunks/chunk-623d5ab6f3efd8d4ee5b28d6678bcc772d3d4f74aef4a1f33625b106d54f060a.js'));
  }
  if (key === 'dc999231e816a58dff4b91316a84008aa98ed7980a349bd1c46cda62ac783701') {
    pending.push(import('./chunks/chunk-7bb96fe72248b23e65f603cd6937976039ced09d8c9d66a3f2e404b1faf04154.js'));
  }
  if (key === '152bbf42ffe58f29e388190ff9ae21c34634528fd10b19f07ae515c1d0c55757') {
    pending.push(import('./chunks/chunk-599da7e24a3ef77e819d0c90208814567aa03207cc951e202a512f64b43372c5.js'));
  }
  if (key === '02fde9515de7cf986cdfb8b712a96fbb4500824792eb5d8e9a849309c2989615') {
    pending.push(import('./chunks/chunk-94c9b3dea70641390632412f26a9e1a850578b1d814c0dd21da44c46164a22cb.js'));
  }
  if (key === '9d2e45471e46a64c5cc3c376f62d75d618c54b7ab91abea1305fe05cbd937a3e') {
    pending.push(import('./chunks/chunk-f803df643c07aa37eee57a440b68a91abb3cd3408a72bcc573024c04162131e4.js'));
  }
  if (key === '547b901c1456d2d38c92ae170a6447cce1e554bfb9ffb916338c13ab2bbad096') {
    pending.push(import('./chunks/chunk-a37e80880189ccad08e7650b14c1f7ecbe0ba16de649943e1028974c8430e038.js'));
  }
  if (key === '146fea80d8b53581e0201710a295b62e9c956ee8941bb9a02296420746620a73') {
    pending.push(import('./chunks/chunk-a37e80880189ccad08e7650b14c1f7ecbe0ba16de649943e1028974c8430e038.js'));
  }
  if (key === '2cd4213c924c4cb0c1729f93ee2e871148e728fcae93612af883311eaa159197') {
    pending.push(import('./chunks/chunk-7f2c82b9d251fff91665532ef91629b0ed3e98b79222684c5dd35d074ec0f2da.js'));
  }
  if (key === 'd97e30aea2bffe3a0bb054f9c2659be6a8e5c66207b6a31c32ec167c456ed749') {
    pending.push(import('./chunks/chunk-2d918c774f92b574593a4eb42c57fbcc9a0d2cb464b91f0bd8e549845ed78b44.js'));
  }
  if (key === '9c61c5c40b7f481a6b12c15e52ce477a9edcea5dd4a727fa5007395dd7a86d65') {
    pending.push(import('./chunks/chunk-7f2c82b9d251fff91665532ef91629b0ed3e98b79222684c5dd35d074ec0f2da.js'));
  }
  if (key === '5fe55c363c388bbafbc3df0b349bf7d3dcd6b84b8c28d382a4230d641d5e6311') {
    pending.push(import('./chunks/chunk-a547a817969d9ad23aac1526b0189acbfae9b4b6d462a2b503158d191668d072.js'));
  }
  if (key === 'a7a6514e511e8397ce4d7ad78b30bdaa9c19aaaddfaecda130dc915eb325ae94') {
    pending.push(import('./chunks/chunk-a37e80880189ccad08e7650b14c1f7ecbe0ba16de649943e1028974c8430e038.js'));
  }
  if (key === 'd83a5829ff92dffe26f577571782f2089e38c8c683b41c42098ff5a09e1d0e14') {
    pending.push(import('./chunks/chunk-a37e80880189ccad08e7650b14c1f7ecbe0ba16de649943e1028974c8430e038.js'));
  }
  if (key === '9f4fb838ca521a2c418d5304b9a43c82b5d462081984daee814fd871c929b05d') {
    pending.push(import('./chunks/chunk-98dd760aee93335ff8383eaeedd9e487ee798770e1cc5da3338cb0dcc93bdad7.js'));
  }
  if (key === 'e512775480bbc0537b18ea435e9309a60430b13d8971be8543954da57a99607c') {
    pending.push(import('./chunks/chunk-f803df643c07aa37eee57a440b68a91abb3cd3408a72bcc573024c04162131e4.js'));
  }
  if (key === 'a628c7edfd67a14d5b262cf067a417160ac3ca56b3d51d5af1682be84c24fa82') {
    pending.push(import('./chunks/chunk-9848a0f0245a43b0659b2da41cfb097cb06269e14fd337614f7908d1e7c301cc.js'));
  }
  if (key === '72c3cdbd3c05fe970dd6a7d9e2669c49b30a239533284a3f9a55f29c210cf94a') {
    pending.push(import('./chunks/chunk-a37e80880189ccad08e7650b14c1f7ecbe0ba16de649943e1028974c8430e038.js'));
  }
  if (key === '44d02ff52caa5e63c6d2f34abc68688cc655fe3646cf7701971bac751e31cc44') {
    pending.push(import('./chunks/chunk-7bb96fe72248b23e65f603cd6937976039ced09d8c9d66a3f2e404b1faf04154.js'));
  }
  if (key === '2336b331b450e847c6423158dba06ae65b5dc9f24092cca463046d54f30bd255') {
    pending.push(import('./chunks/chunk-f512863ce703862a60e931cdc9d287e00cff68d6496698a1f4afae206d7594da.js'));
  }
  if (key === 'fc6e8765aca5728fff715f546e4f0841d8c3a75952074187f8ed2b4c2ea98d55') {
    pending.push(import('./chunks/chunk-5207fdfa1351f0b586eb5a1f2c92c4eb6104b9a66dc8fabf4040b2ab3636c39f.js'));
  }
  if (key === 'e9827700e4a7dfdcae948882298ce96aa61e3e8e2a230dea9e57050a016c8889') {
    pending.push(import('./chunks/chunk-94c9b3dea70641390632412f26a9e1a850578b1d814c0dd21da44c46164a22cb.js'));
  }
  if (key === '424889b2e783d54b35ec2467bba99a36b04123d528365753b0052f1c65b53deb') {
    pending.push(import('./chunks/chunk-98dd760aee93335ff8383eaeedd9e487ee798770e1cc5da3338cb0dcc93bdad7.js'));
  }
  if (key === '5ca898c17c95305e2aa7d0690f71c356f3618715adb67cb06563f891d31e3d07') {
    pending.push(import('./chunks/chunk-623d5ab6f3efd8d4ee5b28d6678bcc772d3d4f74aef4a1f33625b106d54f060a.js'));
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