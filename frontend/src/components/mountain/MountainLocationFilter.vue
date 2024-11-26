<script setup lang="ts">
import { ref } from 'vue';
import { citiesData, Province } from '../../data/CityData';
import Button from '../ui/button/Button.vue';
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue
} from '../ui/select';
import { useGetMountain } from '../../api/mountain/mountain';
import MountainList from './MountainList.vue';
import Map from '../map/Map.vue';
import { useMountainStore } from '../../store/mountains';

const selectedProvince = ref<Province>('서울특별시');
const selectedCity = ref('전체');
const isSearched = ref(false);
const isHovered = ref(false);

const { setMountains } = useMountainStore();
const { mutateAsync: getMountain } = useGetMountain();

const handleSearch = () => {
  getMountain({
    province: selectedProvince.value,
    city: selectedCity.value
  }).then(data => {
    setMountains(data.mountains);
  });
  isSearched.value = true;
};
</script>

<template>
  <div
    class="grid flex-1 grid-flow-row grid-cols-1 grid-rows-2 md:grid-rows-1 md:grid-cols-2">
    <div
      class="flex flex-col items-center row-start-2 p-8 md:col-start-1 md:row-auto">
      <div class="flex flex-col items-center w-full h-full max-w-3xl">
        <div class="w-full mb-12">
          <div class="relative mb-8">
            <h2 class="text-2xl font-extrabold tracking-wide text-santaGreen">
              Mountain
            </h2>
            <svg
              class="absolute left-0 w-20 h-2 opacity-40 -bottom-2 text-santaFreshGreen/50">
              <rect
                width="100%"
                height="100%"
                fill="currentColor" />
            </svg>
          </div>

          <div
            class="flex flex-col items-start gap-4 p-6 sm:flex-row sm:items-center bg-santaLightGreen/10 backdrop-blur-md rounded-2xl">
            <Select v-model="selectedProvince">
              <SelectTrigger
                class="min-w-[160px] bg-santaWhite hover:bg-santaLightGreen/10 transition-all duration-300 rounded-xl">
                <SelectValue />
              </SelectTrigger>
              <SelectContent class="bg-santaWhite rounded-xl">
                <SelectGroup>
                  <SelectLabel class="text-sm font-medium text-emerald-700/70"
                    >도/특별시/광역시</SelectLabel
                  >
                  <SelectItem
                    v-for="province in Object.keys(citiesData)"
                    :key="province"
                    :value="province"
                    class="transition-all duration-200 hover:bg-emerald-100">
                    {{ province }}
                  </SelectItem>
                </SelectGroup>
              </SelectContent>
            </Select>

            <Select v-model="selectedCity">
              <SelectTrigger
                class="min-w-[160px] bg-santaWhite hover:bg-santaLightGreen/10 transition-all duration-300 rounded-xl">
                <SelectValue />
              </SelectTrigger>
              <SelectContent class="bg-santaWhite rounded-xl">
                <SelectGroup>
                  <SelectLabel class="text-sm font-medium text-emerald-700/70"
                    >시/군/구</SelectLabel
                  >
                  <SelectItem
                    v-for="city in citiesData[selectedProvince]"
                    :key="city"
                    :value="city"
                    class="transition-all duration-200 hover:bg-emerald-100">
                    {{ city }}
                  </SelectItem>
                </SelectGroup>
              </SelectContent>
            </Select>

            <Button
              @click="handleSearch"
              @mouseenter="isHovered = true"
              @mouseleave="isHovered = false"
              class="bg-santaFreshGreen border-hidden hover:bg-santaFreshGreen text-white font-medium px-6 py-2.5 rounded-lg transition-all duration-200 shadow-md hover:shadow-lg transform hover:-translate-y-0.5">
              <span class="relative z-10">검색하기</span>
            </Button>
          </div>
        </div>

        <div class="flex-1 w-full">
          <MountainList v-if="isSearched" />
          <div
            v-else
            class="flex flex-col items-center justify-center p-16 bg-santaLightGray/10 border-emerald-100 rounded-2xl">
            <div class="space-y-8 text-center">
              <div class="relative mx-auto w-28 h-28">
                <div
                  class="absolute inset-0 rounded-full bg-santaGray/20"></div>
                <svg
                  class="absolute inset-0 w-full h-full text-deepGreen"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="2">
                  <path d="m8 3 4 8 5-5 5 15H2L8 3z" />
                </svg>
              </div>

              <h3
                class="text-2xl font-bold tracking-tight text-santaFreshGreen">
                산을 검색해보세요!
              </h3>

              <p
                class="max-w-lg mx-auto text-base leading-relaxed text-gray-700">
                지역을 선택하고
                <strong class="text-santaFreshGreen">검색하기</strong> 버튼을
                클릭하면<br />
                해당 지역의 산들을 확인할 수 있습니다.
              </p>

              <div
                class="inline-flex items-center gap-3 px-4 py-2 text-sm font-medium text-gray-700 bg-gray-100 border rounded-full border-santaGreen/20">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 24 24"
                  fill="currentColor"
                  class="w-5 h-5 text-santaFreshGreen">
                  <path
                    d="M4 6.14286V18.9669L9.06476 16.7963L15.0648 19.7963L20 17.6812V4.85714L21.303 4.2987C21.5569 4.18992 21.8508 4.30749 21.9596 4.56131C21.9862 4.62355 22 4.69056 22 4.75827V19L15 22L9 19L2.69696 21.7013C2.44314 21.8101 2.14921 21.6925 2.04043 21.4387C2.01375 21.3765 2 21.3094 2 21.2417V7L4 6.14286ZM16.2426 11.2426L12 15.4853L7.75736 11.2426C5.41421 8.89949 5.41421 5.10051 7.75736 2.75736C10.1005 0.414214 13.8995 0.414214 16.2426 2.75736C18.5858 5.10051 18.5858 8.89949 16.2426 11.2426ZM12 12.6569L14.8284 9.82843C16.3905 8.26633 16.3905 5.73367 14.8284 4.17157C13.2663 2.60948 10.7337 2.60948 9.17157 4.17157C7.60948 5.73367 7.60948 8.26633 9.17157 9.82843L12 12.6569Z"></path>
                </svg>
                검색 결과는 지도에서도 확인할 수 있습니다.
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <Map class="row-start-1 md:col-start-2 md:row-auto"></Map>
  </div>
</template>

<style scoped>
.animate-dash {
  stroke-dasharray: 1000;
  stroke-dashoffset: 1000;
  animation: dash 3s ease-out forwards infinite;
}

@keyframes dash {
  0% {
    stroke-dashoffset: 1000;
  }
  100% {
    stroke-dashoffset: 0;
  }
}

@keyframes pulse {
  0%,
  100% {
    opacity: 0.5;
    transform: scale(1);
  }
  50% {
    opacity: 0.2;
    transform: scale(1.05);
  }
}
</style>
