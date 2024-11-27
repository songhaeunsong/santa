<script setup lang="ts">
import { useGetMemberMountainLike } from '../../../api/member/information';

const {
  data: mountainLikeData,
  isError,
  isLoading
} = useGetMemberMountainLike();
</script>

<template>
  <div v-if="isLoading || isError">...</div>
  <div v-if="!isError && mountainLikeData">
    <div class="min-h-screen pb-8">
      <div class="max-w-full px-6 py-8">
        <h2 class="mb-6 text-2xl font-bold text-santaGreen">관심 있는 산</h2>
        <div class="flex flex-wrap max-w-full gap-4 pb-4">
          <router-link
            v-for="mountain in mountainLikeData.mountainLikes"
            :to="`/mountain/${mountain.mountainId}`"
            :key="mountain.id"
            class="flex-shrink-0 w-64 rounded-lg overflow-hidden transition-all duration-300 hover:shadow-lg border border-[#99B39E]/20">
            <div class="relative h-48">
              <img
                :src="
                  mountain.mountainImage.length > 0
                    ? mountain.mountainImage
                    : '/santa_illustration.png'
                "
                :alt="mountain.mountainName"
                class="object-cover w-full h-full transition-transform duration-300 hover:scale-105" />
              <div class="absolute top-3 right-3">
                <button
                  class="p-2 transition-colors rounded-full shadow-sm bg-white/90">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    class="w-5 h-5 text-santaFreshGreen"
                    fill="currentColor"
                    viewBox="0 0 24 24">
                    <path
                      d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z" />
                  </svg>
                </button>
              </div>
            </div>
            <div class="p-4">
              <h3 class="text-lg font-semibold text-[#39593F] mb-2">
                {{ mountain.mountainName }}
              </h3>
            </div>
          </router-link>
        </div>
      </div>

      <div
        v-if="mountainLikeData.mountainLikes.length === 0"
        class="text-center py-12 bg-[#F9F5E7] rounded-lg">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="w-16 h-16 mx-auto text-[#99B39E] mb-4"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor">
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
        </svg>
        <p class="text-[#636C67] text-lg">관심 있는 산이 없습니다.</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.overflow-x-auto {
  -webkit-overflow-scrolling: touch;
  scrollbar-width: none;
  -ms-overflow-style: none;
}

.overflow-x-auto::-webkit-scrollbar {
  display: none;
}
</style>
