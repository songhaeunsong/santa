<script setup lang="ts">
import { computed, ref, watch } from 'vue';
import { KakaoMap, KakaoMapCustomOverlay } from 'vue3-kakao-maps';
import { calculateCenter, calculateZoom } from '../../utils/coordinateUtil';
import { storeToRefs } from 'pinia';
import { useMountainStore } from '../../store/mountains';

const { mountains } = storeToRefs(useMountainStore());

const mapConfig = ref({
  lat: 37.566826,
  lng: 126.9786567,
  zoom: 8
});

const center = computed(() => {
  if (mountains.value.length > 0) {
    return calculateCenter(mountains.value);
  }
  return { latitude: 37.566826, longitude: 126.9786567 };
});

const zoom = computed(() => {
  if (mountains.value.length > 0) {
    return calculateZoom(mountains.value);
  }
  return 8;
});

watch(
  mountains,
  () => {
    mapConfig.value = {
      lat: center.value.latitude,
      lng: center.value.longitude,
      zoom: zoom.value
    };
  },
  { deep: true }
);
</script>

<template>
  <KakaoMap
    :key="`${mapConfig.lat}-${mapConfig.lng}-${mapConfig.zoom}`"
    :width="'100%'"
    :height="'100%'"
    :lat="mapConfig.lat"
    :lng="mapConfig.lng"
    :level="mapConfig.zoom"
    :draggable="true">
    <KakaoMapCustomOverlay
      v-for="data in mountains"
      :key="data.id"
      :lat="data.latitude"
      :lng="data.longitude">
      <p
        class="absolute px-2 py-1 text-xs bg-white border rounded-full border-santaLightGreen drop-shadow-md">
        {{ data.name }}
      </p>
    </KakaoMapCustomOverlay>
  </KakaoMap>
</template>
