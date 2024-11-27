import { getLoginStatus, setLoginStatus } from '../utils/loginUtil';
import { useGetIsActive } from '../api/member/authentication';

export function useAuth() {
  async function execute() {
    if (getLoginStatus()) {
      return;
    }

    const { refetch } = useGetIsActive();

    const response = await refetch();
    if (response.isSuccess) {
      setLoginStatus(true);
    } else {
      setLoginStatus(false);
    }
  }

  return { execute };
}
