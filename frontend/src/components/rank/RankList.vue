<script setup lang="ts">
import { useGetIndividualRank } from '../../api/rank/rank';
import RankListItem from './RankListItem.vue';

const { data: individualRankData, isError } = useGetIndividualRank();
</script>

<template>
  <ul
    v-if="!isError && individualRankData"
    class="w-full p-6 py-6 flex flex-col rounded-[5px] text-santaBlack animate-fadeIn">
    <li
      v-for="(member, index) in individualRankData.rank"
      :key="member.id">
      <RankListItem
        :member="member"
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
