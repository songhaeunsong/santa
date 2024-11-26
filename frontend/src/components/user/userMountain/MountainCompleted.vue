<script setup>
import { computed } from 'vue';
import { useGetMountainCompleted } from '../../../api/mountain/mountain';

const { data: mountainCompletedData, isError } = useGetMountainCompleted();

const hasCompletedMountains = computed(
  () =>
    mountainCompletedData?.completes &&
    mountainCompletedData.completes.length > 0
);
</script>

<template v-if="!isError">
  <div class="w-full p-6 bg-stone-50 rounded-2xl">
    <div class="flex items-center justify-start gap-3 mb-8">
      <div class="flex items-center gap-2">
        <h2 class="text-2xl font-bold text-santaGreen">나의 완등 도장</h2>
      </div>
      <div
        class="flex items-center justify-center w-8 h-8 font-bold text-white rounded-full text-md bg-santaFreshGreen">
        {{ mountainCompletedData?.completes.length || 0 }}
      </div>
    </div>

    <div
      v-if="!mountainCompletedData?.completes"
      class="flex flex-col items-center justify-center py-12">
      <!-- 빈 상태 도장 -->
      <div class="relative w-32 h-32 mb-6 opacity-40">
        <div class="relative aspect-square">
          <div
            class="absolute inset-0 flex flex-col items-center justify-center border-2 rounded-full border-santaGreen/30">
            <svg
              viewBox="0 0 100 100"
              class="absolute inset-0 w-full h-full opacity-50">
              <circle
                cx="50"
                cy="50"
                r="48"
                fill="none"
                stroke="currentColor"
                stroke-width="2"
                class="text-deepGreen/30" />
              <circle
                cx="50"
                cy="50"
                r="45"
                fill="none"
                stroke="currentColor"
                stroke-width="1"
                class="text-deepGreen/20" />
            </svg>

            <svg
              version="1.0"
              xmlns="http://www.w3.org/2000/svg"
              width="60px"
              height="60px"
              viewBox="0 0 512.000000 512.000000"
              preserveAspectRatio="xMidYMid meet">
              <g
                transform="translate(0.000000,512.000000) scale(0.100000,-0.100000)"
                fill="#4d6d57"
                stroke="1"
                class="opacity-30">
                <path
                  d="M2370 3943 c-16 -16 -179 -264 -361 -553 -182 -289 -334 -528 -338 -533 -4 -4 -68 81 -142 189 -82 120 -144 201 -161 210 -37 19 -97 18 -131 -4 -17 -10 -148 -193 -325 -452 l-297 -435 -265 -5 c-245 -5 -267 -7 -292 -25 -55 -41 -73 -117 -39 -168 38 -58 52 -62 239 -67 l172 -5 -60 -89 c-66 -97 -73 -130 -44 -192 29 -62 117 -84 179 -45 20 12 163 213 414 580 211 310 387 559 391 554 14 -14 191 -275 196 -288 3 -7 -177 -300 -401 -652 -366 -580 -405 -645 -405 -682 0 -80 49 -131 124 -131 56 0 84 19 130 89 23 36 366 577 761 1201 394 624 722 1140 729 1147 8 8 164 -231 621 -954 438 -694 618 -971 640 -984 17 -11 49 -19 74 -19 36 0 49 6 76 33 38 38 50 73 41 120 -3 17 -96 173 -205 346 l-199 315 111 88 c60 49 114 87 118 86 4 -2 202 -217 440 -478 238 -261 444 -483 458 -492 14 -10 43 -18 64 -18 76 0 127 54 127 133 0 50 0 50 -179 246 l-83 91 238 0 c263 0 273 2 315 66 34 52 17 128 -39 169 -26 19 -45 20 -389 23 l-362 3 -239 262 c-132 144 -249 270 -260 281 -27 24 -101 32 -133 15 -13 -7 -91 -66 -173 -132 -124 -100 -150 -117 -161 -106 -7 8 -185 286 -395 619 -210 333 -397 620 -416 638 -29 27 -41 32 -84 32 -40 0 -55 -5 -80 -27z" />
              </g>
            </svg>
          </div>
        </div>
      </div>
      <!-- 빈 상태 메시지 -->
      <p class="text-lg font-medium text-santaGreen">
        아직 완등한 산이 없어요!
      </p>
      <p class="mt-2 text-sm text-santaGreen/70">
        첫 번째 산을 정복하고 도장을 수집해보세요!
      </p>
    </div>

    <div
      v-else
      class="grid grid-cols-3 gap-6 sm:grid-cols-4 lg:grid-cols-6">
      <div
        v-for="complete in mountainCompletedData?.completes"
        :key="complete.id"
        class="relative group">
        <!-- 기존 도장 컴포넌트 내용 -->
        <div class="relative aspect-square">
          <div
            class="absolute inset-0 flex flex-col items-center justify-center transition-all duration-300 rounded-full hover:bg-santaGreen/50">
            <svg
              viewBox="0 0 100 100"
              class="absolute inset-0 w-full h-full opacity-90">
              <circle
                cx="50"
                cy="50"
                r="48"
                fill="none"
                stroke="currentColor"
                stroke-width="2"
                class="text-deepGreen" />
              <circle
                cx="50"
                cy="50"
                r="45"
                fill="none"
                stroke="currentColor"
                stroke-width="1"
                class="text-deepGreen/80" />
            </svg>

            <svg
              viewBox="0 0 100 100"
              class="absolute inset-0 w-full h-full">
              <path
                :id="`circle-${complete.id}`"
                d="M 50,50 m -37,0 a 37,37 0 1,1 74,0 a 37,37 0 1,1 -74,0"
                fill="none" />
              <text class="text-xs fill-santaGreen/60">
                <textPath
                  :href="`#circle-${complete.id}`"
                  startOffset="0%">
                  S A N T A - - S A N T A - - - S A N T A - -
                </textPath>
              </text>
            </svg>

            <div class="relative flex flex-col items-center justify-center p-4">
              <svg
                version="1.0"
                xmlns="http://www.w3.org/2000/svg"
                width="60px"
                height="60px"
                viewBox="0 0 512.000000 512.000000"
                preserveAspectRatio="xMidYMid meet">
                <g
                  transform="translate(0.000000,512.000000) scale(0.100000,-0.100000)"
                  fill="#4d6d57"
                  stroke="1">
                  <path
                    d="M2370 3943 c-16 -16 -179 -264 -361 -553 -182 -289 -334 -528 -338 -533 -4 -4 -68 81 -142 189 -82 120 -144 201 -161 210 -37 19 -97 18 -131 -4 -17 -10 -148 -193 -325 -452 l-297 -435 -265 -5 c-245 -5 -267 -7 -292 -25 -55 -41 -73 -117 -39 -168 38 -58 52 -62 239 -67 l172 -5 -60 -89 c-66 -97 -73 -130 -44 -192 29 -62 117 -84 179 -45 20 12 163 213 414 580 211 310 387 559 391 554 14 -14 191 -275 196 -288 3 -7 -177 -300 -401 -652 -366 -580 -405 -645 -405 -682 0 -80 49 -131 124 -131 56 0 84 19 130 89 23 36 366 577 761 1201 394 624 722 1140 729 1147 8 8 164 -231 621 -954 438 -694 618 -971 640 -984 17 -11 49 -19 74 -19 36 0 49 6 76 33 38 38 50 73 41 120 -3 17 -96 173 -205 346 l-199 315 111 88 c60 49 114 87 118 86 4 -2 202 -217 440 -478 238 -261 444 -483 458 -492 14 -10 43 -18 64 -18 76 0 127 54 127 133 0 50 0 50 -179 246 l-83 91 238 0 c263 0 273 2 315 66 34 52 17 128 -39 169 -26 19 -45 20 -389 23 l-362 3 -239 262 c-132 144 -249 270 -260 281 -27 24 -101 32 -133 15 -13 -7 -91 -66 -173 -132 -124 -100 -150 -117 -161 -106 -7 8 -185 286 -395 619 -210 333 -397 620 -416 638 -29 27 -41 32 -84 32 -40 0 -55 -5 -80 -27z" />
                </g>
              </svg>
              <h3
                class="text-xs text-center font-bold leading-none mt-[-10px] text-santaGreen">
                {{ complete.mountainName }}
              </h3>
            </div>
          </div>
          <div
            class="absolute inset-0 rounded-full opacity-40 mix-blend-multiply bg-gradient-to-br from-transparent via-red-900/5 to-red-900/10"></div>

          <div
            class="absolute inset-0 rounded-full opacity-20 mix-blend-multiply">
            <svg
              viewBox="0 0 100 100"
              class="w-full h-full">
              <circle
                cx="50"
                cy="50"
                r="48"
                fill="none"
                stroke="currentColor"
                stroke-width="0.5"
                class="text-red-900"
                stroke-dasharray="3 2" />
              <circle
                cx="50"
                cy="50"
                r="46"
                fill="none"
                stroke="currentColor"
                stroke-width="0.5"
                class="text-red-900"
                stroke-dasharray="2 3" />
            </svg>
          </div>
        </div>

        <div
          class="absolute w-full h-full transition-opacity duration-300 rounded-full opacity-0 -bottom-1 -right-1 bg-santaLightGreen/10 blur-sm group-hover:opacity-100" />
      </div>
    </div>
  </div>
</template>
