<script setup lang="ts">
import { Group } from '../../types/types';

const props = defineProps<{ group: Group }>();
</script>

<template>
  <router-link
    :to="`/group/${props.group.id}`"
    class="relative py-6 shadow-lg shadow-gray-300/40 rounded-[5px] mt-3 text-santaGreen bg-white grid grid-cols-[1.5fr_2fr_1fr_1fr] justify-items-center items-center px-3">
    <div class="absolute inset-0 rounded-[5px] bg-white z-1"></div>

    <div class="relative font-bold z-1">{{ props.group.name }}</div>
    <div class="relative z-1"></div>
    <div class="relative z-1">{{ props.group.exp }}</div>
    <div class="relative pr-3 z-1">
      {{ props.group.countOfMembers }}
    </div>
  </router-link>
</template>

<style scoped></style>
