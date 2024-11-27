package site.ssanta.santa.common.open_ai;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OpenAIClient {

    private final String apiKey;
    private final ObjectMapper objectMapper;

    private static final String OPENAI_URL = "https://api.openai.com/v1";
    private final OkHttpClient client = new OkHttpClient();

    public OpenAIClient(@Value("${GPT.KEY}") String apiKey) {
        this.apiKey = apiKey;
        this.objectMapper = new ObjectMapper();
    }

    public List<Double> createEmbedding(String text) {
        try {
            log.info("Creating embedding for text: {}", text);

            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"),
                    objectMapper.writeValueAsString(Map.of(
                            "model", "text-embedding-3-small",
                            "input", text
                    ))
            );

            Request request = new Request.Builder()
                    .url(OPENAI_URL + "/embeddings")
                    .header("Authorization", "Bearer " + apiKey)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            log.info("OpenAI API Response: {}", responseBody);

            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return objectMapper.convertValue(
                    jsonNode.get("data").get(0).get("embedding"),
                    new TypeReference<List<Double>>() {}
            );
        } catch (Exception e) {
            log.error("Failed to create embedding", e);
            throw new RuntimeException("Failed to create embedding", e);
        }
    }

    public String createChatCompletion(String context, String prompt) {
        try {
            Map<String, Object> requestBody = Map.of(
                    "model", "gpt-3.5-turbo",  // GPT-3.5 모델로 변경
                    "messages", List.of(
                            Map.of("role", "system", "content",
                                    "You are a Korean hiking expert. Provide recommendations in Korean."),
                            Map.of("role", "user", "content", context + "\n" + prompt)
                    ),
                    "temperature", 0.7
            );

            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"),
                    objectMapper.writeValueAsString(requestBody)
            );

            Request request = new Request.Builder()
                    .url(OPENAI_URL + "/chat/completions")
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            JsonNode jsonNode = objectMapper.readTree(response.body().string());

            return jsonNode.get("choices").get(0).get("message").get("content").asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate chat completion", e);
        }
    }
}
