package site.ssanta.santa.api.member.dto;

import lombok.*;
import site.ssanta.santa.api.member.domain.Tier;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberProfileResponseDto {

    private String email;
    private String nickname;
    private String profileUrl;
    private Tier tier;
    private Long exp;
    private Boolean isSelf;
    // TODO: 완등 정보
}
