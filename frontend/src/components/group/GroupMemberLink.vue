<script setup lang="ts">
const props = defineProps<{ participant: string; id: number }>();
</script>

<template>
  <router-link
    :to="`/user/${id}`"
    class="font-bold text-xs flex justify-center items-center px-2 py-[1.5px] border rounded-[30px] border-santaGray hover:border-santaLightGray hover:text-santaLightGray">
    {{ props.participant }}
  </router-link>
</template>
