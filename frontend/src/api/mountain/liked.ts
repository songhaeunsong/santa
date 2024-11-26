import { useMutation, useQueryClient } from '@tanstack/vue-query';
import apiClient from '../apiClient';

const postLiked = async (mountainId: number) =>
  (await apiClient.post('/mountain/like', { mountainId })).data;

export const usePostLiked = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (mountainId: number) => postLiked(mountainId),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['mountainsLiked']
      });

      queryClient.invalidateQueries({
        queryKey: ['mountainDetail'],
        refetchType: 'none'
      });
    }
  });
};

const deleteLiked = async (mountainId: number) =>
  await apiClient.delete('/mountain/like', { data: { mountainId } });

export const useDeleteLiked = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (mountainId: number) => deleteLiked(mountainId),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['mountainsLiked']
      });

      queryClient.invalidateQueries({
        queryKey: ['mountainDetail'],
        refetchType: 'none'
      });
    }
  });
};
