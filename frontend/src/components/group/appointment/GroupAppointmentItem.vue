<script setup lang="ts">
import { ref } from 'vue';
import { Appointment } from '../../../types/types';
import Button from '../../ui/button/Button.vue';
import {
  usePostAppointmentCancelJoin,
  usePostAppointmentJoin
} from '../../../api/appointment/appointment';

const props = defineProps<{ appointment: Appointment; groupId: number }>();

const date = new Date(props.appointment.meetAt);

const formattedDate = `${date.getUTCFullYear()}. ${String(date.getUTCMonth() + 1).padStart(2, '0')}. ${String(date.getUTCDate()).padStart(2, '0')}. ${
  date.getUTCHours() < 12 ? '오전' : '오후'
} ${String(date.getUTCHours() % 12 || 12).padStart(2, '0')}:${String(date.getUTCMinutes()).padStart(2, '0')}`;

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
  <div class="group">
    <div
      class="lg:min-w-[380px] md:min-w-[300px] sm:min-w-[250px] p-6 rounded-xl border border-santaLightGreen/20 bg-white hover:shadow-lg transition-all duration-300">
      <div class="flex items-start justify-between mb-5">
        <div class="flex items-center gap-2">
          <h3 class="text-xl font-bold text-santaGreen">
            {{ props.appointment.mountainName }}
          </h3>
        </div>
        <div
          class="flex items-center px-3 py-1 text-sm rounded-lg bg-santaLightGreen/10 text-santaGreen">
          {{ formattedDate }}
        </div>
      </div>

      <div class="flex items-center gap-2 mb-4">
        <svg
          class="w-5 h-5 text-santaGreen/70"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2">
          <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
          <circle
            cx="9"
            cy="7"
            r="4"></circle>
          <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
          <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
        </svg>
        <span class="font-medium text-santaGreen">
          {{ props.appointment.countOfMembers }}명 참여중
        </span>
      </div>

      <div class="flex items-end justify-between">
        <div class="flex flex-wrap gap-2">
          <span
            v-for="member in props.appointment.members"
            :key="member.name"
            class="px-2.5 py-1 text-xs font-medium bg-santaLightGreen/10 text-santaGreen rounded-full">
            {{ member.name }}
          </span>
        </div>

        <div>
          <Button
            v-if="isMemberOfAppointment"
            @click="handleCancelJoin"
            class="px-4 py-2 text-sm font-medium text-white transition-colors duration-300 border rounded-lg bg-deepGreen hover:bg- hover:text-white">
            불참하기
          </Button>

          <Button
            v-else
            @click="handleJoin"
            class="px-4 py-2 text-sm font-medium text-white transition-colors duration-300 rounded-lg bg-santaFreshGreen hover:bg-santaGreen">
            참여하기
          </Button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.group:hover {
  transform: translateY(-2px);
}
</style>
