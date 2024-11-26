<script setup lang="ts">
import { computed, ref, watch } from 'vue';
import { useDeleteLiked, usePostLiked } from '../../api/mountain/liked';
import { useGetMountainDetail } from '../../api/mountain/mountain';
import { useRoute } from 'vue-router';
import BaseLoading from '../BaseLoading.vue';
import MountainCompletedModal from './MountainCompletedModal.vue';
import Button from '../ui/button/Button.vue';
import PathMap from '../map/PathMap.vue';
import { usePostMountainRecommend } from '../../api/mountain/recommend';

const route = useRoute();
const mountainCode = route.params.id as string;
const isLiked = ref(false);

// const {
//   data: mountainDetailData,
//   isError,
//   isLoading,
//   isFetching,
//   refetch
// } = useGetMountainDetail(mountainCode);

const { mutateAsync: postMountainRecommend } = usePostMountainRecommend();

const difficulty = '쉬움';
const mountainDetailData = computed(() =>
  postMountainRecommend({ mountainCode, difficulty }).then(res => {
    return res.geometry.coordinates;
  })
);

// watch(
//   () => mountainDetailData.value,
//   newData => {
//     if (newData) {
//       isLiked.value = newData.mountainInfo.isLiked || false;
//     }
//   },
//   { immediate: true }
// );
</script>
<template>
  <BaseLoading v-if="!mountainDetailData" />
  <div
    v-else-if="mountainDetailData"
    class="bg-[#FFFDF6] p-6 md:p-20">
    <div class="overflow-hidden shadow-lg rounded-xl">
      <AIPathMap :path-data="mountainDetailData"></AIPathMap>
    </div>
  </div>
</template>

<style scoped>
@keyframes checkAnimation {
  0% {
    opacity: 0;
    transform: scale(0);
  }
  50% {
    opacity: 0.5;
    transform: scale(1.2);
  }
  100% {
    opacity: 1;
    transform: scale(1);
  }
}

.animate-check {
  animation: checkAnimation 0.3s ease-in-out;
}
</style>
