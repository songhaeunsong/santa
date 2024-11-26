import { ref, onMounted } from 'vue';

interface Geolocation {
  lat: number | null;
  lng: number | null;
  altitude: number | null;
  error: string | null;
}

export function useGeolocation() {
  const location = ref<Geolocation>({
    lat: null,
    lng: null,
    altitude: null,
    error: null
  });

  const getCurrentLocation = (): void => {
    if (!navigator.geolocation) {
      location.value.error = 'Geolocation is not supported by your browser.';
      return;
    }

    navigator.geolocation.getCurrentPosition(
      position => {
        const { latitude, longitude, altitude } = position.coords;
        location.value.lat = latitude;
        location.value.lng = longitude;
        location.value.altitude = altitude;
        location.value.error = null;
      },
      error => {
        location.value.error = error.message;
      }
    );
  };

  onMounted(() => {
    getCurrentLocation();
  });

  return { location, getCurrentLocation };
}
