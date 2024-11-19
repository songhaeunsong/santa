<script setup lang="ts">
import { ref } from 'vue';
import { Appointment } from '../../../types/types';
import Button from '../../ui/button/Button.vue';
import {
  usePostAppointmentCancelJoin,
  usePostAppointmentJoin
} from '../../../api/appointment/appointment';

const props = defineProps<{ appointment: Appointment; groupId: number }>();
const appointmentDate = new Date(props.appointment.meetAt).toLocaleString();
const isMemberOfAppointment = ref(props.appointment.isMember);

const { mutate: postAppointmentJoin } = usePostAppointmentJoin();
const { mutate: postAppointmentCancelJoin } = usePostAppointmentCancelJoin();
const handleJoin = () => {
  isMemberOfAppointment.value = true;
  postAppointmentJoin(
    {
      groupId: props.groupId,
      appointmentId: props.appointment.id
    },
    {
      onError: () => {
        isMemberOfAppointment.value = false;
      }
    }
  );
};

const handleCancelJoin = () => {
  isMemberOfAppointment.value = false;
  postAppointmentCancelJoin(props.appointment.id, {
    onError: () => {
      isMemberOfAppointment.value = true;
    }
  });
};
</script>

<template>
  <div>
    <div
      class="lg:min-w-[380px] md:min-w-[300px] sm:min-w-[250px] p-4 rounded-lg border grid grid-rows-3">
      <div class="flex justify-between">
        <div class="text-xl font-bold">
          {{ props.appointment.mountainName }}
        </div>
        <div>{{ appointmentDate }}</div>
      </div>
      <div class="mt-auto mr-auto">
        <div class="flex-shrink-0 text-md">
          {{ props.appointment.countOfMembers }}명
        </div>
      </div>
      <div class="flex gap-3">
        <div class="flex flex-wrap items-center gap-1 text-xs">
          <span
            v-for="member in props.appointment.members"
            class="text-santaGreen"
            >{{ member.name }}</span
          >
        </div>
        <div class="mt-auto ml-auto">
          <Button
            v-show="isMemberOfAppointment"
            class="px-3 py-[1px] bg-santaGreen"
            @click="handleCancelJoin"
            >불참하기</Button
          >
          <Button
            v-show="!isMemberOfAppointment"
            class="px-3 py-[1px] bg-santaGreen"
            @click="handleJoin"
            >참여하기</Button
          >
        </div>
      </div>
    </div>
  </div>
</template>
