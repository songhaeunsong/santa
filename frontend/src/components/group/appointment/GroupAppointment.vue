<script setup lang="ts">
import Select from '../../ui/select/Select.vue';
import SelectContent from '../../ui/select/SelectContent.vue';
import SelectGroup from '../../ui/select/SelectGroup.vue';
import SelectItem from '../../ui/select/SelectItem.vue';
import SelectLabel from '../../ui/select/SelectLabel.vue';
import SelectTrigger from '../../ui/select/SelectTrigger.vue';
import SelectValue from '../../ui/select/SelectValue.vue';

import { reactive, ref } from 'vue';
import { GroupDetail } from '../../../types/types';
import { useGetAppointmentList } from '../../../api/appointment/appointment';
import GroupAppointmentItem from './GroupAppointmentItem.vue';
import GroupAppointmentRegisterModal from './GroupAppointmentRegisterModal.vue';

const currentYear = new Date().getFullYear();
const currentMonth = new Date().getMonth() + 1;
const selectYear = ref(
  Array.from({ length: 5 }, (_, i) => (currentYear + i).toString())
);
const selectMonth = [
  '1',
  '2',
  '3',
  '4',
  '5',
  '6',
  '7',
  '8',
  '9',
  '10',
  '11',
  '12'
];

const selectDate = reactive({
  year: currentYear.toString(),
  month: currentMonth.toString()
});

const props = defineProps<{ groupDetail: GroupDetail; date: string }>();

const { data: appointmentData, isError } = useGetAppointmentList(
  props.groupDetail.id,
  `2024-11-01`
);
</script>

<template>
  <div class="flex flex-col gap-5">
    <div class="flex gap-5">
      <Select v-model="selectDate.year">
        <SelectTrigger class="w-[180px]">
          <SelectValue placeholder="Year" />
        </SelectTrigger>
        <SelectContent>
          <SelectGroup>
            <SelectLabel>Year</SelectLabel>
            <SelectItem
              v-for="year in selectYear"
              :key="year"
              :value="year">
              {{ year }}
            </SelectItem>
          </SelectGroup>
        </SelectContent>
      </Select>

      <Select v-model="selectDate.month">
        <SelectTrigger class="w-[180px]">
          <SelectValue placeholder="Month" />
        </SelectTrigger>
        <SelectContent>
          <SelectGroup>
            <SelectLabel>Month</SelectLabel>
            <SelectItem
              v-for="month in selectMonth"
              :key="month"
              :value="month">
              {{ month }}월
            </SelectItem>
          </SelectGroup>
        </SelectContent>
      </Select>
    </div>

    <div
      v-if="!isError && appointmentData"
      class="flex flex-col gap-3">
      <GroupAppointmentItem
        :appointment="appointment"
        :group-id="groupDetail.id"
        v-for="appointment in appointmentData.appointments" />
    </div>
    <div class="flex justify-end">
      <GroupAppointmentRegisterModal :group-id="props.groupDetail.id" />
    </div>
  </div>
</template>
