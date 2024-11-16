<script setup lang="ts">
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger
} from '../ui/dialog';

import Button from '../ui/button/Button.vue';
import { ref } from 'vue';
import { usePostGroup } from '../../api/group/group';
import DialogClose from '../ui/dialog/DialogClose.vue';

const { mutate: postGroup } = usePostGroup();
const input = ref({
  name: '',
  description: ''
});

const handleRegister = () => {
  postGroup(input.value, {
    onSuccess: () => {
      input.value.name = '';
      input.value.description = '';
    }
  });
};
</script>

<template>
  <Dialog>
    <DialogTrigger as-child>
      <Button
        variant="outline"
        class="bg-santaGreen hover:bg-santaLightGreen text-santaWhite"
        >등록</Button
      >
    </DialogTrigger>
    <DialogContent class="sm:max-w-[425px] bg-santaWhite">
      <DialogHeader>
        <DialogTitle class="text-santaGreen">그룹 등록</DialogTitle>
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
            v-model="input.name"
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
            v-model="input.description"
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
          :disabled="!input.name"
          @click="handleRegister">
          <DialogClose>등록하기</DialogClose>
        </Button>
      </DialogFooter>
    </DialogContent>
  </Dialog>
</template>
