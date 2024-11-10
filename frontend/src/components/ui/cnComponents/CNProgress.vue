<script setup lang="ts">
import { Progress } from '../progress';

const props = defineProps<{ progressValue: number }>();
</script>

<template>
  <span class="text-xs">0</span>
  <Progress
    :model-value="props.progressValue"
    class="h-6 w-[100%]" />
  <span class="text-xs">100</span>
</template>
