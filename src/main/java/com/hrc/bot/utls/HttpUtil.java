package com.hrc.bot.utls;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class HttpUtil {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    /**
     * Perform a synchronous GET request.
     *
     * @param url URL of the resource to fetch
     * @param headers Map of header names to values
     * @return Response body as a String
     * @throws Exception If any error occurs during the request
     */
    public static String get(String url, Map<String, String> headers) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET();

        if (headers != null && !headers.isEmpty()) {
            headers.forEach(builder::header);
        }

        HttpRequest request = builder.build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return response.body();
        } else {
            throw new RuntimeException("Failed to perform GET request: " + response.statusCode());
        }
    }

    /**
     * Perform a synchronous POST request.
     *
     * @param url URL of the resource to post data to
     * @param body The body of the POST request as a String
     * @param headers Map of header names to values
     * @return Response body as a String
     * @throws Exception If any error occurs during the request
     */
    public static String  post(String url, String body, Map<String, String> headers) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(body));

        if (headers != null && !headers.isEmpty()) {
            headers.forEach(builder::header);
        }

        HttpRequest request = builder.build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return response.body();
        } else {
            throw new RuntimeException("Failed to perform POST request: " + response.statusCode());
        }
    }
}