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
        class="bg-santaFreshGreen hover:bg-santaFreshGreen text-white font-medium px-6 py-2.5 rounded-lg transition-all duration-200 shadow-md hover:shadow-lg transform hover:-translate-y-0.5">
        그룹 등록
      </Button>
    </DialogTrigger>

    <DialogContent
      class="sm:max-w-[500px] bg-white rounded-xl shadow-xl transform transition-all">
      <DialogHeader class="mb-6 space-y-3">
        <DialogTitle
          class="text-2xl font-bold text-santaFreshGreen bg-clip-text">
          새로운 그룹 만들기
        </DialogTitle>
        <DialogDescription class="text-base text-gray-500">
          등산을 함께 할 그룹을 만들어보세요!
        </DialogDescription>
      </DialogHeader>

      <div class="space-y-6">
        <div class="space-y-2">
          <label
            for="name"
            class="block text-sm font-medium text-gray-700">
            그룹명
          </label>
          <input
            id="name"
            v-model="input.name"
            placeholder="예: 싸피 15반"
            required
            class="w-full px-4 py-2.5 rounded-lg border border-gray-200 focus:border-santaFreshGreen transition-all duration-200 placeholder-gray-400 text-gray-800" />
        </div>

        <div class="space-y-2">
          <label
            for="description"
            class="block text-sm font-medium text-gray-700">
            소개글
          </label>
          <textarea
            id="description"
            v-model="input.description"
            placeholder="그룹에 대해 소개해주세요"
            rows="4"
            required
            class="w-full px-4 py-2.5 rounded-lg border border-gray-200 focus:border-santaFreshGreen transition-all duration-200 placeholder-gray-400 text-gray-800 resize-none"></textarea>
        </div>
      </div>

      <DialogFooter class="mt-8">
        <DialogClose>
          <Button
            type="submit"
            :disabled="!input.name"
            @click="handleRegister"
            class="w-full bg-santaFreshGreen hover:bg-santaFreshGreen text-white font-medium px-6 py-2.5 rounded-lg transition-all duration-200 shadow-md hover:shadow-lg disabled:opacity-50 disabled:cursor-not-allowed transform hover:-translate-y-0.5">
            그룹 만들기
          </Button>
        </DialogClose>
      </DialogFooter>
    </DialogContent>
  </Dialog>
</template>
