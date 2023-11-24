package edu.hw6.task5;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class HackerNews {
    private static final URI BASE_URI = URI.create("https://hacker-news.firebaseio.com/v0/");
    private static final HttpRequest TOP_STORIES_REQUEST = HttpRequest
            .newBuilder()
            .uri(BASE_URI.resolve("topstories.json"))
            .build();

    private static final Pattern TITLE_EXTRACTOR = Pattern.compile("\"title\":\"([^\"]*)\"");
    private static final int STATUS_CODE_OK = 200;

    private final HttpClient client;

    public HackerNews() {
        this(HttpClient.newHttpClient());
    }

    public HackerNews(HttpClient client) {
        this.client = client;
    }

    public long[] hackerNewsTopStories() {
        try {
            HttpResponse<String> response = client.send(TOP_STORIES_REQUEST, HttpResponse.BodyHandlers.ofString());
            return Stream.of(response.body().split("[,\\[\\]]"))
                    .skip(1)
                    .mapToLong(Long::parseLong)
                    .toArray();
        } catch (IOException | InterruptedException e) {
            return new long[0];
        }
    }

    public String news(long id) {
        try {
            HttpRequest request = HttpRequest.newBuilder(BASE_URI.resolve("item/" + id + ".json")).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != STATUS_CODE_OK) {
                return null;
            }

            Matcher matcher = TITLE_EXTRACTOR.matcher(response.body());
            return matcher.find() ? matcher.group(1) : null;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
