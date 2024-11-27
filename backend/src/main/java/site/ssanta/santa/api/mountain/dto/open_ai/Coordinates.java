package site.ssanta.santa.api.mountain.dto.open_ai;

import lombok.Getter;

import java.util.List;

@Getter
public class Coordinates {

    private String type;
    private List<List<List<Double>>> coordinates;
}
