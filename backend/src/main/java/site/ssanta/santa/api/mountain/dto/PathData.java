package site.ssanta.santa.api.mountain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PathData {

    private Map<String, Object> attributes;  // 원본 속성 정보
    private PathGeometry geometry;

    @Getter
    public class PathGeometry {
        private String type;
        private List<List<Double>> coordinates;
        private List<Double> point;
    }
}
