import axios from 'axios';

const apiClient = axios.create({
  withCredentials: true,
  baseURL: `${import.meta.env.VITE_BASE_URL}`,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json'
  }
});

export default apiClient;
