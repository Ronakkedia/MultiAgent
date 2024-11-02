package com.example.multiagent;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class ResearchAgent {
    private static final String SERPER_API_KEY = "a104f57ca4bc2ae62eb1fc22db1333a059b83d4b";

    public List<String> fetchIndustryTrends(String companyNameOrIndustry) throws IOException {

        String query = companyNameOrIndustry;
        OkHttpClient client = new OkHttpClient();

        String jsonBody = new JSONObject().put("q", query).toString();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonBody);

        Request request = new Request.Builder()
                .url("https://google.serper.dev/search") // Updated URL
                .post(body)
                .addHeader("X-API-KEY", SERPER_API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        List<String> industryTrends = new ArrayList<>();
        if (response.isSuccessful() && response.body() != null) {
            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray organicResults = jsonResponse.optJSONArray("organic");
            for (int i = 0; i < organicResults.length(); i++) {
                industryTrends.add(organicResults.getJSONObject(i).optString("snippet"));
            }
        } else {
            System.err.println("Request failed: " + response);
        }
        response.close();
        return industryTrends;
    }
}