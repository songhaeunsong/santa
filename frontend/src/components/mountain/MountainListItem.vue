<script setup lang="ts">
import { Mountain } from '../../types/types';

const props = defineProps<{ mountain: Mountain }>();
</script>

<template>
  <router-link
    :to="`/mountain/${props.mountain.id}`"
    class="overflow-hidden transition-shadow border rounded-lg hover:shadow-lg">
    <div class="relative">
      <img
        :src="props.mountain.image || '/santa_illustration.png'"
        :alt="props.mountain.name"
        class="object-cover w-full h-48" />
      <div
        class="absolute p-2 rounded-full top-2 right-2 bg-white/90"
        :class="
          props.mountain.isLiked ? 'text-santaFreshGreen' : 'text-santaGray'
        ">
        <svg
          width="20"
          height="20"
          viewBox="0 0 24 24"
          fill="currentColor"
          stroke="none">
          <path
            d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z" />
        </svg>
      </div>
    </div>

    <div class="p-4">
      <h3 class="mb-2 text-lg font-semibold">{{ props.mountain.name }}</h3>
      <div class="flex items-center text-gray-600">
        <div class="flex items-center gap-2 text-[#636C67]">
          <svg
            width="20"
            height="20"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2">
            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
            <polyline points="22 4 12 14.01 9 11.01"></polyline>
          </svg>
          <span>고도: {{ props.mountain.height }}m</span>
        </div>
      </div>
    </div>
  </router-link>
</template>
