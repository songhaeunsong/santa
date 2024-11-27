package site.ssanta.santa.api.mountain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import site.ssanta.santa.api.mountain.dto.open_ai.MountainAttributes;
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
        String prompt = """
        위의 등산로들 중에서 가장 적절한 등산로를 찾아주세요:
        1. 산 입구/시작점에서 출발하는 경로
        2. 정상까지 이어지는 경로
        3. 하나의 연결된 코스로 추천
        4. 경로의 시작점과 종점을 명확히 표시
        5. 정상까지 가는 최적의 경로를 선택한 이유도 설명
        """;

        String recommendation = openAIClient.createChatCompletion(context, prompt);
        return new RouteRecommendation(suitablePaths, recommendation);
    }

    private String buildContextFromPaths(List<MountainEmbedding> paths) {
        StringBuilder context = new StringBuilder();
        context.append("등산로 정보:\n\n");

        for (MountainEmbedding path : paths) {
            MountainAttributes attrs = path.getPathData().getAttributes();
            List<List<Double>> coordinates = path.getPathData().getGeometry().getCoordinates().get(0);

            context.append(String.format(
                    "구간: %s\n난이도: %s\n길이: %.2f km\n" +
                            "시작 좌표: (위도: %f, 경도: %f)\n" +
                            "끝 좌표: (위도: %f, 경도: %f)\n\n",
                    attrs.getPmtnNm(),
                    attrs.getPmtnDffl(),
                    attrs.getPmtnLt(),
                    coordinates.get(0).get(0),            // 시작점 위도
                    coordinates.get(0).get(1),            // 시작점 경도
                    coordinates.get(coordinates.size()-1).get(0),  // 끝점 위도
                    coordinates.get(coordinates.size()-1).get(1)   // 끝점 경도
            ));
        }

        return context.toString();
    }
}
