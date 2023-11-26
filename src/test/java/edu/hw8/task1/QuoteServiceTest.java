package edu.hw8.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class QuoteServiceTest {
    @Test
    @DisplayName("Single client")
    void singleClient() throws InterruptedException {
        Server server = new QuoteServer();
        Thread thread = new Thread(server::start);
        thread.start();
        try (Client client = new QuoteClient()) {
            String keyword = "интеллект";

            client.send(keyword);
            String response = client.waitResponse();

            assertThat(response).isEqualTo("Чем ниже интеллект, тем громче оскорбления");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.shutdown();
        thread.join();
    }

    @Test
    @DisplayName("Multiple clients")
    void multipleClients() throws InterruptedException, ExecutionException {
        Server server = new QuoteServer();
        Thread thread = new Thread(server::start);
        thread.start();
        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            String keyword = "интеллект";

            Future<String> request1 = executorService.submit(() -> {
                try (Client client = new QuoteClient()) {
                    client.send(keyword);
                    return client.waitResponse();
                }
            });
            Future<String> request2 = executorService.submit(() -> {
                try (Client client = new QuoteClient()) {
                    client.send(keyword);
                    return client.waitResponse();
                }
            });

            assertThat(request1.get()).isEqualTo("Чем ниже интеллект, тем громче оскорбления");
            assertThat(request2.get()).isEqualTo("Чем ниже интеллект, тем громче оскорбления");
        }
        server.shutdown();
        thread.join();
    }

    @Test
    @DisplayName("More clients than threads")
    void moreClientsThanThreads() throws InterruptedException {
        Server server = new QuoteServer();
        Thread thread = new Thread(server::start);
        thread.start();

        try (ExecutorService executorService = Executors.newFixedThreadPool(10)) {
            Stream.generate(() -> (Callable<String>) () -> {
                        try (Client client = new QuoteClient()) {
                            client.send("личности");
                            return client.waitResponse();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .limit(10)
                    .map(executorService::submit)
                    .map(stringFuture -> {
                        try {
                            return stringFuture.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .forEach(response -> assertThat(response).isEqualTo("Не переходи на личности там, где их нет"));
        }

        server.shutdown();
        thread.join();
    }

    @Test
    @DisplayName("No quotes found")
    void noQuotesFound() throws InterruptedException {
        Server server = new QuoteServer();
        Thread thread = new Thread(server::start);
        thread.start();
        try (Client client = new QuoteClient()) {
            String keyword = "no quotes for this keyword";

            client.send(keyword);
            String response = client.waitResponse();

            assertThat(response).isEqualTo("No quotes found for this keyword");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.shutdown();
        thread.join();
    }
}
