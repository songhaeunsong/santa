import { useMutation, useQuery, useQueryClient } from '@tanstack/vue-query';
import apiClient from '../apiClient';
import { Complete, MountainDetail, Mountains } from '../../types/types';
import { Province, provinceObj } from '../../data/CityData';
import { usePostAppointment } from '../appointment/appointment';

const getMountain = (province: Province, city: string) => {
  const cityRequest = city === '전체' ? '' : city;

  return apiClient
    .get<Mountains>('/mountain', {
      params: {
        province: provinceObj[province],
        city: cityRequest
      }
    })
    .then(res => res.data);
};

export const useGetMountain = () =>
  useMutation({
    mutationFn: ({ province, city }: { province: Province; city: string }) =>
      getMountain(province, city),
    onSuccess: () => {}
  });

const getMountainDetail = (mountainCode: string) =>
  apiClient
    .get<MountainDetail>('/mountain/detail', {
      params: {
        mountainCode
      }
    })
    .then(res => res.data);

export const useGetMountainDetail = (mountainCode: string) =>
  useQuery({
    queryKey: ['mountainDetail'],
    queryFn: () => getMountainDetail(mountainCode),
    staleTime: 0,
    gcTime: 0
  });

export interface GetMountainCompletedResponse {
  completes: Complete[];
}

const getMountainCompleted = () =>
  apiClient
    .get<GetMountainCompletedResponse>('mountain/complete')
    .then(res => res.data);

export const useGetMountainCompleted = () =>
  useQuery({
    queryKey: ['mountainsCompleted'],
    queryFn: () => getMountainCompleted(),
    staleTime: 1000 * 60 * 10
  });

interface PostMountainCompletedResponse {
  exp: number;
}

const postMountainCompleted = (mountainId: number) =>
  apiClient
    .post<PostMountainCompletedResponse>('mountain/complete', { mountainId })
    .then(res => res.data);

export const usePostMountainCompleted = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (mountainId: number) => postMountainCompleted(mountainId),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['mountainsCompleted', 'mountainDetail']
      });
    }
  });
};
