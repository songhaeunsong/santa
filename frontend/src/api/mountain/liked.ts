import { useMutation } from '@tanstack/vue-query';
import apiClient from '../apiClient';

const postLiked = async (mountainId: number) =>
  (await apiClient.post('/mountain/like', { mountainId })).data;

export const usePostLiked = () => {
  return useMutation({
    mutationFn: (mountainId: number) => postLiked(mountainId)
  });
};
