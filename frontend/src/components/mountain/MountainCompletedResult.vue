<script setup lang="ts">
const props = defineProps<{
  isCompleted: boolean;
  exp: number;
  accuracy: number;
}>();
</script>

<template>
  <div v-if="props.isCompleted">
    <p>
      {{ props.exp }}
    </p>
    <span>획득했습니다!</span>
  </div>
  <div v-else-if="!props.isCompleted">
    <div>정확도: {{ accuracy }}</div>
    <div>
      위도 경도 범위에 들지 못했을 경우 0% ~ 고도 95% 이하일 경우 완등 실패로
      간주됩니다.
    </div>
  </div>
</template>

<style scoped></style>
