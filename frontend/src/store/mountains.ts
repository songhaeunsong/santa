import { defineStore } from 'pinia';
import { ref } from 'vue';
import { Mountain } from '../types/types';

export const useMountainStore = defineStore('mountains', () => {
  const mountains = ref<Mountain[]>([]);

  const setMountains = (newMountains: Mountain[]) => {
    mountains.value = [...newMountains];
  };

  return {
    mountains,
    setMountains
  };
});
