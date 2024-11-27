<script setup lang="ts">
import { computed, ref, watch } from 'vue';
import BaseLoading from '../components/BaseLoading.vue';
import GroupRegisterModal from '../components/group/GroupRegisterModal.vue';
import { useGetGroupRank } from '../api/rank/rank';
import GroupMedalist from '../components/group/GroupMedalist.vue';
import GroupList from '../components/group/GroupList.vue';

const { data: groupData, isError, isLoading } = useGetGroupRank();

const medalist = {
  first: computed(() => groupData.value?.rank?.[0]?.name || ''),
  second: computed(() => groupData.value?.rank?.[1]?.name || ''),
  third: computed(() => groupData.value?.rank?.[2]?.name || '')
};
</script>

<template>
  <BaseLoading v-if="isLoading" />

  <div
    v-else-if="!isError && groupData"
    class="w-[100%] px-[30px] lg:px-[300px] md:px-[100px] sm:px-[30px] py-[30px] flex flex-col justify-center items-center gap-20">
    <div class="self-start">
      <p class="text-2xl font-extrabold text-santaGreen">Group Top 3</p>
      <svg class="w-20 h-2 mt-1 opacity-40 text-santaFreshGreen/50">
        <rect
          width="100%"
          height="100%"
          fill="currentColor" />
      </svg>
    </div>
    <GroupMedalist :medalist="medalist" />
    <div class="w-[100%] flex flex-col gap-2">
      <div class="flex justify-between px-6 pt-12 pb-2">
        <div>
          <p class="text-2xl font-extrabold text-santaGreen">Group Rank</p>
          <svg class="w-20 h-2 mt-1 opacity-40 text-santaFreshGreen/50">
            <rect
              width="100%"
              height="100%"
              fill="currentColor" />
          </svg>
        </div>
        <GroupRegisterModal />
      </div>
      <GroupList />
    </div>
  </div>
</template>
