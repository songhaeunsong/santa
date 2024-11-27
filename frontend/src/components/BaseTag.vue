<script setup lang="ts">
const props = defineProps<{ tagLabel: string }>();
</script>

<template>
  <div
    class="py-1 px-2 text-xs bg-santaLightGreen text-white font-semibold rounded-[7px] text-center">
    {{ props.tagLabel }}
  </div>
</template>
