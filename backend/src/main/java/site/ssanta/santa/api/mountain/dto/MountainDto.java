package site.ssanta.santa.api.mountain.dto;

import lombok.*;
import site.ssanta.santa.api.mountain.domain.MountainPath;
import site.ssanta.santa.api.mountain.domain.MountainInfo;
import site.ssanta.santa.api.mountain.domain.MountainSpot;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MountainDto {

    private MountainInfo info;
    private List<MountainPath> paths;
    private List<MountainSpot> spots;
}
