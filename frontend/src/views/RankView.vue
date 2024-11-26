<script setup lang="ts">
import { computed, reactive, ref, watch } from 'vue';
import BaseLoading from '../components/BaseLoading.vue';
import { useGetIndividualRank } from '../api/rank/rank';
import { Medalist } from '../types/types';
import RankMedalist from '../components/rank/RankMedalist.vue';
import RankList from '../components/rank/RankList.vue';

const { data: rankData, isError, isLoading } = useGetIndividualRank();

const medalist = {
  first: computed(() => rankData.value?.rank?.[0]?.nickname || ''),
  second: computed(() => rankData.value?.rank?.[1]?.nickname || ''),
  third: computed(() => rankData.value?.rank?.[2]?.nickname || '')
};
</script>

<template>
  <BaseLoading v-if="isLoading" />

  <div
    v-else-if="!isError && rankData"
    class="w-[100%] px-[30px] lg:px-[300px] md:px-[100px] sm:px-[30px] py-[30px] flex flex-col justify-center items-center gap-20">
    <div class="self-start">
      <p class="text-2xl font-extrabold text-santaGreen">Rank Top 3</p>
      <svg class="w-20 h-2 mt-1 opacity-40 text-santaFreshGreen/50">
        <rect
          width="100%"
          height="100%"
          fill="currentColor" />
      </svg>
    </div>
    <RankMedalist :medalist="medalist" />
    <div class="w-[100%] flex flex-col gap-2">
      <div class="flex px-6 pt-12 pb-2">
        <div>
          <p class="text-2xl font-extrabold text-santaGreen">Rank</p>
          <svg class="w-20 h-2 mt-1 opacity-40 text-santaFreshGreen/50">
            <rect
              width="100%"
              height="100%"
              fill="currentColor" />
          </svg>
        </div>
      </div>
      <RankList />
    </div>
  </div>
</template>
