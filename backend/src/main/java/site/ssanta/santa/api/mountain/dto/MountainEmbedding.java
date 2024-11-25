package site.ssanta.santa.api.mountain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
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

    @JsonProperty("mountain_name")
    private String mountainName;

    @JsonProperty("path_name")
    private String pathName;

    private String difficulty;
    private double length;
    private String text;
    private List<Double> embedding;

    @JsonProperty("path_data")
    private PathData pathData;
}
