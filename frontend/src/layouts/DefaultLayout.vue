<script setup lang="ts">
import { computed } from 'vue';
import CNNavigationMenu from '../components/ui/cnComponents/CNNavigationMenu.vue';
import UserMenu from '../components/user/UserMenu.vue';
import { getLoginStatus } from '../utils/loginUtil';

const isActive = computed(() => {
  return getLoginStatus();
});
</script>

<template>
  <div class="flex flex-col min-h-screen">
    <div
      class="h-[60px] box-border bg-santaIvory flex justify-between p-8 items-center text-deepGreen sticky top-0 z-10 shadow">
      <router-link to="/">
        <span class="text-2xl font-bold">SANTA</span>
      </router-link>
      <div class="flex items-center gap-10">
        <CNNavigationMenu />
        <UserMenu v-if="isActive" />
        <router-link
          v-if="!isActive"
          to="/login"
          >로그인</router-link
        >
      </div>
    </div>
    <router-view
      class="flex flex-col flex-1 w-full overflow-auto bg-santaWhite"></router-view>
  </div>
</template>
