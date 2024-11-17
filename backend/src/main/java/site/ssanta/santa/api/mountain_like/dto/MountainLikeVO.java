package site.ssanta.santa.api.mountain_like.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MountainLikeVO {

    private Long id;
    private Long mountainId;
    private String mountainName;
    private String mountainImage;
}
