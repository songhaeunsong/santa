<script setup lang="ts">
import { Group } from '../../types/types';

const props = defineProps<{ group: Group; rank: number }>();
</script>

<template>
  <router-link
    :to="`/group/${props.group.id}`"
    class="relative py-4 shadow-lg shadow-gray-300/40 rounded-[5px] mt-3 text-santaGreen bg-white grid grid-cols-[1fr_1.5fr_2fr_1fr_1fr] justify-items-center items-center px-3 transition-transform duration-300 transform hover:scale-[1.025]">
    <div class="absolute inset-0 rounded-[5px] bg-white z-1"></div>

    <div class="relative font-bold z-1">{{ props.rank }}</div>
    <div class="relative font-bold z-1">{{ props.group.name }}</div>
    <div class="relative z-1">
      <div class="w-full bg-gray-200 rounded-full h-2.5 mt-1">
        <div
          :style="{ width: props.group.exp + '%' }"
          class="bg-blue-500 h-2.5 rounded-full"></div>
      </div>
    </div>
    <div class="relative z-1">
      <span class="text-xs text-gray-500">{{ props.group.exp }}</span>
    </div>
    <div class="relative flex items-center gap-1 pr-3 z-1">
      {{ props.group.countOfMembers }}
    </div>
  </router-link>
</template>
