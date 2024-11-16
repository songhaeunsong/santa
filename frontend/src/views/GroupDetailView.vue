<script setup lang="ts">
import { useRoute } from 'vue-router';
import GroupDetail from '../components/group/GroupDetail.vue';
const route = useRoute();
const groupId = route.params.id as string;
</script>

<template>
  <div
    class="w-[100%] px-[30px] lg:px-[300px] md:px-[100px] sm:px-[30px] py-[30px] flex flex-col justify-center items-center">
    <GroupDetail :id="groupId" />
  </div>
</template>
