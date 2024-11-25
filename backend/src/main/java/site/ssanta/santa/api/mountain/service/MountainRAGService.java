package site.ssanta.santa.api.mountain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import site.ssanta.santa.api.mountain.dto.open_ai.MountainEmbedding;
import site.ssanta.santa.api.mountain.dto.open_ai.RouteRecommendation;
import site.ssanta.santa.common.open_ai.OpenAIClient;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MountainRAGService {

    private final OpenAIClient openAIClient;
    private final ObjectMapper objectMapper;
    private static final double EARTH_RADIUS = 6371;
    private static final double MAX_DISTANCE = 10.0;

    @Value("${EMBEDDING.FILE.PATH}")
    private String embeddingFilePath;
    private List<MountainEmbedding> embeddings;

    @PostConstruct
    public void loadEmbeddings() {
        log.debug("Starting to load embeddings from JSON...");
        try {
            Resource resource = new ClassPathResource(embeddingFilePath);

            if (!resource.exists()) {
                throw new FileNotFoundException("Embedding file not found at: " + embeddingFilePath);
            }

            try (InputStream is = resource.getInputStream()) {
                embeddings = objectMapper.readValue(is,
                        objectMapper.getTypeFactory().constructCollectionType(
                                List.class,
                                MountainEmbedding.class
                        )
                );
                log.debug("Successfully loaded {} embeddings from JSON", embeddings.size());
            }

        } catch (Exception e) {
            log.error("Failed to load embeddings: {}", e.getMessage());
            throw new RuntimeException("Failed to load embeddings: " + e.getMessage(), e);
        }
    }

    public RouteRecommendation recommendRoute(Long mountainCode, String difficulty) {
        List<MountainEmbedding> suitablePaths = embeddings.stream()
                .filter(emb -> emb.getMountainCode().equals(mountainCode))
                .filter(emb -> emb.getPathData().getAttributes().getPmtnDffl().equals(difficulty))
                .toList();

        if (suitablePaths.isEmpty()) {
            throw new RuntimeException("No suitable paths found");
        }

        String context = buildContextFromPaths(suitablePaths);
        String recommendation = openAIClient.createChatCompletion(context, "Recommend the best summit trail route");

        return new RouteRecommendation(suitablePaths, recommendation);
    }

    private String buildContextFromPaths(List<MountainEmbedding> paths) {
        StringBuilder context = new StringBuilder();
        context.append("Possible summit trail routes:\n\n");

        for (MountainEmbedding path : paths) {
            context.append(String.format(
                    "Trail: %s\nDifficulty: %s\nLength: %.2f km\n\n",
                    path.getPathData().getAttributes().getPmtnNm(),
                    path.getPathData().getAttributes().getPmtnDffl(),
                    path.getPathData().getAttributes().getPmtnLt()
            ));
        }

        return context.toString();
    }
}
