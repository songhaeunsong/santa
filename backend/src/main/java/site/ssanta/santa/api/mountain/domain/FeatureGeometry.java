package site.ssanta.santa.api.mountain.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class FeatureGeometry {

    private String type;
    private List<List<List<BigDecimal>>> coordinates;
    private List<BigDecimal> point;
}
