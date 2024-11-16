<script setup lang="ts">
import CNProgress from '../ui/cnComponents/CNProgress.vue';
import MountainTab from './userMountain/MountainTab.vue';
import { useGetMemberImpormation } from '../../api/member/information';

const { data: memberInformation, isError } = useGetMemberImpormation();
</script>

<template>
  <div
    v-if="!isError && memberInformation"
    class="w-[100%] px-[200px] py-[100px] bg-santaBeige gap-8 flex">
    <div class="w-[200px] h-[200px] bg-white rounded-[50%] flex-shrink-0">
      <img
        :src="memberInformation.profileUrl"
        alt="user-profile"
        class="object-cover w-full h-full rounded-[50%]" />
    </div>
    <div
      class="flex flex-col justify-between gap-6 infoDiv text-santaGreen w-[100%] py-8">
      <div>
        <p class="text-3xl font-bold">{{ memberInformation.nickname }}</p>
        <p class="py-1 font-normal">{{ memberInformation.email }}</p>
      </div>
      <div class="flex items-center gap-2 w-[100%]">
        <p
          class="px-2 py-1 mr-3 text-xs font-medium bg-santaWhite text-santaGreen rounded-xl">
          {{ memberInformation.tier }}
        </p>
        <CNProgress :progress-value="memberInformation.exp" />
      </div>
    </div>
  </div>
  <MountainTab />
</template>
