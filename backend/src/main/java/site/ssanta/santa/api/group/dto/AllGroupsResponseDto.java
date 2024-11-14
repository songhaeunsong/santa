package site.ssanta.santa.api.group.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AllGroupsResponseDto {

    @ArraySchema(schema = @Schema(implementation = GroupVO.class))
    private List<GroupVO> groups;
}
