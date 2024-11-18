package site.ssanta.santa.api.mountain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MountainQueryResponseDto {

    private Long id;
    private String name;
    private String province;
    private String city;
    private Integer height;
    private String latitude;
    private String longitude;
    private String image;
    private Boolean isLiked;
}
