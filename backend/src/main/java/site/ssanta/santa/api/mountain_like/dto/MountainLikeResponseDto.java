package site.ssanta.santa.api.mountain_like.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MountainLikeResponseDto {

    @ArraySchema(schema = @Schema(implementation = MountainLikeVO.class))
    private List<MountainLikeVO> mountainLikes;
}
