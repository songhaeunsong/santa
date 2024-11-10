import { useQuery } from '@tanstack/vue-query';
import apiClient from '../apiClient';

type tierType = 'BRONZE' | 'SILVER' | 'GOLD' | 'DIAMOND' | 'CHALLENGER';

interface Member {
  exp: number;
  profileUrl: string;
  tier: tierType;
  nickname: string;
  email: string;
}

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
