package site.ssanta.santa.api.tourist_attraction.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TouristQueryResponseDto {

    @ArraySchema(schema = @Schema(implementation = TouristAttractionVO.class))
    List<TouristAttractionVO> attractions;
}
