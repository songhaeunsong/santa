<script setup lang="ts">
import { useRoute } from 'vue-router';
import BaseLoading from '../BaseLoading.vue';
import { useGetMountainRecommend } from '../../api/mountain/recommend';
import AIPathMap from '../map/AIPathMap.vue';

const route = useRoute();
const mountainCode = route.params.id as string;

const difficulty = '쉬움';

const { data: mountainDetailData } = useGetMountainRecommend(
  +mountainCode,
  difficulty
);
</script>
<template>
  <BaseLoading v-if="!mountainDetailData" />
  <div
    v-else-if="mountainDetailData"
    class="bg-[#FFFDF6] p-6 md:p-20">
    <div class="overflow-hidden shadow-lg rounded-xl">
      <AIPathMap
        :path-data="mountainDetailData.geometry.coordinates"></AIPathMap>
    </div>
  </div>
</template>

<style scoped>
@keyframes checkAnimation {
  0% {
    opacity: 0;
    transform: scale(0);
  }
  50% {
    opacity: 0.5;
    transform: scale(1.2);
  }
  100% {
    opacity: 1;
    transform: scale(1);
  }
}

.animate-check {
  animation: checkAnimation 0.3s ease-in-out;
}
</style>
