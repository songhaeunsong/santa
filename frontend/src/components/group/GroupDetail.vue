<script setup lang="ts">
import { useGetgroupDetail } from '../../api/group/group';

import GroupMemberLink from './GroupMemberLink.vue';
import GroupAppointment from './appointment/GroupAppointment.vue';
import BaseLoading from '../BaseLoading.vue';
import BaseTag from '../BaseTag.vue';
import GroupJoinModal from './GroupJoinModal.vue';

const props = defineProps<{ id: string }>();
const { data: groupDetail, isError } = useGetgroupDetail(props.id);
</script>

<template>
  <div
    v-if="!isError && groupDetail"
    class="w-[100%] flex flex-col gap-2 lg:px-6 md:px-4 sm:px-0">
    <div class="flex items-center justify-between py-2">
      <div class="text-2xl font-bold text-santaGreen">
        {{ groupDetail.name }}
      </div>
      <GroupJoinModal
        v-if="!groupDetail.isMember"
        :group-name="groupDetail.name"
        :group-id="groupDetail.id" />
      <div v-else></div>
    </div>
    <ul
      class="w-[100%] lg:p-6 p-4 py-6 flex bg-white flex-col rounded-[5px] text-santaGray shadow-lg shadow-gray-300/40 gap-5">
      <li class="flex items-center gap-3">
        <BaseTag tag-label="소개" />
        <span>{{ groupDetail.description }}</span>
      </li>
      <li class="flex items-center gap-3">
        <BaseTag tag-label="그룹장" />
        <GroupMemberLink
          :id="groupDetail.adminId"
          :participant="groupDetail.adminName" />
      </li>
      <li class="flex items-start gap-3">
        <BaseTag tag-label="멤버" />
        <div>
          <div class="mb-2 font-bold z-1">
            {{ groupDetail.countOfMembers }} 명
          </div>
          <div class="flex flex-wrap gap-2 z-1 text-santaGray">
            <GroupMemberLink
              v-for="participant in groupDetail.participants"
              :id="participant.memberId"
              :participant="participant.nickname" />
          </div>
        </div>
      </li>

      <li class="flex items-center gap-3">
        <BaseTag tag-label="경험치" />
        <div class="font-bold z-1">
          {{ groupDetail.exp }}
        </div>
      </li>
      <li class="flex items-center gap-1 mt-10">
        <BaseTag tag-label="등산 약속" />
      </li>
      <GroupAppointment
        :group-id="groupDetail.id"
        date="2024-11-30" />
    </ul>
  </div>
  <BaseLoading v-else />
</template>
