import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';

import HomeView from '../components/HomeView.vue';
import HikeView from '../components/HikeView.vue';
import RankView from '../components/RankView.vue';
import DefaultLayout from '../layouts/DefaultLayout.vue';

const routes: readonly RouteRecordRaw[] = [
  {
    path: '/',
    component: DefaultLayout,
    children: [{ path: '', component: HomeView }]
  },
  {
    path: '/hike',
    component: DefaultLayout,
    children: [{ path: '', component: HikeView }]
  },
  {
    path: '/rank',
    component: DefaultLayout,
    children: [{ path: '', component: RankView }]
  }
];

export const router = createRouter({
  history: createWebHistory(),
  routes
});
