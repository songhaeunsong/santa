package site.ssanta.santa.api.mountain_complete.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MountainCompleteVO {

    private Long id;
    private Long mountainId;
    private String mountainName;
    private String mountainImage;
}
