<script setup lang="ts">
import { useGetMemberInformation } from '../../api/member/information';
import CNProgress from '../ui/cnComponents/CNProgress.vue';
import MountainTab from './userMountain/MountainTab.vue';
import { onMounted, ref } from 'vue';

const { data: memberInformation, isError } = useGetMemberInformation();
const isLoaded = ref(false);

onMounted(() => {
  setTimeout(() => {
    isLoaded.value = true;
  }, 300);
});
</script>

<template>
  <div
    v-if="!isError && memberInformation"
    class="min-h-screen w-full px-6 md:px-[200px] py-12 md:py-[100px] bg-gradient-to-br from-santaIvory to-santaBeige">
    <div
      class="flex flex-col gap-12 mx-auto sm:w-[600px] md:w-[680px] lg:w-[950px]">
      <div
        :class="[
          'transition-all duration-700 transform',
          isLoaded ? 'translate-y-0 opacity-100' : 'translate-y-10 opacity-0'
        ]"
        class="relative flex flex-col items-center w-full gap-8 p-8 overflow-hidden shadow-2xl md:flex-row bg-santaWhite rounded-2xl">
        <div class="relative w-[200px] h-[200px] flex-shrink-0 group">
          <div
            class="absolute inset-0 transition-opacity duration-300 rounded-full opacity-0 bg-santaFreshGreen group-hover:opacity-20"></div>
          <div class="absolute inset-4">
            <img
              :src="memberInformation.profileUrl"
              alt="user-profile"
              class="object-cover w-full h-full transition-transform duration-300 rounded-full shadow-lg group-hover:scale-105" />
          </div>
          <svg
            class="absolute top-0 left-0 w-full h-full"
            viewBox="0 0 100 100">
            <defs>
              <linearGradient
                id="profileGradient"
                x1="0%"
                y1="0%"
                x2="100%"
                y2="0%">
                <stop
                  offset="0%"
                  style="stop-color: #04a65d; stop-opacity: 1" />
                <stop
                  offset="100%"
                  style="stop-color: #9ad3ac; stop-opacity: 1" />
              </linearGradient>
            </defs>
            <circle
              cx="50"
              cy="50"
              r="47"
              fill="none"
              stroke="url(#profileGradient)"
              stroke-width="1.5"
              stroke-dasharray="295.3"
              stroke-dashoffset="0"></circle>
          </svg>
        </div>

        <div class="flex flex-col justify-between w-full gap-6 text-deepGreen">
          <div class="space-y-2">
            <h1
              class="text-4xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-santaGreen to-santaFreshGreen">
              {{ memberInformation.nickname }}
            </h1>
            <p class="text-lg font-normal text-santaGray">
              {{ memberInformation.email }}
            </p>
          </div>

          <div class="flex flex-col items-center gap-4 md:flex-row">
            <div
              class="px-6 py-2 text-sm font-medium text-white transition-shadow duration-300 shadow-md bg-gradient-to-r from-santaFreshGreen to-santaLightGreen rounded-xl hover:shadow-lg">
              {{ memberInformation.tier }}
            </div>
            <CNProgress :progress-value="memberInformation.exp" />
          </div>
        </div>
      </div>

      <div
        :class="[
          'transition-all duration-700 delay-300 transform',
          isLoaded ? 'translate-y-0 opacity-100' : 'translate-y-10 opacity-0'
        ]"
        class="w-full">
        <MountainTab />
      </div>
    </div>
  </div>
</template>

<style scoped>
.animate-spin-slow {
  animation: spin 8s linear infinite;
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

/* Hover Effects */
.group:hover .animate-spin-slow {
  animation-duration: 4s;
}
</style>
