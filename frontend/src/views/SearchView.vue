<script setup lang="ts">
import { useGeolocation } from '../hooks/useGeolocation';

const { location } = useGeolocation();
</script>

<template>
  <div>
    <h2>사용자 위치</h2>
    <div v-if="location.lat !== null && location.lng !== null">
      <div>위도 (Latitude): {{ location.lat }}</div>
      <div>경도 (Longitude): {{ location.lng }}</div>
      <div>경도 (altitude): {{ location.altitude }}</div>
    </div>
    <div v-else-if="location.error">오류: {{ location.error }}</div>
    <div v-else>위치를 불러오는 중입니다...</div>
  </div>
</template>
