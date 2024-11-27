<script setup lang="ts">
import { ref, watch } from 'vue';
import { useDeleteLiked, usePostLiked } from '../../api/mountain/liked';
import { useGetMountainDetail } from '../../api/mountain/mountain';
import { useRoute } from 'vue-router';
import BaseLoading from '../BaseLoading.vue';
import MountainCompletedModal from './MountainCompletedModal.vue';
import Button from '../ui/button/Button.vue';
import PathMap from '../map/PathMap.vue';

const route = useRoute();
const mountainCode = route.params.id as string;
const isLiked = ref(false);
const isModalOpen = ref(false);

const checkBox = ref({
  isAttractionChecked: false,
  isAccommodationChecked: false,
  isRestaurantChecked: false
});

const difficulties = [
  { color: '#6DAD2C', level: '하', description: '초보자도 도전 가능한 등산로' },
  { color: '#ffac30', level: '중', description: '적당한 체력이 필요한 등산로' },
  { color: '#f25050', level: '상', description: '숙련자를 위한 고난도 등산로' }
];

const handleOpenModal = () => {
  isModalOpen.value = true;
};

const handleCloseModal = () => {
  refetch();

  isModalOpen.value = false;
};

const {
  data: mountainDetailData,
  isError,
  isLoading,
  isFetching,
  refetch
} = useGetMountainDetail(mountainCode);

watch(
  () => mountainDetailData.value,
  newData => {
    if (newData) {
      isLiked.value = newData.mountainInfo.isLiked || false;
    }
  },
  { immediate: true }
);

const { mutate: postLiked } = usePostLiked();
const { mutate: deleteLiked } = useDeleteLiked();

const handleLiked = () => {
  isLiked.value = true;
  postLiked(+mountainCode, {
    onError: () => {
      isLiked.value = false;
    }
  });
};
const handleCancelLiked = () => {
  isLiked.value = false;
  deleteLiked(+mountainCode, {
    onError: () => {
      isLiked.value = false;
    }
  });
};
</script>
<template>
  <BaseLoading v-if="isLoading || isFetching" />
  <div
    v-else-if="!isError && mountainDetailData"
    class="bg-[#FFFDF6] p-6 md:p-20">
    <div class="relative flex items-start justify-between mb-8">
      <div class="flex flex-col gap-2">
        <h1 class="mb-4 text-3xl font-bold text-deepGreen">
          {{ mountainDetailData.info.mountainName }}
        </h1>
        <div class="flex items-center gap-2 text-santaGreen">
          <svg
            width="20"
            height="20"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2">
            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
            <polyline points="22 4 12 14.01 9 11.01"></polyline>
          </svg>
          <span
            >위치:
            {{
              `${mountainDetailData.mountainInfo.province} ${mountainDetailData.mountainInfo.city}`
            }}</span
          >
        </div>
        <div class="flex items-center gap-2 text-santaGreen">
          <svg
            width="20"
            height="20"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2">
            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
            <polyline points="22 4 12 14.01 9 11.01"></polyline>
          </svg>
          <span>고도: {{ mountainDetailData.mountainInfo.height }}m</span>
        </div>
      </div>

      <button
        @click="isLiked ? handleCancelLiked() : handleLiked()"
        class="p-2 transition-colors rounded-full hover:bg-santaIvory"
        :class="isLiked ? 'text-santaFreshGreen' : 'text-santaGray'">
        <svg
          width="28"
          height="28"
          viewBox="0 0 24 24"
          fill="currentColor"
          stroke="none">
          <path
            d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z" />
        </svg>
      </button>
    </div>

    <Button
      v-if="!mountainDetailData.isCompletedToday"
      @click="handleOpenModal"
      class="flex font-medium text-md self-end items-center justify-center gap-2 w-[130px] px-5 py-6 text-white transition-all duration-300 rounded-lg bg-santaFreshGreen hover:bg-santaFreshGreen hover:shadow-lg hover:translate-y-[-2px]">
      <svg
        class="w-5 h-5"
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
        stroke-width="2">
        <path
          d="M14 9V5a3 3 0 00-3-3l-4 9v11h11.28a2 2 0 002-1.7l1.38-9a2 2 0 00-2-2.3zM7 22H4a2 2 0 01-2-2v-7a2 2 0 012-2h3" />
      </svg>
      완등 인증
    </Button>

    <Button
      v-if="mountainDetailData.isCompletedToday"
      class="flex font-medium text-sm self-end items-center justify-center gap-2 w-[150px] px-6 py-6 text-santaFreshGreen rounded-full bg-santaLightGray/10 hover:bg-santaLightGray/10 border-2 border-santaFreshGreen shadow-none">
      * * COMPLETED * *
    </Button>

    <MountainCompletedModal
      v-if="isModalOpen"
      :on-close="handleCloseModal"
      :mountain="mountainDetailData.mountainInfo" />

    <div class="flex items-end justify-between">
      <div>
        <div class="flex gap-6 mb-8 rounded-xl">
          <div
            @click="
              checkBox.isAttractionChecked = !checkBox.isAttractionChecked
            "
            class="flex items-center gap-3 px-6 py-3 transition-all duration-300 ease-in-out transform border shadow-sm rounded-xl hover:scale-105 active:scale-95"
            :class="
              checkBox.isAttractionChecked
                ? 'bg-santaGreen text-white shadow-lg border-santaGreen'
                : 'bg-santaWhite text-[#202721] border'
            ">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
              class="w-5 h-5 transition-colors duration-300"
              :class="
                checkBox.isAttractionChecked ? 'text-white' : 'text-[#435E49]'
              ">
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M20 10c0 6-8 12-8 12s-8-6-8-12a8 8 0 1 1 16 0zM12 11a2 2 0 1 0 0-4 2 2 0 0 0 0 4z" />
            </svg>
            <span class="font-medium select-none">관광지</span>
          </div>

          <div
            @click="
              checkBox.isAccommodationChecked = !checkBox.isAccommodationChecked
            "
            class="flex items-center gap-3 px-6 transition-all duration-300 ease-in-out transform border shadow-sm rounded-xl hover:scale-105 active:scale-95"
            :class="
              checkBox.isAccommodationChecked
                ? 'bg-santaGreen text-white shadow-lg border-santaGreen'
                : 'bg-santaWhite text-[#202721] border'
            ">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
              class="w-5 h-5 transition-colors duration-300"
              :class="
                checkBox.isAccommodationChecked
                  ? 'text-white'
                  : 'text-[#435E49]'
              ">
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
            </svg>
            <span class="font-medium select-none">숙소</span>
          </div>

          <div
            @click="
              checkBox.isRestaurantChecked = !checkBox.isRestaurantChecked
            "
            class="flex items-center gap-3 px-6 py-3 transition-all duration-300 ease-in-out transform border shadow-sm rounded-xl hover:scale-105 active:scale-95"
            :class="
              checkBox.isRestaurantChecked
                ? 'bg-santaGreen text-white shadow-lg border-santaGreen'
                : 'bg-santaWhite text-santaBlack border'
            ">
            <svg
              version="1.0"
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 512 512"
              preserveAspectRatio="xMidYMid meet"
              class="w-4 h-4 transition-colors duration-300">
              <g
                transform="translate(0.000000,512.000000) scale(0.100000,-0.100000)"
                :stroke="checkBox.isRestaurantChecked ? '#ffffff' : '#435E49'"
                stroke-width="150"
                :fill="checkBox.isRestaurantChecked ? '#ffffff' : '#435E49'">
                <path
                  d="M587 5056 c-49 -18 -64 -32 -88 -77 -18 -37 -19 -67 -19 -727 0 -656
1 -693 20 -767 44 -171 152 -301 314 -380 l96 -47 0 -1250 c0 -1389 -4 -1305
68 -1442 100 -192 293 -308 512 -309 254 -1 469 151 558 394 l27 74 3 1273 c2
1013 5 1272 15 1272 7 0 46 16 86 35 94 45 214 161 259 250 69 139 66 97 70
862 2 466 0 705 -8 731 -14 53 -74 108 -129 117 -50 8 -123 -24 -155 -68 -21
-28 -21 -42 -26 -730 -6 -785 -1 -741 -78 -820 -71 -74 -95 -81 -289 -85
l-173 -4 0 800 0 799 -23 34 c-32 49 -105 83 -158 74 -53 -9 -115 -64 -129
-113 -6 -24 -10 -320 -10 -815 l0 -779 -167 4 c-142 3 -174 6 -208 23 -50 25
-105 80 -130 130 -19 38 -20 70 -25 740 -5 638 -7 703 -22 732 -34 62 -122 94
-191 69z m1171 -3253 l-3 -1238 -22 -40 c-63 -117 -189 -175 -307 -141 -74 22
-134 70 -168 135 l-28 53 0 1234 0 1234 265 0 265 0 -2 -1237z" />
                <path
                  d="M4393 5045 c-325 -112 -617 -306 -815 -542 -378 -451 -537 -1052
-538 -2024 0 -222 14 -302 72 -412 48 -91 157 -196 248 -240 41 -19 83 -38 93
-41 16 -5 17 -43 17 -617 0 -680 -1 -672 68 -803 100 -192 293 -308 512 -309
254 -1 469 151 558 394 l27 74 2 2210 3 2210 -23 39 c-44 79 -116 98 -224 61z
m-75 -2910 l-3 -1570 -22 -40 c-63 -117 -189 -175 -306 -141 -71 21 -129 65
-164 123 l-28 48 -5 705 -5 705 -24 38 c-28 46 -78 72 -153 80 -99 10 -180 65
-223 152 -19 38 -20 60 -19 365 3 563 55 916 184 1241 137 347 370 613 688
786 l77 42 3 -482 c1 -265 1 -1188 0 -2052z" />
              </g>
            </svg>

            <span class="font-medium select-none">음식점</span>
          </div>
        </div>
      </div>
      <div
        class="w-[400px] flex justify-end items-center h-[20px] px-6 mb-8 mt-16">
        <div class="flex justify-center gap-6">
          <div
            v-for="(diff, index) in difficulties"
            :key="index"
            class="flex items-center gap-3 group">
            <div class="flex items-center gap-2">
              <div class="relative">
                <div
                  class="w-3 h-5 rounded"
                  :style="{ backgroundColor: diff.color }"></div>
              </div>
              <span class="text-sm font-medium text-deepGreen"
                >난이도 {{ diff.level }}</span
              >
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="overflow-hidden shadow-lg rounded-xl">
      <PathMap
        :path-data="mountainDetailData.paths"
        :province="mountainDetailData.mountainInfo.province"
        :city="mountainDetailData.mountainInfo.city"
        :check-box="checkBox"></PathMap>
    </div>
  </div>
</template>

<style scoped>
@keyframes checkAnimation {
  0% {
    opacity: 0;
    transform: scale(0);
  }
  50% {
    opacity: 0.5;
    transform: scale(1.2);
  }
  100% {
    opacity: 1;
    transform: scale(1);
  }
}

.animate-check {
  animation: checkAnimation 0.3s ease-in-out;
}
</style>
