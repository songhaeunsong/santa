<script setup lang="ts">
import { useGetAppointmentList } from '../../../api/appointment/appointment';
import Carousel from '../../ui/carousel/Carousel.vue';
import CarouselContent from '../../ui/carousel/CarouselContent.vue';
import CarouselItem from '../../ui/carousel/CarouselItem.vue';
import CarouselNext from '../../ui/carousel/CarouselNext.vue';
import CarouselPrevious from '../../ui/carousel/CarouselPrevious.vue';
import GroupAppointmentCalendar from './GroupAppointmentCalendar.vue';
import GroupAppointmentCard from './GroupAppointmentCard.vue';
import GroupAppointmentItem from './GroupAppointmentItem.vue';
import GroupAppointmentRegisterModal from './GroupAppointmentRegisterModal.vue';

const props = defineProps<{ groupId: number; date: string }>();

const { data: appointmentData, isError } = useGetAppointmentList(
  props.groupId,
  props.date
);
</script>

<template>
  <div class="flex gap-5"></div>

  <Carousel
    v-if="!isError && appointmentData"
    class="px-8"
    :opts="{
      align: 'start'
    }">
    <CarouselContent class="-ml-1">
      <CarouselItem
        v-for="appointment in appointmentData.appointments"
        :key="appointment.id"
        class="flex-wrap basis-1/2">
        <div class="flex gap-4">
          <GroupAppointmentItem
            :appointment="appointment"
            :group-id="groupId" />
        </div>
      </CarouselItem>
    </CarouselContent>
    <CarouselPrevious />
    <CarouselNext />
  </Carousel>
  <div class="flex justify-end">
    <GroupAppointmentRegisterModal :group-id="groupId" />
  </div>
</template>
