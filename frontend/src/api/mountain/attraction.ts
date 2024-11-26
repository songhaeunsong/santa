import { useQuery } from '@tanstack/vue-query';
import apiClient from '../apiClient';
import { Attractions } from '../../types/types';

interface AttractionRequstTypes {
  type: number;
  province: string;
  city: string;
}
const getAttraction = ({ type, province, city }: AttractionRequstTypes) =>
  apiClient
    .get<Attractions>('/attraction', {
      params: {
        type,
        province,
        city
      }
    })
    .then(res => res.data);

export const useGetAttraction = ({
  type,
  province,
  city
}: AttractionRequstTypes) =>
  useQuery({
    queryKey: ['attactions', type, province, city],
    queryFn: () => getAttraction({ type, province, city }),
    staleTime: 0,
    gcTime: 0
  });
