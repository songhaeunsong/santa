<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { usePostCodeApi } from '../api/member/authentication';
import BaseLoading from '../components/BaseLoading.vue';

const route = useRoute();
const code = ref<string>(route.query.code as string);

const postCode = usePostCodeApi();

onMounted(() => {
  if (code.value) {
    postCode(code.value);
  }
});
</script>

<template>
  <BaseLoading />
</template>
