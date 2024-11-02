package com.example.multiagent;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.MediaType;
import java.util.*;

public class UseCaseGeneratorAgent {
    private static final String OPENAI_API_KEY = "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";
    private static final String COHERE_API_KEY = "GEpKT3zXX1yoMnVn064Sk6Vgizu2zQg7OgdQyvnr";
    private static final String COHERE_API_URL = "https://api.cohere.ai/v1/generate";

    public String generateUseCase(String prompt) throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        int retryCount = 0;
        int maxRetries = 5;
        int backoffDelay = 1000;

        while (retryCount < maxRetries) {
            JSONObject jsonBody = new JSONObject()
                    .put("model", "gpt-3.5-turbo")
                    .put("messages", new JSONArray().put(new JSONObject()
                            .put("role", "user")
                            .put("content", prompt)))
                    .put("temperature", 0.7);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), jsonBody.toString());
            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/chat/completions")
                    .post(body)
                    .addHeader("Authorization", "Bearer " + OPENAI_API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    String generatedText = jsonResponse
                            .getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content");

                    return generatedText;
                } else if (response.code() == 429) {
                    retryCount++;
                    System.out.println("Rate limit hit. Retrying in " + backoffDelay + " ms...");
                    Thread.sleep(backoffDelay);
                    backoffDelay *= 2;
                } else {
                    System.err.println("Request failed with status: " + response.code());
                    return "Request failed: " + response.message();
                }
            }
        }
        return "Failed to generate use case after multiple attempts.";
    }

    public String generateUseCaseUsingCohere(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();
        JSONObject jsonBody = new JSONObject()
                .put("prompt", prompt)
                .put("model", "command")
                .put("max_tokens", 200)
                .put("temperature", 0.7);
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), jsonBody.toString());
        Request request = new Request.Builder()
                .url(COHERE_API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + COHERE_API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                String generatedText = jsonResponse.getJSONArray("generations")
                        .getJSONObject(0)
                        .getString("text");

                return generatedText.trim();
            } else {
                System.err.println("Request failed: " + response);
                return "Error: Unable to generate use cases.";
            }
        }
    }

    public String generateUseCaseWithHuggingFace(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();
        JSONObject jsonBody = new JSONObject()
                .put("inputs", prompt);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonBody.toString());

        Request request = new Request.Builder()
                .url("https://api-inference.huggingface.co/models/ronakkedia/multiagent") // Replace with the model endpoint
                .post(body)
                .addHeader("Authorization", "Bearer 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") // Replace with your Hugging Face API key
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful() && response.body() != null) {
            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);
            return jsonResponse.getJSONArray("generated_text").getString(0);
        } else {
            System.err.println("Request failed: " + response);
            return null;
        }
    }

}