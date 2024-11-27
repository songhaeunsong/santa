<script setup lang="ts">
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger
} from '../../ui/dialog';

import { ref } from 'vue';
import DialogClose from '../../ui/dialog/DialogClose.vue';
import { usePostAppointment } from '../../../api/appointment/appointment';
import Button from '../../ui/button/Button.vue';

const { mutate: postAppointment } = usePostAppointment();

const props = defineProps<{ groupId: number }>();
const input = ref({
  groupId: props.groupId,
  mountainId: 113050202,
  date: '2024-11-30T15:30:00'
});

const handleRegister = () => {
  postAppointment(input.value, {
    onSuccess: () => {
      input.value.mountainId = 0;
      input.value.date = '';
    }
  });
};
</script>

<template>
  <Dialog>
    <DialogTrigger as-child>
      <Button
        variant="outline"
        class="rounded-lg bg-santaFreshGreen hover:bg-santaFreshGreen/80 hover:text-santaWhite text-santaWhite"
        >일정 만들기</Button
      >
    </DialogTrigger>
    <DialogContent class="sm:max-w-[425px] bg-santaWhite">
      <DialogHeader>
        <DialogTitle class="text-santaGreen">등산 일정 등록</DialogTitle>
        <DialogDescription class="text-santaLightGreen">
          생성할 그룹의 이름과 소개글을 적어주세요!
        </DialogDescription>
      </DialogHeader>
      <div class="grid gap-4 py-4">
        <div class="grid items-center grid-cols-5 gap-4">
          <label
            for="name"
            class="text-right text-santa-black">
            그룹명
          </label>
          <input
            id="name"
            v-model="input.groupId"
            placeholder="싸피 15반"
            required
            class="col-span-4 px-4 py-2 placeholder-gray-200 border rounded" />
        </div>
        <div class="grid items-center grid-cols-5 gap-4">
          <label
            for="username"
            class="text-right">
            소개글
          </label>
          <textarea
            id="username"
            v-model="input.mountainId"
            placeholder="싸피 서울 캠퍼스 15반의 등산 모임입니다."
            rows="4"
            required
            class="col-span-4 px-4 py-2 placeholder-gray-200 border rounded"></textarea>
        </div>
      </div>
      <DialogFooter>
        <Button
          type="submit"
          class="bg-santaGreen hover:bg-santaLightGreen text-santaWhite"
          :disabled="!input.date"
          @click="handleRegister">
          <DialogClose>등록하기</DialogClose>
        </Button>
      </DialogFooter>
    </DialogContent>
  </Dialog>
</template>
