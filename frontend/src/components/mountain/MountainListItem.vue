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
      <div class="absolute p-2 rounded-full top-2 right-2 bg-white/90">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="w-5 h-5 text-blue-600"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor">
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M12 2l9 21H3L12 2z" />
        </svg>
      </div>
    </div>

    <div class="p-4">
      <h3 class="mb-2 text-lg font-semibold">{{ props.mountain.name }}</h3>
      <div class="flex items-center text-gray-600">
        <span>고도: {{ props.mountain.height }}</span>
      </div>
    </div>
  </router-link>
</template>
