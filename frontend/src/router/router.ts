import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';

import DefaultLayout from '../layouts/DefaultLayout.vue';
import HomeView from '../views/HomeView.vue';
import HikeView from '../views/HikeView.vue';
import RankView from '../views/RankView.vue';
import LoginView from '../views/LoginView.vue';
import RedirectView from '../views/RedirectView.vue';
import SignupInitView from '../views/SignupInitView.vue';
import SimpleLayout from '../layouts/SimpleLayout.vue';

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
  },
  {
    path: '/login',
    component: DefaultLayout,
    children: [{ path: '', component: LoginView }]
  },
  {
    path: '/login/init',
    component: SimpleLayout,
    children: [{ path: '', component: SignupInitView }]
  },
  {
    path: '/oauth/redirect',
    component: DefaultLayout,
    children: [{ path: '', component: RedirectView }]
  }
];

export const router = createRouter({
  history: createWebHistory(),
  routes
});
