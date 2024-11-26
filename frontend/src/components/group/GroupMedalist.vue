<script setup lang="ts">
import { Medalist } from '../../types/types';

const props = defineProps<{ medalist: Medalist }>();
</script>

<template>
  <div class="relative flex justify-center items-end h-[170px] w-full mt-10">
    <div
      class="silver-podium podium w-[150px] mx-4 rounded-t-lg flex justify-center items-end pb-2 bg-rankSilver"
      style="height: 150px; box-shadow: inset 0 0 25px rgba(0, 0, 0, 0.1)">
      <div
        class="absolute flex items-center gap-2 px-2 py-2 opacity-0 animate-riseContent-2"
        style="bottom: 160px">
        <div class="w-[30px] shrink-0">
          <img
            src="/silver_medal.png"
            alt="은메달" />
        </div>
        <span class="text-sm font-bold text-deepGreen">{{
          props.medalist.second
        }}</span>
      </div>
    </div>

    <div
      class="gold-podium podium w-[180px] rounded-t-lg flex justify-center items-end pb-2 bg-rankGold"
      style="height: 200px; box-shadow: inset 0 0 25px rgba(0, 0, 0, 0.1)">
      <div
        class="absolute flex items-center gap-2 px-2 py-2 opacity-0 animate-riseContent-1"
        style="bottom: 210px">
        <div class="w-[42px] shrink-0">
          <img
            src="/gold_medal.png"
            alt="금메달" />
        </div>
        <span class="text-lg font-bold text-deepGreen">{{
          props.medalist.first
        }}</span>
      </div>
    </div>

    <div
      class="bronze-podium podium w-[150px] mx-4 rounded-t-lg flex justify-center items-end pb-2 bg-rankBronze"
      style="height: 100px; box-shadow: inset 0 0 25px rgba(0, 0, 0, 0.1)">
      <div
        class="absolute flex items-center gap-2 px-2 py-2 opacity-0 animate-riseContent-3"
        style="bottom: 110px">
        <div class="w-[30px] shrink-0">
          <img
            src="/bronze_medal.png"
            alt="동메달" />
        </div>
        <span class="text-sm font-bold text-deepGreen">{{
          props.medalist.third
        }}</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
@keyframes riseContent {
  0% {
    transform: translateY(100%);
    opacity: 0;
  }
  100% {
    transform: translateY(0);
    opacity: 1;
  }
}

.animate-riseContent-1 {
  animation: riseContent 1s ease-in-out forwards;
}

.animate-riseContent-2 {
  animation: riseContent 1s ease-in-out forwards;
  animation-delay: 0.6s;
}

.animate-riseContent-3 {
  animation: riseContent 1s ease-in-out forwards;
  animation-delay: 0.8s;
}
</style>
