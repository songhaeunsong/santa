package site.ssanta.santa.api.member.dto;

import site.ssanta.santa.api.member.domain.Tier;

public interface UserInfoVO {

    String getEmail();
    String getNickname();
    String getProfileUrl();
    Tier getTier();
    Long getExp();
}
