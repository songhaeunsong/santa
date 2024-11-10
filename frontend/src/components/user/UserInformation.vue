<script setup lang="ts">
import { ref } from 'vue';
import CNProgress from '../ui/cnComponents/CNProgress.vue';
import MountainTab from './userMountain/MountainTab.vue';

const user = ref({
  exp: 30,
  profileUrl: '/santaLogo.png',
  tier: 'BRONZE',
  nickname: '하은',
  email: 'gkdms3469@gmail.com'
});
</script>

<template>
  <div class="w-[100%] px-[30px] py-[50px] bg-santaGreen gap-5 flex">
    <div class="w-[200px] h-[200px] bg-white rounded flex-shrink-0">
      <img
        :src="user.profileUrl"
        alt="user-profile"
        class="object-cover w-full h-full rounded" />
    </div>
    <div
      class="flex flex-col justify-between gap-6 infoDiv text-santaWhite w-[100%]">
      <div>
        <p class="text-2xl font-semibold">{{ user.nickname }}</p>
        <p class="py-1 font-normal">{{ user.email }}</p>
      </div>
      <div class="flex items-center gap-2 w-[100%]">
        <p
          class="px-2 py-1 mr-3 text-xs font-medium bg-santaWhite text-deepGreen rounded-xl">
          {{ user.tier }}
        </p>
        <CNProgress :progress-value="user.exp" />
      </div>
    </div>
  </div>
  <MountainTab />
</template>
