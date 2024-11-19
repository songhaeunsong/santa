package site.ssanta.santa.api.mountain.domain;

import java.math.BigDecimal;
import java.util.List;

public class FeatureGeometry {

    private String type;
    private List<List<List<BigDecimal>>> coordinates;
    private List<BigDecimal> point;
}
