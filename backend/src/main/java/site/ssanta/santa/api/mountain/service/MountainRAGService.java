package site.ssanta.santa.api.mountain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import site.ssanta.santa.api.mountain.dto.MountainEmbedding;
import site.ssanta.santa.api.mountain.dto.MountainSimilarity;
import site.ssanta.santa.api.mountain.dto.RouteRecommendation;
import site.ssanta.santa.api.mountain.dto.RouteRequest;
import site.ssanta.santa.common.open_ai.OpenAIClient;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public RouteRecommendation recommendRoute(RouteRequest request) {
        String userQuery = String.format(
                "Location: %s, looking for %s difficulty level hiking trail",
                request.getUserLocation().getAddress(),
                request.getDifficulty()
        );

        List<Double> queryEmbedding = openAIClient.createEmbedding(userQuery);

        // 위치와 유사도를 모두 고려하여 경로 찾기
        MountainEmbedding bestPath = embeddings.stream()
                .map(emb -> {
                    double similarity = cosineSimilarity(queryEmbedding, emb.getEmbedding());

                    // coordinates의 첫 번째 좌표를 시작점으로 사용
                    double distance = emb.getPathData().getGeometry().getCoordinates() != null
                            && !emb.getPathData().getGeometry().getCoordinates().isEmpty() ?
                            calculateDistance(
                                    request.getUserLocation().getLatitude(),
                                    request.getUserLocation().getLongitude(),
                                    emb.getPathData().getGeometry().getCoordinates().get(0).get(0),  // 시작점 위도
                                    emb.getPathData().getGeometry().getCoordinates().get(0).get(1)   // 시작점 경도
                            ) : Double.MAX_VALUE;

                    double distanceScore = Math.max(0, 1 - (distance / MAX_DISTANCE));
                    double combinedScore = (similarity * 0.6) + (distanceScore * 0.4);

                    log.info("Trail: {}, Similarity: {}, Distance: {}km, Combined Score: {}",
                            emb.getMountainName(), similarity, distance, combinedScore);

                    return new MountainSimilarity(emb, combinedScore);
                })
                .max(Comparator.comparingDouble(MountainSimilarity::getSimilarity))
                .map(MountainSimilarity::getEmbedding)
                .orElseThrow(() -> new RuntimeException("No suitable path found"));

        String context = String.format(
                "Found this hiking trail:\nMountain: %s\nDifficulty: %s\nLength: %.2f km",
                bestPath.getMountainName(),
                bestPath.getDifficulty(),
                bestPath.getLength()
        );

        String recommendation = openAIClient.createChatCompletion(context, userQuery);

        return new RouteRecommendation(List.of(bestPath), recommendation);
    }

//    public RouteRecommendation recommendRoute(RouteRequest request) {
//        String userPrompt = String.format(
//                "Looking for hiking trails near latitude, longitude %s with %s difficulty level using " +
//                        "mountain_embeddings.json file in one mountain and can climb to the peek of that mountain.",
//                request.getUserLocation().getLatitude() + ", " + request.getUserLocation().getLongitude(),
//                request.getDifficulty()
//        );
//
//        List<Double> queryEmbedding = openAIClient.createEmbedding(userPrompt);
//
//        List<MountainEmbedding> relevantPaths = embeddings.stream()
//                .map(emb -> new MountainSimilarity(emb, cosineSimilarity(queryEmbedding, emb.getEmbedding())))
//                .sorted(Comparator.comparingDouble(MountainSimilarity::getSimilarity).reversed())
//                .limit(3)
//                .map(MountainSimilarity::getEmbedding)
//                .toList();
//
//        String context = "Here are the found hiking trails:\n\n" + relevantPaths.stream()
//                .map(emb -> String.format(
//                        "- Mountain: %s\n  Trail: %s\n  Difficulty: %s\n  Length: %.2fkm",
//                        emb.getMountainName(),
//                        emb.getPathName(),
//                        emb.getDifficulty(),
//                        emb.getLength()
//                ))
//                .collect(Collectors.joining("\n\n"));
//
//        String recommendation = openAIClient.createChatCompletion(context, userPrompt);
//
//        return new RouteRecommendation(relevantPaths, recommendation);
//    }

    private double cosineSimilarity(List<Double> vec1, List<Double> vec2) {
        if (vec1.size() != vec2.size()) {
            log.error("Vector size mismatch: {} vs {}", vec1.size(), vec2.size());
            throw new IllegalArgumentException("Vectors must be of the same size");
        }

        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (int i = 0; i < vec1.size(); i++) {
            dotProduct += vec1.get(i) * vec2.get(i);
            norm1 += vec1.get(i) * vec1.get(i);
            norm2 += vec2.get(i) * vec2.get(i);
        }

        if (norm1 == 0.0 || norm2 == 0.0) {
            log.warn("Zero vector detected");
            return 0.0;
        }

        double similarity = dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));

        if (Double.isNaN(similarity) || Double.isInfinite(similarity)) {
            log.error("Invalid similarity value: {}", similarity);
            return 0.0;
        }

        return similarity;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return EARTH_RADIUS * c;
    }
}
