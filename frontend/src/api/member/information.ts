import { useQuery } from '@tanstack/vue-query';
import apiClient from '../apiClient';
import { Member, MemberDetail, MountainLikes } from '../../types/types';

const getMemberInformation = () =>
  apiClient.get<Member>('member/mypage').then(res => res.data);

export const useGetMemberInformation = () => {
  return useQuery({
    queryKey: ['myInformation'],
    queryFn: () => getMemberInformation(),
    staleTime: 1000 * 60 * 10
  });
};

const getOtherMemberInformation = (id: string) =>
  apiClient
    .get<MemberDetail>('member/profile', { params: { id } })
    .then(res => res.data);

export const useGetOtherMemberInformation = (memberId: string) => {
  return useQuery({
    queryKey: ['memberInformation'],
    queryFn: () => getOtherMemberInformation(memberId),
    staleTime: 0
  });
};

const getMemberMountainLike = () =>
  apiClient.get<MountainLikes>('member/like').then(res => res.data);

export const useGetMemberMountainLike = () =>
  useQuery({
    queryKey: ['mountainsLiked'],
    queryFn: () => getMemberMountainLike(),
    staleTime: 0
  });
