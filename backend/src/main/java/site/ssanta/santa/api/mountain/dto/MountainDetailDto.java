package site.ssanta.santa.api.mountain.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import site.ssanta.santa.api.mountain.domain.MountainPath;
import site.ssanta.santa.api.mountain.domain.MountainInfo;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MountainDetailDto {

    private MountainInfo info;

    @ArraySchema(schema = @Schema(implementation = MountainPath.class))
    private List<MountainPath> paths;
    private MountainQueryResponseDto mountainInfo;

    @Setter
    private Boolean isCompletedToday;
}
