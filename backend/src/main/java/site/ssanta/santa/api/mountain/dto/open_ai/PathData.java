package site.ssanta.santa.api.mountain.dto.open_ai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PathData {

    private MountainAttributes attributes;
    private Coordinates geometry;
    private String fileType;
}
