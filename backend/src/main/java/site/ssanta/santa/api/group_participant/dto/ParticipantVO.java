package site.ssanta.santa.api.group_participant.dto;

import site.ssanta.santa.api.group_participant.domain.Role;

public interface ParticipantVO {

    Long getMemberId();
    Role getRole();
}
