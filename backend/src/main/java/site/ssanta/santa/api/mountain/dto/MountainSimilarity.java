package site.ssanta.santa.api.mountain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MountainSimilarity {

    private MountainEmbedding embedding;
    private double similarity;
}
