package site.ssanta.santa.api.mountain.dto;

import lombok.Getter;

@Getter
public class RouteRequest {

    private UserLocation userLocation;
    private String difficulty;
}
