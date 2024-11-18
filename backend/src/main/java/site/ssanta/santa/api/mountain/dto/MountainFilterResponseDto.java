package site.ssanta.santa.api.mountain.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MountainFilterResponseDto {

    @ArraySchema(schema = @Schema(implementation = MountainQueryResponseDto.class))
    private List<MountainQueryResponseDto> mountains;
}
