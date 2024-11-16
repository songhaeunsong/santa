<script setup>
import { useGetgroupAll } from '../../api/group/group';
import BaseLoading from '../BaseLoading.vue';
import GroupListItem from './GroupListItem.vue';

const { data: groupData, isError } = useGetgroupAll();
</script>

<template>
  <ul
    v-if="!isError && groupData"
    class="w-[100%] p-6 py-6 flex flex-col rounded-[5px] text-santaBlack">
    <li
      class="grid grid-cols-[1.5fr_2fr_1fr_1fr] text-xs text-santaLightGreen justify-items-center px-3 items-center">
      <div>그룹명</div>
      <div></div>
      <div>경험치</div>
      <div>팀원 수</div>
    </li>
    <li v-for="group in groupData.groups">
      <GroupListItem :group="group" />
    </li>
  </ul>
  <BaseLoading v-else />
</template>
