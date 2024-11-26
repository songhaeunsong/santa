<script setup lang="ts">
import { Member } from '../../types/types';
import { computed } from 'vue';

const props = defineProps<{ member: Member; rank: number }>();

const getRankColor = computed(() => {
  switch (props.rank) {
    case 1:
      return 'text-';
    case 2:
      return 'text-gray-400';
    case 3:
      return 'text-amber-600';
    default:
      return 'text-santaGray';
  }
});

const getTierColor = computed(() => {
  switch (props.member.tier.toLowerCase()) {
    case 'bronze':
      return 'from-amber-700 to-amber-500';
    case 'silver':
      return 'from-gray-400 to-gray-300';
    case 'gold':
      return 'from-yellow-500 to-yellow-300';
    case 'platinum':
      return 'from-emerald-500 to-teal-300';
    default:
      return 'from-santaGreen to-santaLightGreen';
  }
});

const expPercentage = computed(() => Math.min(100, props.member.exp));
</script>

<template>
  <router-link
    :to="`/user/${props.member.id}`"
    class="relative block p-4 transition-all duration-300 bg-white rounded-xl hover:shadow-xl hover:scale-[1.02]">
    <div class="flex items-center gap-4">
      <!-- Rank -->
      <div class="flex items-center justify-center w-12 h-12">
        <div
          :class="[
            'flex items-center justify-center w-10 h-10 text-xl font-bold rounded-lg',
            getRankColor,
            props.rank <= 3 ? 'bg-santaIvory' : 'bg-transparent'
          ]">
          {{ props.rank }}
        </div>
      </div>

      <!-- Profile Info -->
      <div class="flex-1">
        <div class="flex items-center gap-3 mb-2">
          <h3 class="text-lg font-bold text-deepGreen">
            {{ props.member.nickname }}
          </h3>
          <div
            class="px-3 py-1 text-sm font-medium text-white rounded-full bg-gradient-to-r"
            :class="getTierColor">
            {{ props.member.tier }}
          </div>
        </div>

        <!-- Experience Bar -->
        <div
          class="relative w-full h-4 overflow-hidden rounded-full bg-santaIvory">
          <div
            class="absolute top-0 left-0 h-full transition-all duration-500 bg-gradient-to-r from-santaGreen to-santaFreshGreen"
            :style="{ width: `${expPercentage}%` }">
            <div class="absolute inset-0 opacity-50 bg-sparkle"></div>
          </div>
          <div class="absolute inset-0 flex items-center justify-end px-3">
            <span class="text-xs font-medium text-deepGreen">
              {{ props.member.exp }}XP
            </span>
          </div>
        </div>
      </div>

      <!-- Hover Effect Indicator -->
      <div
        class="flex items-center self-stretch pl-4 ml-4 border-l border-santaIvory">
        <svg
          class="w-5 h-5 transition-transform duration-300 transform text-santaGray group-hover:translate-x-1"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24">
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M9 5l7 7-7 7" />
        </svg>
      </div>
    </div>
  </router-link>
</template>

<style scoped>
@keyframes sparkle {
  0% {
    background-position: 200% center;
  }
  100% {
    background-position: -200% center;
  }
}

.bg-sparkle {
  background: linear-gradient(
    90deg,
    rgba(255, 255, 255, 0) 0%,
    rgba(255, 255, 255, 0.4) 50%,
    rgba(255, 255, 255, 0) 100%
  );
  background-size: 200% 100%;
  animation: sparkle 2s linear infinite;
}
</style>
