package site.ssanta.santa.api.tourist_attraction.dto;

import java.math.BigDecimal;

public interface TouristAttractionVO {

    Integer getContentId();
    String getTitle();
    String getImage();
    Integer getMapLevel();
    BigDecimal getLatitude();
    BigDecimal getLongitude();
    String getAddress();
    String getProvince();
    String getCity();
    Integer getTypeId();
}
