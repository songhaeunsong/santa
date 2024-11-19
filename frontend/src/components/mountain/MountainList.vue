<script setup lang="ts">
import { Mountains } from '../../types/types';
import MountainListItem from './MountainListItem.vue';

const props = defineProps<{ mountainData: Mountains | [] }>();
</script>

<template>
  <div class="overflow-hidden overflow-y-auto lg:h-[75vh] h-[29vh] md:h-[75vh]">
    <div
      v-if="props.mountainData && 'mountains' in props.mountainData"
      class="grid w-full max-h-full gap-4 overflow-y-auto md:grid-cols-2 lg:grid-cols-3">
      <MountainListItem
        v-for="(mountain, index) in props.mountainData.mountains"
        :key="index"
        :mountain="mountain" />
    </div>
  </div>
</template>
