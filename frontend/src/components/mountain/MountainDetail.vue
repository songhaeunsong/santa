<script setup lang="ts">
import { ref } from 'vue';
import { usePostLiked } from '../../api/mountain/liked';

const dummy = {
  city: '은평구',
  height: 243,
  id: 113800101,
  image:
    'https://www.ep.go.kr/CmsWeb/resource/attach/renewal/contents/ecology_park/img_ep01.jpg',
  isLiked: false,
  latitude: '37.623333',
  longitude: '126.939722',
  name: '백련산',
  province: '서울시'
};
const isLiked = ref<boolean>(dummy.isLiked);

const { mutate: postLiked } = usePostLiked();

const handleLiked = () => {
  isLiked.value = true;
  postLiked(dummy.id, {
    onError: () => {
      isLiked.value = false;
    }
  });
};
const handleCancelLiked = () => {
  isLiked.value = false;
};
</script>

<template>
  <div>
    <div>{{ dummy.name }}</div>
    <div>고도: {{ dummy.height }}</div>
    <div>{{ dummy.image }}</div>
    <div>{{ dummy.isLiked ? '관심' : '관심x' }}</div>
    <div>
      <span>{{ dummy.province }}</span> <span>{{ dummy.city }}</span>
    </div>

    <div>
      <Button
        v-show="!isLiked"
        @click="handleLiked"
        >관심</Button
      >
      <Button
        v-show="isLiked"
        @click="handleCancelLiked"
        >취소</Button
      >
    </div>
  </div>
</template>
