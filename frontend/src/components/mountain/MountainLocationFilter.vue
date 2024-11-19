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
import { Mountains } from '../../types/types';
import MountainList from './MountainList.vue';

const selectedProvince = ref<Province>('서울특별시');
const selectedCity = ref('');

const mountainData = ref<Mountains | []>([]);
const { mutateAsync: getMountain } = useGetMountain();

const handleSearch = async () => {
  const data = await getMountain({
    province: selectedProvince.value,
    city: selectedCity.value
  });
  mountainData.value = data;
};
</script>

<template>
  <div class="flex flex-col items-center w-full h-full gap-10">
    <div class="flex items-center gap-4">
      <Select v-model="selectedProvince">
        <SelectTrigger class="min-w-[120px]">
          <SelectValue placeholder="도/특별시/광역시" />
        </SelectTrigger>
        <SelectContent>
          <SelectGroup>
            <SelectLabel>도/특별시/광역시</SelectLabel>
            <SelectItem
              v-for="province in Object.keys(citiesData)"
              :key="province"
              :value="province">
              {{ province }}
            </SelectItem>
          </SelectGroup>
        </SelectContent>
      </Select>
      <Select v-model="selectedCity">
        <SelectTrigger class="min-w-[120px]">
          <SelectValue placeholder="시/군/구" />
        </SelectTrigger>
        <SelectContent>
          <SelectGroup>
            <SelectLabel>시/군/구</SelectLabel>
            <SelectItem
              v-for="city in citiesData[selectedProvince]"
              :key="city"
              :value="city"
              >{{ city }}</SelectItem
            >
          </SelectGroup>
        </SelectContent>
      </Select>
      <Button
        class="bg-santaGreen hover:bg-santaBlack"
        @click="handleSearch"
        >검색</Button
      >
    </div>
    <MountainList :mountain-data="mountainData" />
  </div>
</template>
