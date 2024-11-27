<script setup lang="ts">
import { Progress } from '../progress';

const props = defineProps<{ progressValue: number }>();
</script>

<template>
  <span class="text-xs text-santaLightGray">0</span>
  <Progress
    :model-value="props.progressValue"
    class="h-6 w-[100%] bg-santaIvory" />
  <span class="text-xs text-santaLightGray">100</span>
</template>
