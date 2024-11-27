<script setup lang="ts">
import { ref } from 'vue';
import { KakaoMap } from 'vue3-kakao-maps';

const colorMountainEasy = '#6DAD2C';

const props = defineProps<{
  pathData: number[][][];
}>();

const message = ref('');
const map = ref<kakao.maps.Map | null>(null);

const mapConfig = ref({
  lat: 37.566826,
  lng: 126.9786567,
  zoom: 5
});

const onLoadKakaoMap = (mapRef: kakao.maps.Map) => {
  map.value = mapRef;

  if (props.pathData) {
    props.pathData.forEach(path => {
      let strokeColor = colorMountainEasy;

      props.pathData.forEach(segment => {
        const linePath = segment.map(
          coord => new kakao.maps.LatLng(coord[0], coord[1])
        );

        const polyline = new kakao.maps.Polyline({
          path: linePath,
          strokeWeight: 4,
          strokeColor,
          strokeOpacity: 0.8,
          strokeStyle: 'solid'
        });

        polyline.setMap(map.value);
      });
    });

    [mapConfig.value.lat, mapConfig.value.lng] =
      props.pathData[0][0].map(Number);
    mapConfig.value.zoom = 8;
  }
};
</script>

<template>
  <KakaoMap
    :key="`${mapConfig.lat}-${mapConfig.lng}-${mapConfig.zoom}`"
    :lat="mapConfig.lat"
    :lng="mapConfig.lng"
    :level="mapConfig.zoom"
    style="width: 100%; height: 500px"
    @onLoadKakaoMap="onLoadKakaoMap">
  </KakaoMap>
  <p v-html="message"></p>
</template>
