import { useMutation, useQuery, useQueryClient } from '@tanstack/vue-query';
import apiClient from '../apiClient';

interface Geometry {
  type: string;
  coordinates: number[][][];
}

interface Response {
  geometry: Geometry;
}
const getMountainRecommend = (mountainCode: number, difficulty: string) =>
  apiClient
    .post<Response>('/mountain/recommend', {
      params: {
        mountainCode,
        difficulty
      }
    })
    .then(res => res.data);

export const useGetMountainRecommend = (
  mountainCode: number,
  difficulty: string
) =>
  useQuery({
    queryKey: ['mountainRecommend'],
    queryFn: () => getMountainRecommend(mountainCode, difficulty),
    staleTime: 0
  });
