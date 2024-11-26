import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';

import DefaultLayout from '../layouts/DefaultLayout.vue';
import HomeView from '../views/HomeView.vue';
import RankView from '../views/RankView.vue';
import LoginView from '../views/LoginView.vue';
import RedirectView from '../views/RedirectView.vue';
import SignupInitView from '../views/SignupInitView.vue';
import SimpleLayout from '../layouts/SimpleLayout.vue';
import GroupView from '../views/GroupView.vue';
import SearchView from '../views/SearchView.vue';
import UserInformationView from '../views/UserInformationView.vue';
import GroupDetailView from '../views/GroupDetailView.vue';
import ContentLayout from '../layouts/ContentLayout.vue';
import { useAuth } from '../hooks/useAuth';
import { getLoginStatus } from '../utils/loginUtil';
import MountainView from '../views/MountainView.vue';
import MountainDetailView from '../views/MountainDetailView.vue';
import MyInformationView from '../views/MyInformationView.vue';
import AIMountainView from '../views/AIMountainView.vue';

const routes: readonly RouteRecordRaw[] = [
  {
    path: '/',
    component: DefaultLayout,
    children: [{ path: '', component: HomeView }],
    meta: { publicPath: true }
  },
  {
    path: '/mountain',
    component: DefaultLayout,
    children: [{ path: '', component: MountainView }],
    meta: { publicPath: false }
  },
  {
    path: '/mountain/ai',
    component: DefaultLayout,
    children: [{ path: '', component: AIMountainView }],
    meta: { publicPath: false }
  },
  {
    path: '/mountain/:id',
    component: DefaultLayout,
    children: [{ path: '', component: MountainDetailView }],
    meta: { publicPath: false }
  },
  {
    path: '/rank',
    component: DefaultLayout,
    children: [{ path: '', component: RankView }],
    meta: { publicPath: false }
  },
  {
    path: '/group',
    component: ContentLayout,
    children: [{ path: '', component: GroupView }],
    meta: { publicPath: false }
  },
  {
    path: '/group/:id',
    component: ContentLayout,
    children: [{ path: '', component: GroupDetailView }],
    meta: { publicPath: false }
  },
  {
    path: '/search',
    component: DefaultLayout,
    children: [{ path: '', component: SearchView }],
    meta: { publicPath: true }
  },
  {
    path: '/login',
    component: SimpleLayout,
    children: [{ path: '', component: LoginView }],
    meta: { publicPath: true }
  },
  {
    path: '/login/init',
    component: SimpleLayout,
    children: [{ path: '', component: SignupInitView }],
    meta: { publicPath: false }
  },
  {
    path: '/oauth/redirect',
    component: SimpleLayout,
    children: [{ path: '', component: RedirectView }],
    meta: { publicPath: true }
  },
  {
    path: '/user',
    component: DefaultLayout,
    children: [{ path: '', component: MyInformationView }],
    meta: { publicPath: false }
  },
  {
    path: '/user/:id',
    component: DefaultLayout,
    children: [{ path: '', component: UserInformationView }],
    meta: { publicPath: false }
  }
];

export const router = createRouter({
  history: createWebHistory(),
  routes
});

const { execute } = useAuth();

router.beforeEach(async (to, _, next) => {
  await execute();
  const publicPath = to.meta.publicPath;

  if (!getLoginStatus() && !publicPath) {
    next({
      path: '/login',
      query: { redirect: to.fullPath }
    });
  } else if (getLoginStatus() && to.path === '/login') {
    next({ path: '/' });
  } else {
    next();
  }
});
