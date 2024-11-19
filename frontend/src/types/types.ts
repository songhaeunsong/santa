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
  countOfMembers: number;
  meetAt: string;
  mountainName: string;
  isMember: boolean;
  isAdmin: boolean;
  members: [
    {
      id: number;
      name: string;
    }
  ];
}

export interface Appointments {
  appointments: Appointment[];
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

export interface Mountain {
  name: string;
  id: number;
  province: string;
  city: string;
  height: number;
  isLiked: boolean;
  latitude: string;
  longitude: string;
  image: string;
}

export interface Mountains {
  mountains: Mountain[];
}

export interface MountainLike {
  id: number;
  mountainId: number;
  mountainName: string;
  mountainImage: string;
}

export interface MountainLikes {
  mountainLikes: MountainLike[];
}
