import { useQuery } from '@tanstack/vue-query';
import { GroupRank, MemberRank } from '../../types/types';
import apiClient from '../apiClient';

const getGroupRank = () =>
  apiClient.get<GroupRank>('rank/group').then(res => res.data);

export const useGetGroupRank = () =>
  useQuery({
    queryKey: ['groupRank'],
    queryFn: () => getGroupRank(),
    staleTime: 1000 * 60 * 2
  });

const getIndividualRank = () =>
  apiClient.get<MemberRank>('rank/individual').then(res => res.data);

export const useGetIndividualRank = () =>
  useQuery({
    queryKey: ['individualRank'],
    queryFn: () => getIndividualRank(),
    staleTime: 1000 * 60 * 2
  });
