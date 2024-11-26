<script setup lang="ts">
import { useGetgroupDetail } from '../../api/group/group';
import { onMounted, ref } from 'vue';
import GroupMemberLink from './GroupMemberLink.vue';
import GroupAppointment from './appointment/GroupAppointment.vue';
import BaseLoading from '../BaseLoading.vue';
import BaseTag from '../BaseTag.vue';
import GroupJoinModal from './GroupJoinModal.vue';
import GroupAppointmentCalendar from './appointment/GroupAppointmentCalendar.vue';

const props = defineProps<{ id: string }>();
const { data: groupDetail, isError } = useGetgroupDetail(props.id);
const isLoaded = ref(false);

onMounted(() => {
  setTimeout(() => {
    isLoaded.value = true;
  }, 300);
});
</script>

<template>
  <div
    v-if="!isError && groupDetail"
    class="w-full min-h-screen bg-gradient-to-br from-santaIvory to-santaBeige">
    <div
      class="flex flex-col w-full max-w-6xl gap-6 px-4 py-8 mx-auto lg:px-6 md:px-4 sm:px-4">
      <!-- Header Section -->
      <div
        :class="[
          'transition-all duration-700 transform',
          isLoaded ? 'translate-y-0 opacity-100' : 'translate-y-10 opacity-0'
        ]"
        class="flex items-center justify-between px-6 py-4 shadow-lg bg-santaWhite rounded-xl">
        <div class="flex items-center gap-4">
          <svg
            class="w-8 h-8 text-santaFreshGreen"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor">
            <path
              d="M17 7l-5 5-5-5"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round">
              <animate
                attributeName="d"
                dur="1.5s"
                repeatCount="indefinite"
                values="M17 7l-5 5-5-5;M17 12l-5 5-5-5;M17 7l-5 5-5-5" />
            </path>
          </svg>
          <h1
            class="text-3xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-santaGreen to-santaFreshGreen">
            {{ groupDetail.name }}
          </h1>
        </div>
        <GroupJoinModal
          v-if="!groupDetail.isMember"
          :group-name="groupDetail.name"
          :group-id="groupDetail.id"
          class="transition-transform hover:scale-105" />
      </div>

      <ul
        :class="[
          'transition-all duration-700 delay-100 transform',
          isLoaded ? 'translate-y-0 opacity-100' : 'translate-y-10 opacity-0'
        ]"
        class="relative flex flex-col w-full gap-8 p-8 overflow-hidden shadow-lg bg-santaWhite rounded-xl">
        <div class="absolute top-0 right-0 w-64 h-64 opacity-5">
          <svg
            viewBox="0 0 100 100"
            class="w-full h-full">
            <circle
              cx="50"
              cy="50"
              r="40"
              fill="none"
              stroke="#435147"
              stroke-width="0.5">
              <animate
                attributeName="r"
                from="30"
                to="50"
                dur="3s"
                repeatCount="indefinite" />
              <animate
                attributeName="opacity"
                from="0.5"
                to="0"
                dur="3s"
                repeatCount="indefinite" />
            </circle>
          </svg>
        </div>

        <li class="flex items-center gap-4 group">
          <BaseTag
            tag-label="소개"
            class="transition-transform group-hover:scale-105" />
          <span class="text-lg text-santaGray">{{
            groupDetail.description
          }}</span>
        </li>

        <li class="flex items-center gap-4 group">
          <BaseTag
            tag-label="그룹장"
            class="transition-transform group-hover:scale-105" />
          <GroupMemberLink
            :id="groupDetail.adminId"
            :participant="groupDetail.adminName"
            class="transition-colors hover:text-santaFreshGreen" />
        </li>

        <li class="flex items-start gap-4 group">
          <BaseTag
            tag-label="멤버"
            class="transition-transform group-hover:scale-105" />
          <div class="flex-1">
            <div class="mb-3 text-lg font-bold text-deepGreen">
              {{ groupDetail.countOfMembers }} 명
            </div>
            <div class="flex flex-wrap gap-3">
              <GroupMemberLink
                v-for="participant in groupDetail.participants"
                :key="participant.memberId"
                :id="participant.memberId"
                :participant="participant.nickname"
                class="transition-all hover:text-santaFreshGreen hover:-translate-y-0.5" />
            </div>
          </div>
        </li>

        <li class="flex items-center gap-4 group">
          <BaseTag
            tag-label="경험치"
            class="transition-transform group-hover:scale-105" />
          <div class="text-lg font-bold text-deepGreen">
            {{ groupDetail.exp }}
            <span class="ml-2 text-sm text-santaGray">XP</span>
          </div>
        </li>

        <div class="pt-6 border-t border-santaLightGray/30">
          <li class="flex items-center gap-4 mb-6 group">
            <BaseTag
              tag-label="등산 약속"
              class="transition-transform group-hover:scale-105" />
          </li>
          <GroupAppointmentCalendar
            :group-id="groupDetail.id"
            date="2024-11-30"
            class="mb-6" />
          <GroupAppointment
            :group-id="groupDetail.id"
            date="2024-11-30" />
        </div>
      </ul>
    </div>
  </div>
  <BaseLoading v-else />
</template>

<style scoped>
.group:hover BaseTag {
  transform: scale(1.05);
}

@keyframes float {
  0% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-5px);
  }
  100% {
    transform: translateY(0px);
  }
}

.hover-float:hover {
  animation: float 2s ease-in-out infinite;
}
</style>
