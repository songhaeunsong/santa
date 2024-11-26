<script setup lang="ts">
import { storeToRefs } from 'pinia';
import MountainListItem from './MountainListItem.vue';
import { useMountainStore } from '../../store/mountains';

const { mountains } = storeToRefs(useMountainStore());
</script>

<template>
  <div
    class="w-full overflow-hidden overflow-y-auto lg:h-[75vh] h-[29vh] md:h-[75vh]">
    <div
      class="grid w-full max-h-full gap-4 overflow-y-auto md:grid-cols-2 lg:grid-cols-3">
      <MountainListItem
        v-for="(mountain, index) in mountains"
        :key="index"
        :mountain="mountain" />
    </div>
  </div>
</template>
