package com.example.multiagent;

import okhttp3.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;
import java.io.*;

public class DatasetCollectorAgent {
    public List<String> searchDatasets1(String useCases) throws IOException {
        useCases = "investment banking";
        String query = useCases + " dataset site:kaggle.com";
        String url = "https://api.serper.dev/search?q=" + query;

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        request.setHeader("X-API-Key", "2b3d2fd061c040bb0acd0c13320fb97a");

        String response = EntityUtils.toString(client.execute(request).getEntity());
        client.close();

        List<String> datasetLinks = new ArrayList<>();
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray organicResults = jsonResponse.optJSONArray("organic");
        if (organicResults != null) {
            for (int i = 0; i < organicResults.length(); i++) {

                datasetLinks.add(organicResults.getJSONObject(i).getString("link"));
            }
        } else {
            System.err.println("No organic results found.");
        }

        return datasetLinks;
    }

    public List<String> searchDatasets(String useCases, String companyNameOrIndustry) throws IOException {
        String query = companyNameOrIndustry + " dataset site:kaggle.com";
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"q\":\"" + query + "\"}");

        Request request = new Request.Builder()
                .url("https://google.serper.dev/search")
                .method("POST", body)
                .addHeader("X-API-KEY", "a104f57ca4bc2ae62eb1fc22db1333a059b83d4b")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        List<String> datasetLinks = new ArrayList<>();

        if (response.isSuccessful() && response.body() != null) {
            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray organicResults = jsonResponse.optJSONArray("organic");


            if (organicResults != null) {
                for (int i = 0; i < organicResults.length(); i++) {
                    datasetLinks.add(organicResults.getJSONObject(i).getString("link"));
                }
            }
        } else {
            System.err.println("Request failed: " + response.message());
        }

        response.close();
        return datasetLinks;
    }
}


