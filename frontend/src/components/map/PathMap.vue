<script setup lang="ts">
import { computed, reactive, ref } from 'vue';
import {
  KakaoMap,
  KakaoMapCustomOverlay,
  KakaoMapMarker
} from 'vue3-kakao-maps';
import { MountainPath } from '../../types/types';
import { useGetAttraction } from '../../api/mountain/attraction';

const colorMountainEasy = '#6DAD2C';
const colorMountainMedium = '#FFAC30';
const colorMountainHard = '#f25050';

const props = defineProps<{
  pathData: MountainPath[];
  province: string;
  city: string;
  checkBox: {
    isAttractionChecked: boolean;
    isAccommodationChecked: boolean;
    isRestaurantChecked: boolean;
  };
}>();

const { data: attractionData } = useGetAttraction({
  type: 12,
  province: props.province,
  city: props.city
});

const { data: accommodationData } = useGetAttraction({
  type: 32,
  province: props.province,
  city: props.city
});

const { data: restaurantData } = useGetAttraction({
  type: 39,
  province: props.province,
  city: props.city
});

const message = ref('');
const map = ref<kakao.maps.Map | null>(null);

const mapConfig = ref({
  lat: 37.566826,
  lng: 126.9786567,
  zoom: 6
});

const markers = computed(() => {
  const combinedMarkers = [];

  if (props.checkBox.isAttractionChecked && attractionData?.value) {
    combinedMarkers.push(
      ...attractionData.value.attractions.map(item => ({
        lat: item.latitude,
        lng: item.longitude,
        imageSrc: '/marker_attraction.png',
        title: item.title,
        address: item.address,
        image: item.image
      }))
    );
  }

  if (props.checkBox.isAccommodationChecked && accommodationData?.value) {
    combinedMarkers.push(
      ...accommodationData.value.attractions.map(item => ({
        lat: item.latitude,
        lng: item.longitude,
        imageSrc: '/marker_accommodation.png',
        title: item.title,
        address: item.address,
        image: item.image
      }))
    );
  }

  if (props.checkBox.isRestaurantChecked && restaurantData?.value) {
    combinedMarkers.push(
      ...restaurantData.value.attractions.map(item => ({
        lat: item.latitude,
        lng: item.longitude,
        imageSrc: '/marker_restaurant.png',
        title: item.title,
        address: item.address,
        image: item.image
      }))
    );
  }

  return combinedMarkers;
});

const onLoadKakaoMap = (mapRef: kakao.maps.Map) => {
  map.value = mapRef;

  if (props.pathData) {
    props.pathData.forEach(path => {
      let strokeColor = colorMountainEasy;
      if (path.attributes.difficulty === '쉬움')
        strokeColor = colorMountainEasy;
      else if (path.attributes.difficulty === '중간')
        strokeColor = colorMountainMedium;
      else if (path.attributes.difficulty === '어려움')
        strokeColor = colorMountainHard;

      const coordinates = path.geometry.coordinates;
      if (coordinates && Array.isArray(coordinates)) {
        coordinates.forEach(segment => {
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
      }
    });

    [mapConfig.value.lat, mapConfig.value.lng] =
      props.pathData[0].geometry.coordinates[0][0].map(Number);
    mapConfig.value.zoom = 6;
  }
};

const markerVisibility = reactive<Record<string, boolean>>({});

const handleClickKakaoMapMarker = (lat: number, lng: number) => {
  Object.keys(markerVisibility).forEach(key => (markerVisibility[key] = false));
  markerVisibility[`${lat}-${lng}`] = true;
};

const hadleCloseOverlay = (lat: number, lng: number) => {
  markerVisibility[`${lat}-${lng}`] = false;
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
    <KakaoMapMarker
      v-for="{ lat, lng, imageSrc } in markers"
      :key="`${lat}-${lng}`"
      :lat
      :lng
      :image="{
        imageSrc,
        imageWidth: 25,
        imageHeight: 40
      }"
      @onClickKakaoMapMarker="handleClickKakaoMapMarker(lat, lng)"
      :clickable="true" />
    <KakaoMapCustomOverlay
      v-for="{ lat, lng, title, address, image } in markers"
      :key="`${lat}-${lng}`"
      :lat
      :lng
      :yAnchor="1.7"
      :visible="markerVisibility[`${lat}-${lng}`] || false">
      <div class="flex gap-2 p-3 bg-white border rounded-sm">
        <button
          @click="hadleCloseOverlay(lat, lng)"
          class="absolute p-1 text-gray-400 transition-colors rounded-full top-2 right-2 hover:text-gray-600 hover:bg-gray-100">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round">
            <path d="M18 6L6 18"></path>
            <path d="M6 6l12 12"></path>
          </svg>
        </button>
        <div class="relative w-8 h-8">
          <img
            :src="image.length ? image : '/santaLogo.png'"
            class="object-contain w-full h-full" />
        </div>
        <div class="flex flex-col">
          <span class="text-santaBlack">{{ title }}</span>
          <span class="text-sm text-santaGray">{{ address }}</span>
        </div>
      </div>
    </KakaoMapCustomOverlay>
  </KakaoMap>
  <p v-html="message"></p>
</template>
