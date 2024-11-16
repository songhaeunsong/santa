<script setup lang="ts">
import CNNavigationMenu from '../components/ui/cnComponents/CNNavigationMenu.vue';
import UserMenu from '../components/user/UserMenu.vue';
import { getLoginStatus } from '../utils/loginUtil';

const isActive = getLoginStatus();
</script>

<template>
  <div class="flex flex-col min-h-screen">
    <div
      class="h-[60px] bg-santaIvory flex justify-between p-8 items-center text-deepGreen sticky top-0 z-10 shadow">
      <router-link to="/">
        <span class="text-2xl font-bold">SANTA</span>
      </router-link>
      <div class="flex items-center gap-1">
        <CNNavigationMenu />
        <UserMenu v-if="isActive" />
        <router-link
          v-if="!isActive"
          to="/login"
          class="ml-8"
          >로그인</router-link
        >
      </div>
    </div>
    <div
      class="flex flex-col items-center flex-1 w-full overflow-auto bg-santaIvory">
      <router-view></router-view>
    </div>
  </div>
</template>
