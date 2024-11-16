<script setup lang="ts">
import { Appointment } from '../../types/types';
import Button from '../ui/button/Button.vue';

const props = defineProps<{ appointment: Appointment }>();
</script>

<template>
  <div>
    <div
      class="lg:min-w-[380px] md:min-w-[300px] sm:min-w-[250px] p-4 rounded-lg border grid grid-rows-3">
      <div class="flex justify-between">
        <div class="text-xl font-bold">
          {{ props.appointment.mountainName }}
        </div>
        <div>{{ props.appointment.meetAt }}</div>
      </div>
      <div class="mt-auto mr-auto">
        <div class="flex-shrink-0 text-md">
          {{ props.appointment.countOfParticipant }}명
        </div>
      </div>
      <div class="flex gap-3">
        <div class="flex flex-wrap items-center gap-1 text-xs">
          <!-- <div>하드코딩</div> -->
          <span class="text-santaGreen">하은</span>
          <span class="text-santaGreen">준희</span>
          <span class="text-santaGreen">희민</span>
          <span class="text-santaGreen">지후</span>
          <span class="text-santaGreen">예준</span>
          <span class="text-santaGreen">수아</span>
        </div>
        <div class="mt-auto ml-auto">
          <Button class="px-3 py-[1px] bg-santaGreen">참여</Button>
        </div>
      </div>
    </div>
  </div>
</template>
