<script setup lang="ts">
import {
  CalendarCell,
  CalendarCellTrigger,
  CalendarGrid,
  CalendarGridBody,
  CalendarGridHead,
  CalendarGridRow,
  CalendarHeadCell,
  CalendarHeader,
  CalendarHeading
} from '../../ui/calendar';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue
} from '../../ui/select';
import { cn } from '../../../lib/utils';
import {
  type DateValue,
  getLocalTimeZone,
  today
} from '@internationalized/date';
import { useVModel } from '@vueuse/core';
import {
  CalendarRoot,
  type CalendarRootEmits,
  type CalendarRootProps,
  useDateFormatter,
  useForwardPropsEmits
} from 'radix-vue';
import { createDecade, createYear, toDate } from 'radix-vue/date';
import { computed, ref, watch, type HTMLAttributes, type Ref } from 'vue';
import GroupAppointmentCard from './GroupAppointmentCard.vue';
import { useGetAppointmentList } from '../../../api/appointment/appointment';

const props = withDefaults(
  defineProps<
    CalendarRootProps & { class?: HTMLAttributes['class'] } & {
      groupId: number;
      date: string;
    }
  >(),
  {
    modelValue: undefined,
    placeholder() {
      return today(getLocalTimeZone());
    },
    weekdayFormat: 'short'
  }
);

const emits = defineEmits<CalendarRootEmits>();

const delegatedProps = computed(() => {
  const { class: _, placeholder: __, ...delegated } = props;

  return delegated;
});

const placeholder = useVModel(props, 'modelValue', emits, {
  passive: true,
  defaultValue: today(getLocalTimeZone())
}) as Ref<DateValue>;

const forwarded = useForwardPropsEmits(delegatedProps, emits);

const formatter = useDateFormatter('en');

// const groupProps = defineProps<{ groupId: number; date: string }>();

const selectedDate = ref<{ month: string; year: string }>({
  month: placeholder.value.month.toString(),
  year: placeholder.value.year.toString()
});

const {
  data: appointmentData,
  isError,
  refetch
} = useGetAppointmentList(
  props.groupId,
  `${selectedDate.value.year}-${selectedDate.value.month}-01`
);

watch(
  selectedDate,
  () => {
    // console.log('데이터 변경');
    // console.log(`${selectedDate.value.year}-${selectedDate.value.month}-01`);

    // queryClient.invalidateQueries({ queryKey: ['appointments'] });
    refetch();
  },
  { deep: true }
);

// console.log(selectedMonth.value, selectedYear.value);
</script>

<template>
  <div class="flex gap-5">
    <CalendarRoot
      v-slot="{ date, grid, weekDays }"
      v-model:placeholder="placeholder"
      v-bind="forwarded"
      :class="cn('rounded-md flex flex-col items-center', props.class)">
      <CalendarHeader>
        <CalendarHeading class="flex items-center justify-between w-full gap-2">
          <Select
            v-model="selectedDate.month"
            :default-value="placeholder.month.toString()"
            @update:model-value="
              (v: string | null) => {
                if (!v || !placeholder) return;
                if (Number(v) === placeholder?.month) return;
                placeholder = placeholder.set({
                  month: Number(v)
                });
              }
            ">
            <SelectTrigger
              aria-label="Select month"
              class="w-[60%]">
              <SelectValue placeholder="Select month" />
            </SelectTrigger>
            <SelectContent class="max-h-[200px]">
              <SelectItem
                v-for="month in createYear({ dateObj: date })"
                :key="month.toString()"
                :value="month.month.toString()">
                {{ formatter.custom(toDate(month), { month: 'long' }) }}
              </SelectItem>
            </SelectContent>
          </Select>

          <Select
            v-model="selectedDate.year"
            :default-value="placeholder.year.toString()"
            @update:model-value="
              (v: string | null) => {
                if (!v || !placeholder) return;
                if (Number(v) === placeholder?.year) return;
                placeholder = placeholder.set({
                  year: Number(v)
                });
              }
            ">
            <SelectTrigger
              aria-label="Select year"
              class="w-[40%]">
              <SelectValue placeholder="Select year" />
            </SelectTrigger>
            <SelectContent class="max-h-[200px]">
              <SelectItem
                v-for="yearValue in createDecade({
                  dateObj: date,
                  startIndex: -10,
                  endIndex: 10
                })"
                :key="yearValue.toString()"
                :value="yearValue.year.toString()">
                {{ yearValue.year }}
              </SelectItem>
            </SelectContent>
          </Select>
        </CalendarHeading>
      </CalendarHeader>

      <div
        class="flex flex-col pt-4 space-y-4 sm:flex-row sm:gap-x-4 sm:gap-y-0">
        <CalendarGrid
          v-for="month in grid"
          :key="month.value.toString()">
          <CalendarGridHead>
            <CalendarGridRow>
              <CalendarHeadCell
                v-for="day in weekDays"
                :key="day">
                {{ day }}
              </CalendarHeadCell>
            </CalendarGridRow>
          </CalendarGridHead>
          <CalendarGridBody class="grid">
            <CalendarGridRow
              v-for="(weekDates, index) in month.rows"
              :key="`weekDate-${index}`"
              class="w-full mt-2">
              <CalendarCell
                v-for="weekDate in weekDates"
                :key="weekDate.toString()"
                :date="weekDate"
                @click="() => console.log(weekDate.day)">
                <CalendarCellTrigger
                  :day="weekDate"
                  :month="month.value" />
              </CalendarCell>
            </CalendarGridRow>
          </CalendarGridBody>
        </CalendarGrid>
      </div>
    </CalendarRoot>
    <GroupAppointmentCard v-if="!isError && appointmentData" />
  </div>
</template>
