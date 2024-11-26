import { createApp } from 'vue';
import './assets/index.css';
import App from './App.vue';
import {
  VueQueryPlugin,
  type VueQueryPluginOptions
} from '@tanstack/vue-query';
import { router } from './router/router';
import { useKakao } from 'vue3-kakao-maps/@utils';
import { createPinia } from 'pinia';

const queryClientOptions: VueQueryPluginOptions = {
  queryClientConfig: {
    defaultOptions: {
      queries: {
        retry: 1,
        refetchOnWindowFocus: false
      }
    }
  }
};

const pinia = createPinia();

const app = createApp(App);
useKakao(`${import.meta.env.VITE_KAKAO_JAVASCRIPT_KEY}`);
app.use(VueQueryPlugin, queryClientOptions);
app.use(pinia);
app.use(router);
app.mount('#app');
