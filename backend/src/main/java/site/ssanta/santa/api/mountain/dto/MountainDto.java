package site.ssanta.santa.api.mountain.dto;

import lombok.*;
import site.ssanta.santa.api.mountain.domain.MountainFeature;
import site.ssanta.santa.api.mountain.domain.MountainInfo;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MountainDto {

    private MountainInfo info;
    private List<MountainFeature> paths;
    private List<MountainFeature> spots;
}
