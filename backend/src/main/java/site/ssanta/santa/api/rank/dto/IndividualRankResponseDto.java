package site.ssanta.santa.api.rank.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import site.ssanta.santa.api.member.dto.MemberInfoVO;

import java.util.List;

@Getter
@AllArgsConstructor
public class IndividualRankResponseDto {

    @ArraySchema(schema = @Schema(implementation = MemberInfoVO.class))
    List<MemberInfoVO> rank;
}
