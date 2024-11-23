package site.ssanta.santa.api.mountain_complete.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MountainCompleteResponseDto {

    @ArraySchema(schema = @Schema(implementation = MountainCompleteVO.class))
    List<MountainCompleteVO> completes;
}
