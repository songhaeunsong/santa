import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useLocationStore = defineStore('location', () => {
  const userLocation = ref<{ lat: number; lng: number }>({
    lat: 37.5664056,
    lng: 126.9778222
  });
  const isLoading = ref(false);

  const startLoading = () => {
    isLoading.value = true;
  };

  const setUserLocation = (location: { lat: number; lng: number }) => {
    userLocation.value = location;
    isLoading.value = false;
  };

  return {
    userLocation,
    isLoading,
    startLoading,
    setUserLocation
  };
});
