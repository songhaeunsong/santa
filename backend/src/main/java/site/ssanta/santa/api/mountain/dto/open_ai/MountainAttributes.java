package site.ssanta.santa.api.mountain.dto.open_ai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MountainAttributes {

    @JsonProperty("FID")
    private Long fid;
    @JsonProperty("PMNTN_SN")
    private Long pmtnSn;
    @JsonProperty("MNTN_CODE")
    private String mntnCode;
    @JsonProperty("MNTN_NM")
    private String mntnNm;
    @JsonProperty("PMNTN_NM")
    private String pmtnNm;
    @JsonProperty("PMNTN_LT")
    private Double pmtnLt;
    @JsonProperty("PMNTN_DFFL")
    private String pmtnDffl;
    @JsonProperty("MANAGE_SP2")
    private String manageSp2;
}
