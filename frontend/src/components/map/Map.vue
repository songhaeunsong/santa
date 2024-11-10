<script setup>
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps';
const coordinate = {
  lat: 37.566826,
  lng: 126.9786567
};
</script>

<template>
  <KakaoMap
    :width="'100%'"
    :height="'100vh'"
    :lat="coordinate.lat"
    :lng="coordinate.lng"
    :draggable="true">
    <KakaoMapMarker
      :lat="coordinate.lat"
      :lng="coordinate.lng"></KakaoMapMarker>
  </KakaoMap>
</template>
