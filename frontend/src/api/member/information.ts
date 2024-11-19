import { useQuery } from '@tanstack/vue-query';
import apiClient from '../apiClient';
import { Member } from '../../types/types';

const getMemberInformation = () =>
  apiClient.get<Member>('member/mypage').then(res => res.data);

export const useGetMemberImpormation = () => {
  return useQuery({
    queryKey: ['memberInformation'],
    queryFn: () => getMemberInformation(),
    staleTime: 1000 * 60 * 10
  });
};

const getMemberMountainLike = () =>
  apiClient.get('member/like').then(res => res.data);

export const useGetMemberMountainLike = () =>
  useQuery({
    queryKey: ['mountainsLiked'],
    queryFn: () => getMemberMountainLike(),
    staleTime: 1000 * 60 * 1
  });
