package com.pfe.learningplatform.integration;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class AIClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateQuiz(String topic) {
        String url = "http://localhost:8000/generate-quiz";

        Map<String, String> request = Map.of("topic", topic);

        return restTemplate.postForObject(url, request, String.class);
    }
}