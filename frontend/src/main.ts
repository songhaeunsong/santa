import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import { VueQueryPlugin, type VueQueryPluginOptions } from '@tanstack/vue-query';

const queryClientOptions: VueQueryPluginOptions = {
  queryClientConfig: {
    defaultOptions: {
      queries: {
        retry: 1,
        refetchOnWindowFocus: false,
      },
    },
  },
};

const app = createApp(App);

app.use(VueQueryPlugin, queryClientOptions);
app.mount('#app');
