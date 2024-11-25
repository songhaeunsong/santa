package site.ssanta.santa.api.mountain.dto.open_ai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MountainEmbedding implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("mountain_code")
    private Long mountainCode;
    private String text;
    private List<Double> embedding;
    @JsonProperty("path_data")
    private PathData pathData;
}
