package site.ssanta.santa.api.mountain.dto.open_ai;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MountainRecommendationDto {

    @ArraySchema(schema = @Schema(implementation = Coordinates.class))
    private List<Coordinates> path;
    private String recommendation;
}
