<script setup lang="ts">
import { ref, onMounted, watch } from 'vue';
import { usePostMountainCompleted } from '../../api/mountain/mountain';
import { useGeolocation } from '../../hooks/useGeolocation';
import { Mountain } from '../../types/types';

const props = defineProps<{
  onClose: () => void;
  mountain: Mountain;
}>();

const { mutateAsync: postMountainCompleted } = usePostMountainCompleted();
const { location } = useGeolocation();

const showAnimation = ref(true);
const isInMountain = ref(false);

const exp = ref(0);
const accuracy = ref(0);

watch(
  location,
  newLocation => {
    console.log(props.mountain.latitude);

    if (newLocation.lat !== null) isInMountain.value = true;

    if (
      newLocation.lat &&
      (props.mountain.latitude + 0.02 < newLocation.lat ||
        props.mountain.latitude - 0.02 > newLocation.lat)
    ) {
      isInMountain.value = false;
    }
    if (
      newLocation.lng &&
      (props.mountain.longitude + 0.02 < newLocation.lng ||
        props.mountain.longitude - 0.02 > newLocation.lng)
    ) {
      isInMountain.value = false;
    }

    if (isInMountain.value && newLocation.altitude !== null) {
      accuracy.value = Math.min(
        100,
        Math.max(
          0,
          Math.floor((newLocation.altitude / props.mountain.height) * 100)
        )
      );
    }

    // accuracy.value = 96; // 테스트용
    if (accuracy.value >= 95) {
      postMountainCompleted(props.mountain.id).then(data => {
        exp.value = data.exp;
      });
    }
  },
  { deep: true }
);
onMounted(() => {
  setTimeout(() => {
    showAnimation.value = false;
  }, 6000);
});
</script>

<template>
  <div
    class="fixed inset-0 z-50 flex items-center justify-center bg-black/50 backdrop-blur-sm">
    <div class="w-full max-w-lg p-6 mx-4 bg-white rounded-2xl">
      <div
        v-if="showAnimation"
        class="flex flex-col items-center">
        <div class="flex items-center justify-between w-full mb-6">
          <h2 class="text-2xl font-bold text-deepGreen">완등하기</h2>
        </div>
        <div
          class="w-full flex justify-center items-center h-[300px] mb-6 rounded-xl bg-santaLightGray/20">
          <div class="relative flex items-center justify-center">
            <div class="rounded-full w-7 h-7 bg-santaFreshGreen"></div>
            <div
              class="absolute w-8 h-8 rounded-full opacity-50 bg-santaFreshGreen animate-ping"></div>
          </div>
        </div>
        <p class="text-lg font-medium text-center text-santaGreen">
          위치 확인 중...
        </p>
      </div>

      <div
        v-else
        class="flex flex-col items-center pb-9">
        <div class="flex justify-end w-full mb-2">
          <button
            @click="onClose"
            class="flex items-center justify-center w-8 h-8 transition-all duration-300 rounded-full hover:bg-santaIvory group">
            <svg
              class="w-5 h-5 transition-colors duration-300 text-santaGray group-hover:text-deepGreen"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24">
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>

        <div
          v-if="location.lat === null && location.lng === null"
          class="flex flex-col items-center w-full gap-6 py-10">
          <div
            class="flex items-center justify-center w-24 h-24 rounded-full bg-santaIvory">
            <svg
              class="w-14 h-14 text-santaGray"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor">
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
            </svg>
          </div>
          <div class="flex flex-col items-center gap-6">
            <p class="text-2xl font-bold text-santaFreshGreen">
              위치 감지에 실패했습니다.
            </p>
            <span class="text-lg text-santaGray"
              >위치 권한을 허용해주세요!</span
            >
          </div>
        </div>

        <div
          v-else-if="isInMountain && accuracy >= 95"
          class="flex flex-col items-center w-full gap-6 py-10">
          <div class="flex flex-col items-center gap-6">
            <p class="text-4xl font-bold text-santaFreshGreen">
              + {{ exp }} XP
            </p>
            <span class="text-xl text-santaGray">획득했습니다!</span>
          </div>
        </div>

        <div
          v-else-if="!isInMountain"
          class="flex flex-col items-center w-full gap-6">
          <div
            class="flex items-center justify-center w-24 h-24 rounded-full bg-santaIvory">
            <svg
              class="w-14 h-14 text-santaGray"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor">
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
            </svg>
          </div>
          <div class="flex flex-col items-center gap-4">
            <div class="flex flex-col items-center gap-8">
              <div class="mb-1 text-2xl font-bold text-deepGreen">
                정확도: {{ accuracy }}%
              </div>
              <div
                class="px-6 py-4 text-base text-center text-santaGray bg-santaIvory rounded-xl">
                현재 위치가 등산로 범위를 벗어났습니다.<br />
                산 정상 근처로 이동해주세요.
              </div>
            </div>
          </div>
        </div>

        <div
          v-else
          class="flex flex-col items-center w-full gap-6">
          <div
            class="flex items-center justify-center w-24 h-24 rounded-full bg-santaIvory">
            <svg
              class="w-14 h-14 text-santaGreen"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor">
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6" />
            </svg>
          </div>
          <div class="flex flex-col items-center gap-8">
            <div class="text-2xl font-bold text-deepGreen">
              정확도: {{ accuracy }}%
            </div>
            <div
              class="px-6 py-4 text-base text-center text-santaGray bg-santaIvory rounded-xl">
              아직 정상에 도착하지 못했어요.<br />
              조금만 더 올라가 보세요!
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.animate-ping {
  animation: ping 1.5s cubic-bezier(0, 0, 0.2, 1) infinite;
}

@keyframes ping {
  75%,
  100% {
    transform: scale(2);
    opacity: 0;
  }
}

.modal-enter-active,
.modal-leave-active {
  transition: opacity 0.3s ease;
}

.modal-enter-from,
.modal-leave-to {
  opacity: 0;
}
</style>
