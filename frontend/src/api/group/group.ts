import { useMutation, useQuery, useQueryClient } from '@tanstack/vue-query';
import { Group, GroupDetail } from '../../types/types';
import apiClient from '../apiClient';

const getGroupAll = async () => (await apiClient.get<Group[]>('group')).data;

export const useGetgroupAll = () =>
  useQuery({
    queryKey: ['groups'],
    queryFn: () => getGroupAll(),
    staleTime: 0
  });

const getGroupDetail = async (id: string) =>
  (await apiClient.get<GroupDetail>('group/detail', { params: { id } })).data;

export const useGetgroupDetail = (groupId: string) =>
  useQuery({
    queryKey: ['group', groupId],
    queryFn: ({ queryKey }) => getGroupDetail(queryKey[1] as string),
    staleTime: 0
  });

interface postGroupResponse {
  groupId: 0;
}
const postGroup = async (name: string, description: string) =>
  (
    await apiClient.post<postGroupResponse>('/group', {
      name,
      description
    })
  ).data;

export const usePostGroup = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({
      name,
      description
    }: {
      name: string;
      description: string;
    }) => postGroup(name, description),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['groups'] });
    },
    onError: error => {
      throw error;
    }
  });
};

const postGroupJoin = async (groupId: number) =>
  (await apiClient.post('/group/join', { groupId })).data;

export const usePostGroupJoin = () => {
  const queryClient = useQueryClient();

  const mutate = useMutation({
    mutationFn: (groupId: number) => {
      return postGroupJoin(groupId);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['groups'] });
      queryClient.invalidateQueries({ queryKey: ['group'] });
    },
    onError: error => {
      throw error;
    }
  });
  return mutate;
};
