import { useMutation, useQueryClient } from '@tanstack/vue-query';
import apiClient from '../apiClient';

interface Geometry {
  type: string;
  coordinates: number[][][];
}

interface Response {
  geometry: Geometry;
}
const postMountainRecommend = (mountainCode: string, difficulty: string) =>
  apiClient
    .post<Response>('/mountain/recommend', {
      mountainCode,
      difficulty
    })
    .then(res => res.data);

export const usePostMountainRecommend = () =>
  useMutation({
    mutationFn: ({
      mountainCode,
      difficulty
    }: {
      mountainCode: string;
      difficulty: string;
    }) => postMountainRecommend(mountainCode, difficulty),
    onSuccess: () => {}
  });
