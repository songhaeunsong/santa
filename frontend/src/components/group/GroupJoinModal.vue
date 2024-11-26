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
import { usePostGroupJoin } from '../../api/group/group';
import DialogClose from '../ui/dialog/DialogClose.vue';

const props = defineProps<{ groupName: string; groupId: number }>();

const { mutate: postGroupJoin } = usePostGroupJoin();

const handleJoin = () => {
  postGroupJoin(props.groupId);
};
</script>

<template>
  <Dialog>
    <DialogTrigger as-child>
      <Button
        variant="outline"
        class="bg-santaGreen hover:bg-santaLightGreen text-santaWhite"
        >가입</Button
      >
    </DialogTrigger>
    <DialogContent class="sm:max-w-[425px] bg-santaWhite">
      <DialogHeader>
        <DialogTitle class="text-santaGreen">그룹 가입</DialogTitle>
        <DialogDescription class="mt-2 text-santaGray">
          {{ props.groupName }}에 가입 신청을 하시겠습니까?
        </DialogDescription>
      </DialogHeader>
      <DialogFooter>
        <Button
          type="submit"
          class="bg-santaGreen hover:bg-santaBlack text-santaWhite hover:gb-santaBlack"
          @click="handleJoin">
          <DialogClose class="w-full h-full">가입 신청하기</DialogClose>
        </Button>
      </DialogFooter>
    </DialogContent>
  </Dialog>
</template>
