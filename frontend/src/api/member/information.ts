import { useQuery } from '@tanstack/vue-query';
import apiClient from '../apiClient';
import { Member } from '../../types/types';

const getMemberInformation = async (): Promise<Member> => {
  return (await apiClient.get<Member>('member/mypage')).data;
};

export const useGetMemberImpormation = () => {
  return useQuery<Member, Error>({
    queryKey: ['memberInformation'],
    queryFn: () => getMemberInformation(),
    staleTime: 1000 * 60 * 10
  });
};
