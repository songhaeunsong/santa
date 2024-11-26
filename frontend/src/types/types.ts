// group

import { ComputedRef } from 'vue';

type roleType = 'ADMIN' | 'PARTICIPANT';

export interface Group {
  name: string;
  id: number;
  description: string;
  exp: number;
  adminId: number;
  countOfMembers: number;
}

export interface GroupRank {
  rank: Group[];
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
  id: number;
  exp: number;
  profileUrl: string;
  tier: tierType;
  nickname: string;
  email: string;
}

export interface MemberDetail extends Member {
  isSelf: boolean;
  completes: Complete[];
}

export interface MemberRank {
  rank: Member[];
}

// mountain
export interface Mountain {
  name: string;
  id: number;
  province: string;
  city: string;
  height: number;
  isLiked: boolean;
  latitude: number;
  longitude: number;
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

export interface Complete {
  id: number;
  mountainId: number;
  mountainName: string;
  mountainImage: string;
}

type Coord = number[][];
export interface Geometry {
  coordinates: Coord[];
  point: any;
  type: string;

  geometry: string;
}

export interface MountainDetail {
  info: MountainInfo;
  paths: MountainPath[];
  spots: MountainSpots[];
  mountainInfo: Mountain;
  isCompletedToday: boolean;
}

export interface MountainInfo {
  id: string;
  mountainCode: string;
  mountainName: string;
}

export interface MountainPath {
  id: string;
  attributes: {
    mountainCode: string;
    mountainName: string;
    pathName: string;
    difficulty: string;
  };
  geometry: Geometry;
  fileType: string;
}

export interface MountainSpots {
  id: string;
  attributes: {
    mountainCode: string;
    mountainName: string;
    pathName: string;
    difficulty: string;
  };
  geometry: Geometry;
  fileType: string;
}

export interface Attraction {
  typeId: number;
  title: string;
  address: string;
  image: string;
  city: string;
  province: string;
  longitude: number;
  latitude: number;
  contentId: number;
  mapLevel: number;
}
export interface Attractions {
  attractions: Attraction[];
}

export interface Medalist {
  first: string | ComputedRef<string>;
  second: string | ComputedRef<string>;
  third: string | ComputedRef<string>;
}
