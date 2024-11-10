import { createApp } from 'vue';
import './assets/index.css';
import App from './App.vue';
import {
  VueQueryPlugin,
  type VueQueryPluginOptions
} from '@tanstack/vue-query';
import { router } from './router/router';
import { useKakao } from 'vue3-kakao-maps/@utils';

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

const app = createApp(App);
useKakao('8df254404729491b295c2b935068e207');
app.use(VueQueryPlugin, queryClientOptions);
app.use(router);
app.mount('#app');
