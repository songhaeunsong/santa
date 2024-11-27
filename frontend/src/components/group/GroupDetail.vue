<script setup lang="ts">
import { useGetgroupDetail } from '../../api/group/group';
import { onMounted, ref } from 'vue';
import GroupAppointment from './appointment/GroupAppointment.vue';
import BaseLoading from '../BaseLoading.vue';
import GroupJoinModal from './GroupJoinModal.vue';

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
    class="w-full min-h-screen">
    <div
      class="flex flex-col w-full max-w-6xl gap-6 px-4 py-8 mx-auto lg:px-6 md:px-4 sm:px-4">
      <div
        :class="[
          'transition-all duration-700 transform',
          isLoaded ? 'translate-y-0 opacity-100' : 'translate-y-10 opacity-0'
        ]"
        class="flex items-center justify-between px-6 py-4 shadow-lg bg-santaWhite rounded-xl">
        <div class="flex items-center gap-4">
          <h1 class="text-2xl font-bold text-deepGreen">
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
        <div class="flex flex-col h-full">
          <div class="flex items-center gap-3 mb-6">
            <div
              class="flex items-center justify-center w-12 h-12 rounded-full bg-santaFreshGreen/10">
              <svg
                class="w-6 h-6 text-santaFreshGreen"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="2">
                <path
                  d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253" />
              </svg>
            </div>
            <h2 class="text-xl font-bold text-santaGreen">소개</h2>
          </div>
          <p class="flex-grow text-lg leading-relaxed text-santaGray">
            {{ groupDetail.description }}
          </p>
        </div>

        <div class="grid grid-cols-3 gap-5">
          <div class="transition-all duration-300 bg-white">
            <div class="flex flex-col h-full">
              <div class="flex items-center gap-3 mb-6">
                <div
                  class="flex items-center justify-center w-12 h-12 rounded-full bg-santaFreshGreen/10">
                  <svg
                    class="w-6 h-6 text-santaFreshGreen"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    stroke-width="2">
                    <path
                      d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z" />
                  </svg>
                </div>
                <h2 class="text-xl font-bold text-santaGreen">그룹장</h2>
              </div>
              <div class="flex items-center flex-grow gap-4">
                <div
                  class="flex items-center gap-4 p-4 bg-santaFreshGreen/5 rounded-xl">
                  <div>
                    <div class="text-lg font-bold text-santaGreen">
                      {{ groupDetail.adminName }}
                    </div>
                    <div class="text-sm text-santaGray">그룹 리더</div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="flex flex-col h-full">
            <div class="flex items-center justify-between mb-6">
              <div class="flex items-center gap-3">
                <div
                  class="flex items-center justify-center w-12 h-12 rounded-full bg-santaFreshGreen/10">
                  <svg
                    class="w-6 h-6 text-santaFreshGreen"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    stroke-width="2">
                    <path
                      d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
                  </svg>
                </div>
                <div class="flex items-center gap-3">
                  <h2 class="text-xl font-bold text-santaGreen">멤버</h2>
                  <span
                    class="px-3 py-1 font-bold rounded-full bg-santaFreshGreen/10 text-santaFreshGreen">
                    {{ groupDetail.countOfMembers }}명
                  </span>
                </div>
              </div>
            </div>
            <div class="flex flex-wrap gap-2">
              <div
                v-for="participant in groupDetail.participants"
                :key="participant.memberId"
                class="px-2 py-1 border bg-santaLightGreen/5 rounded-full text-santaGreen hover:bg-santaFreshGreen/10 hover:-translate-y-0.5 transition-all duration-300 cursor-pointer">
                {{ participant.nickname }}
              </div>
            </div>
          </div>
        </div>

        <div class="flex flex-col h-full">
          <div class="flex items-center gap-3 mb-6">
            <div
              class="flex items-center justify-center w-12 h-12 rounded-full bg-santaFreshGreen/10">
              <svg
                class="w-6 h-6 text-santaFreshGreen"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="2">
                <path d="M13 10V3L4 14h7v7l9-11h-7z" />
              </svg>
            </div>
            <h2 class="text-xl font-bold text-santaGreen">경험치</h2>
          </div>
          <div class="flex-grow">
            <div class="flex items-center justify-between mb-4">
              <span class="text-3xl font-bold text-santaFreshGreen">{{
                groupDetail.exp
              }}</span>
              <span class="text-lg font-medium text-santaGray">EXP</span>
            </div>
            <div
              class="w-full h-3 overflow-hidden rounded-full bg-santaLightGreen/20">
              <div
                class="h-full transition-all duration-1000 rounded-full bg-gradient-to-r from-santaGreen to-santaFreshGreen"
                :style="{ width: `${(groupDetail.exp % 1000) / 10}%` }"></div>
            </div>
          </div>
        </div>

        <div class="pt-6 border-t border-santaLightGray/30">
          <h2 class="text-xl font-bold text-santaGreen">등산 약속</h2>

          <GroupAppointment
            :group-detail="groupDetail"
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
