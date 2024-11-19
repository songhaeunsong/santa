<script setup lang="ts">
import Map from '../components/map/map.vue';
import MountainLocationFilter from '../components/mountain/MountainLocationFilter.vue';
</script>

<template>
  <div
    class="grid flex-1 grid-flow-row grid-cols-1 grid-rows-2 md:grid-rows-1 md:grid-cols-2">
    <div
      class="flex flex-col items-center row-start-2 gap-10 p-6 md:col-start-1 md:row-auto">
      <MountainLocationFilter />
    </div>
    <Map class="row-start-1 md:col-start-2 md:row-auto"></Map>
  </div>
</template>
