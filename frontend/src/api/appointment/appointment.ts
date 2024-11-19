import { useMutation, useQuery, useQueryClient } from '@tanstack/vue-query';
import apiClient from '../apiClient';
import { Appointments } from '../../types/types';

const getAppointmentList = async (groupId: number, date: string) =>
  (
    await apiClient.get<Appointments>('appointment/list', {
      params: { groupId, date }
    })
  ).data;

export const useGetAppointmentList = (groupId: number, date: string) =>
  useQuery({
    queryKey: ['appointments', groupId, date],
    queryFn: () => getAppointmentList(groupId, date),
    enabled: !!groupId && !!date,
    staleTime: 1000 * 60 * 1
  });

interface NewAppointment {
  groupId: number;
  mountainId: number;
  date: string;
}

const postAppointment = async (newAppointment: NewAppointment) =>
  (await apiClient.post('appointment', newAppointment)).data;

export const usePostAppointment = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (newAppointment: NewAppointment) =>
      postAppointment(newAppointment),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['appointments'] });
    },
    onError: error => {
      throw error;
    }
  });
};

const postAppointmentJoin = async (groupId: number, appointmentId: number) =>
  (await apiClient.post('appointment/join', { groupId, appointmentId })).data;

export const usePostAppointmentJoin = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({
      groupId,
      appointmentId
    }: {
      groupId: number;
      appointmentId: number;
    }) => postAppointmentJoin(groupId, appointmentId),
    onSuccess: (_, variables) => {
      queryClient.invalidateQueries({
        queryKey: ['appointments', variables.appointmentId]
      });
    }
  });
};

const postAppointmentCancelJoin = async (appointmentId: number) => {
  await apiClient.delete('appointment/join', { data: { appointmentId } });
};

export const usePostAppointmentCancelJoin = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (appointmentId: number) =>
      postAppointmentCancelJoin(appointmentId),
    onSuccess: (_, appointmentId) => {
      queryClient.invalidateQueries({
        queryKey: ['appointments', appointmentId]
      });
    }
  });
};
