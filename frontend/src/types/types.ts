// group

type roleType = 'ADMIN' | 'PARTICIPANT';

export interface Group {
  name: string;
  id: number;
  description: string;
  exp: number;
  adminId: number;
  countOfMembers: number;
}

export interface GroupDetail {
  id: number;
  name: string;
  exp: number;
  adminId: number;
  adminName: string;
  description: string;
  countOfMembers: number;
  isAdmin: boolean;
  isMember: boolean;
  participants: [
    {
      memberId: number;
      nickname: string;
      role: roleType;
    }
  ];
}

//mountain appointment

export interface Appointment {
  id: number;
  groupId: number;
  mountainName: string;
  mountainId: number;
  countOfParticipant: number;
  meetAt: string;
}

// member

type tierType = 'BRONZE' | 'SILVER' | 'GOLD' | 'DIAMOND' | 'CHALLENGER';

export interface Member {
  exp: number;
  profileUrl: string;
  tier: tierType;
  nickname: string;
  email: string;
}
