import { ref } from 'vue';

let isLogined = ref(false);
export const getLoginStatus = () => isLogined.value;
export const setLoginStatus = (status: boolean) => (isLogined.value = status);
