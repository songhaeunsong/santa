package site.ssanta.santa.api.mountain.dto.open_ai;

import lombok.Getter;

@Getter
public class RouteRequest {

    private Long mountainCode;
    private String difficulty;
}
