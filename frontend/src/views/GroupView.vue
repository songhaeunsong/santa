<script setup lang="ts">
import GroupList from '../components/group/GroupList.vue';
import GroupRegisterModal from '../components/group/GroupRegisterModal.vue';
</script>

<template>
  <div
    class="w-[100%] px-[30px] lg:px-[300px] md:px-[100px] sm:px-[30px] py-[30px] flex flex-col justify-center items-center">
    <div class="w-[100%] flex flex-col gap-2">
      <div class="flex justify-between p-6 py-2">
        <p class="text-2xl font-bold text-santaGreen">Group</p>
        <GroupRegisterModal />
      </div>
      <GroupList />
    </div>
  </div>
</template>
