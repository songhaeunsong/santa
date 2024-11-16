<script setup lang="ts">
import GroupAppointmentItem from './GroupAppointmentItem.vue';

import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious
} from '../ui/carousel';

const appointmentData = [
  {
    id: 1,
    groupId: 1,
    mountainName: '지리산',
    mountainId: 1,
    meetAt: '24.08.24',
    countOfParticipant: 5
  },
  {
    id: 2,
    groupId: 1,
    mountainName: '지리산',
    mountainId: 1,
    meetAt: '24.08.24',
    countOfParticipant: 5
  },
  {
    id: 3,
    groupId: 1,
    mountainName: '지리산2',
    mountainId: 1,
    meetAt: '24.08.24',
    countOfParticipant: 5
  },
  {
    id: 4,
    groupId: 1,
    mountainName: '지리산3',
    mountainId: 1,
    meetAt: '24.08.24',
    countOfParticipant: 5
  },
  {
    id: 5,
    groupId: 1,
    mountainName: '지리산4',
    mountainId: 1,
    meetAt: '24.08.24',
    countOfParticipant: 5
  },
  {
    id: 6,
    groupId: 1,
    mountainName: '지리산5',
    mountainId: 1,
    meetAt: '24.08.24',
    countOfParticipant: 5
  },
  {
    id: 7,
    groupId: 1,
    mountainName: '지리산5',
    mountainId: 1,
    meetAt: '24.08.24',
    countOfParticipant: 5
  },
  {
    id: 8,
    groupId: 1,
    mountainName: '지리산5',
    mountainId: 1,
    meetAt: '24.08.24',
    countOfParticipant: 5
  },
  {
    id: 9,
    groupId: 1,
    mountainName: '지리산5',
    mountainId: 1,
    meetAt: '24.08.24',
    countOfParticipant: 5
  },
  {
    id: 10,
    groupId: 1,
    mountainName: '지리산5',
    mountainId: 1,
    meetAt: '24.08.24',
    countOfParticipant: 5
  }
];
</script>

<template>
  <Carousel
    class="px-8"
    :opts="{
      align: 'start'
    }">
    <CarouselContent class="-ml-1">
      <CarouselItem
        v-for="appointment in appointmentData"
        :key="appointment.id"
        class="flex-wrap basis-1/2">
        <div class="flex gap-4">
          <GroupAppointmentItem :appointment="appointment" />
        </div>
      </CarouselItem>
    </CarouselContent>
    <CarouselPrevious />
    <CarouselNext />
  </Carousel>
</template>
