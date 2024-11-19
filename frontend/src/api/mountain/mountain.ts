import { useMutation } from '@tanstack/vue-query';
import apiClient from '../apiClient';
import { Mountains } from '../../types/types';
import { Province, provinceObj } from '../../data/CityData';

const getMountain = async (province: Province, city: string) =>
  (
    await apiClient.get<Mountains>('/mountain', {
      params: {
        province: provinceObj[province],
        city
      }
    })
  ).data;

export const useGetMountain = () =>
  useMutation({
    mutationFn: ({ province, city }: { province: Province; city: string }) =>
      getMountain(province, city),
    onSuccess: () => {}
  });
