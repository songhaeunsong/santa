<script setup lang="ts">
import { ref } from 'vue';
import { useGetCheckNickname, usePostNickname } from '../../api/authentication';
import Button from '../ui/button/Button.vue';
import { useRouter } from 'vue-router';

const nickname = ref('');
const isDuplicated = ref<null | boolean>(null);
const isChecked = ref(false);
const getCheckNickname = useGetCheckNickname();
const postNickname = usePostNickname();
const router = useRouter();

const handelCheckNickname = async () => {
  if (nickname.value) {
    isDuplicated.value = (await getCheckNickname(nickname.value)).isDuplicated;
    isChecked.value = true;
  }
};

const handleSubmitNickname = () => {
  if (!nickname.value) {
    return;
  }

  getCheckNickname(nickname.value)
    .then(({ isDuplicated }) => {
      if (!isDuplicated) {
        return postNickname(nickname.value);
      }
    })
    .then(() => {
      router.push('/');
    })
    .catch(() => {
      isChecked.value = false;
    });
};
</script>

<template>
  <div class="w-[300px] flex flex-col text-santaBlack mt-18">
    <form>
      <label
        for="nickname"
        class="text-[18px] font-bold text-deepGreen"
        >닉네임</label
      >
      <div class="flex items-center justify-center gap-6 mt-8 mb-2">
        <input
          id="nickname"
          v-model="nickname"
          type="text"
          placeholder="홍길동"
          class="h-10 p-4 border rounded w-[100%]" />
        <Button
          class="h-10 p-4 text-[12px] bg-santaGreen hover:bg-santaLightGreen"
          @click.prevent="handelCheckNickname"
          >확인</Button
        >
      </div>
      <div class="h-6">
        <div v-if="isChecked">
          <span
            v-if="isDuplicated"
            class="text-errorRed"
            >이미 사용 중인 닉네임입니다.</span
          >
          <span
            v-else
            class="text-deepGreen"
            >사용 가능한 닉네임입니다.</span
          >
        </div>
        <div
          v-if="!isChecked"
          class="text-santaLightGreen">
          닉네임 입력 후 확인을 눌러주세요.
        </div>
      </div>
    </form>

    <div class="h-40">
      <div v-if="isChecked && !isDuplicated">
        <Button
          class="w-[300px] h-10 mt-12 bg-santaGreen hover:bg-santaLightGreen"
          @click="handleSubmitNickname"
          >시작하기</Button
        >
      </div>
    </div>
  </div>
</template>
