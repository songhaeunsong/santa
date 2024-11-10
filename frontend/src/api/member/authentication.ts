import { useRouter } from 'vue-router';
import apiClient from '../apiClient';
import { useMutation } from '@tanstack/vue-query';

interface PostCodeResponse {
  isNew: boolean;
}

interface GetCheckNicknameResponse {
  isDuplicated: boolean;
}

type NicknameProps = string;
type AuthCodeProps = string;

const postCode = async (code: AuthCodeProps): Promise<PostCodeResponse> => {
  return (await apiClient.post<PostCodeResponse>('/member/auth', { code }))
    .data;
};

export const usePostCodeApi = () => {
  const router = useRouter();

  const { mutateAsync } = useMutation<PostCodeResponse, Error, string>({
    mutationFn: (code: AuthCodeProps) => postCode(code),
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
  nickname: NicknameProps
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
