package site.ssanta.santa.api.member.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import site.ssanta.santa.api.member.domain.Tier;
import site.ssanta.santa.api.mountain_complete.dto.MountainCompleteVO;

import java.util.List;

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

    @ArraySchema(schema = @Schema(implementation = MountainCompleteVO.class))
    private List<MountainCompleteVO> completes;
}
