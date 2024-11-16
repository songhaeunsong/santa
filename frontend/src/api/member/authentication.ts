import { useRouter } from 'vue-router';
import apiClient from '../apiClient';
import { useMutation, useQuery } from '@tanstack/vue-query';
import { setLoginStatus } from '../../utils/loginUtil';

interface PostCodeResponse {
  isNew: boolean;
}

interface GetCheckNicknameResponse {
  isDuplicated: boolean;
}

const postCode = async (code: string) => {
  return (await apiClient.post<PostCodeResponse>('/member/auth', { code }))
    .data;
};

export const usePostCodeApi = () => {
  const router = useRouter();

  const { mutateAsync } = useMutation({
    mutationFn: (code: string) => postCode(code),
    onSuccess: data => {
      if (data.isNew) {
        router.push('/login/init');
      } else {
        router.push('/');
      }
    },
    onError: () => {
      router.push('/login');
    }
  });

  return mutateAsync;
};

const getCheckNickname = async (
  nickname: string
): Promise<GetCheckNicknameResponse> => {
  return (
    await apiClient.get<GetCheckNicknameResponse>('/member/check-nickname', {
      params: { nickname }
    })
  ).data;
};

export const useGetCheckNickname = () => {
  const { mutateAsync } = useMutation<GetCheckNicknameResponse, Error, string>({
    mutationFn: getCheckNickname
  });
  return mutateAsync;
};

const postNickname = async (nickname: string): Promise<void> => {
  await apiClient.post('/member/nickname', {
    nickname
  });
};

export const usePostNickname = () => {
  const { mutateAsync } = useMutation<void, Error, string>({
    mutationFn: (nickname: string) => postNickname(nickname),
    onError: error => {
      throw error;
    }
  });
  return mutateAsync;
};

const getLogout = async () => {
  await apiClient.get('/member/logout');
};

export const useGetLogout = () => {
  const router = useRouter();

  return useMutation({
    mutationFn: () => getLogout(),
    onSuccess: () => {
      setLoginStatus(false);
      router.push('/');
    },
    onError: error => {
      throw error;
    }
  });
};

type SuccessResponse = '';

type ErrorResponse = {
  status: number;
  code: string;
};

export type ResponseType = SuccessResponse | ErrorResponse;

export const getIsActive = async () =>
  await apiClient.get<ResponseType>('/member/active');

export const useGetIsActive = () =>
  useQuery({
    queryKey: ['isActive'],
    queryFn: () => getIsActive(),
    staleTime: 1000 * 60 * 4
  });
