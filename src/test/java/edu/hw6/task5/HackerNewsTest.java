package edu.hw6.task5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import static org.assertj.core.api.Assertions.assertThat;

class HackerNewsTest {
    @Test
    @DisplayName("Successful request")
    void successfulRequest() {
        HttpClient mockClient = new MockClient("[0,1,2,3]");
        HackerNews hackerNews = new HackerNews(mockClient);

        long[] topStories = hackerNews.hackerNewsTopStories();

        assertThat(topStories).contains(0, 1, 2, 3);
    }

    @Test
    @DisplayName("Failed request")
    void failedRequest() {
        HttpClient mockClient = new MockClient(null);
        HackerNews hackerNews = new HackerNews(mockClient);

        long[] topStories = hackerNews.hackerNewsTopStories();

        assertThat(topStories).isEmpty();
    }

    @Test
    @DisplayName("Find existing article")
    void findExisingArticle() {
        String news = new HackerNews().news(1);

        assertThat(news).isEqualTo("Y Combinator");
    }

    @Test
    @DisplayName("Find nonexistent article")
    void findNonexistentArticle() {
        String news = new HackerNews().news(-1);

        assertThat(news).isNull();
    }

    private static class MockClient extends HttpClient {
        private final String body;

        MockClient(String body) {
            this.body = body;
        }

        @Override
        public Optional<CookieHandler> cookieHandler() {
            return Optional.empty();
        }

        @Override
        public Optional<Duration> connectTimeout() {
            return Optional.empty();
        }

        @Override
        public Redirect followRedirects() {
            return null;
        }

        @Override
        public Optional<ProxySelector> proxy() {
            return Optional.empty();
        }

        @Override
        public SSLContext sslContext() {
            return null;
        }

        @Override
        public SSLParameters sslParameters() {
            return null;
        }

        @Override
        public Optional<Authenticator> authenticator() {
            return Optional.empty();
        }

        @Override
        public Version version() {
            return null;
        }

        @Override
        public Optional<Executor> executor() {
            return Optional.empty();
        }

        @Override
        public <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler)
                throws IOException {
            if (body == null) {
                throw new IOException();
            } else {
                return new MockResponse<>(body);
            }
        }

        @Override
        public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request,
                                                                HttpResponse.BodyHandler<T> responseBodyHandler) {
            return null;
        }

        @Override
        public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request,
                                                                HttpResponse.BodyHandler<T> responseBodyHandler,
                                                                HttpResponse.PushPromiseHandler<T> pushPromiseHandler) {
            return null;
        }
    }

    private static class MockResponse<T> implements HttpResponse<T> {
        private final String body;

        private MockResponse(String body) {
            this.body = body;
        }

        @Override
        public int statusCode() {
            return 0;
        }

        @Override
        public HttpRequest request() {
            return null;
        }

        @Override
        public Optional<HttpResponse<T>> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            return null;
        }

        @Override
        public T body() {
            return (T) body;
        }

        @Override
        public Optional<SSLSession> sslSession() {
            return Optional.empty();
        }

        @Override
        public URI uri() {
            return null;
        }

        @Override
        public HttpClient.Version version() {
            return null;
        }
    }
}
