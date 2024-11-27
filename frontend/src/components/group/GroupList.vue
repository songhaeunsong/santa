<script setup lang="ts">
import { useGetGroupRank } from '../../api/rank/rank';
import GroupListItem from './GroupListItem.vue';

const { data: groupRankData, isError } = useGetGroupRank();
</script>

<template>
  <ul
    v-if="!isError && groupRankData"
    class="w-full p-6 py-6 flex flex-col rounded-[5px] text-santaBlack animate-fadeIn">
    <li
      class="grid grid-cols-[1fr_1.5fr_2fr_1fr_1fr] text-xs text-santaGray justify-items-center px-3 items-center border-b-[0.5px] border-santaGray pb-3 mb-3">
      <div class="flex items-center gap-1">순위</div>
      <div class="flex items-center gap-1">그룹명</div>
      <div></div>
      <div class="flex items-center gap-1">경험치</div>
      <div class="flex items-center gap-1">팀원 수</div>
    </li>
    <li
      v-for="(group, index) in groupRankData.rank"
      :key="group.id">
      <GroupListItem
        :group="group"
        :rank="index + 1" />
    </li>
  </ul>
</template>

<style scoped>
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
.animate-fadeIn {
  animation: fadeIn 1s ease-in-out;
}
</style>
