package site.ssanta.santa.api.rank.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import site.ssanta.santa.api.group.dto.GroupVO;

import java.util.List;

@Getter
@AllArgsConstructor
public class GroupRankResponseDto {

    @ArraySchema(schema = @Schema(implementation = GroupVO.class))
    List<GroupVO> rank;
}
