package site.ssanta.santa.api.group.dto;

import lombok.*;
import site.ssanta.santa.api.group_participant.domain.Role;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipantInfo {

    private Long memberId;
    private String nickname;
    private Role role;
}
