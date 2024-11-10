import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';

import DefaultLayout from '../layouts/DefaultLayout.vue';
import HomeView from '../views/HomeView.vue';
import HikeView from '../views/HikeView.vue';
import RankView from '../views/RankView.vue';
import LoginView from '../views/LoginView.vue';
import RedirectView from '../views/RedirectView.vue';
import SignupInitView from '../views/SignupInitView.vue';
import SimpleLayout from '../layouts/SimpleLayout.vue';
import GroupView from '../views/GroupView.vue';
import SearchView from '../views/SearchView.vue';
import UserInformationView from '../views/UserInformationView.vue';

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
    path: '/group',
    component: DefaultLayout,
    children: [{ path: '', component: GroupView }]
  },
  {
    path: '/search',
    component: DefaultLayout,
    children: [{ path: '', component: SearchView }]
  },
  {
    path: '/login',
    component: SimpleLayout,
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
  },
  {
    path: '/user',
    component: DefaultLayout,
    children: [{ path: '', component: UserInformationView }]
  }
];

export const router = createRouter({
  history: createWebHistory(),
  routes
});
