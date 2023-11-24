package edu.project3;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.validator.routines.UrlValidator;

public class URLLogReader implements LogReader {
    private static final int STATUS_CODE_OK = 200;
    private final HttpClient client;

    public URLLogReader() {
        client = HttpClient.newHttpClient();
    }

    @Override
    public Optional<Stream<String>> readLogs(String source) {
        if (!UrlValidator.getInstance().isValid(source)) {
            return Optional.empty();
        }
        HttpRequest request = HttpRequest.newBuilder(URI.create(source)).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == STATUS_CODE_OK ? Optional.of(response.body().lines()) : Optional.empty();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        client.close();
    }
}
