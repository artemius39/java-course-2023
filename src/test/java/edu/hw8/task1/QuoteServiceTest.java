package edu.hw8.task1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

class QuoteServiceTest {
    private Server server;
    private Thread serverThread;

    @BeforeEach
    void startServer() {
        server = new QuoteServer();
        serverThread = new Thread(server::start);
        serverThread.start();
    }

    @AfterEach
    void stopServer() throws InterruptedException {
        server.shutdown();
        serverThread.join();
    }

    @Test
    @Timeout(30)
    @DisplayName("Single client")
    void singleClient() {
        try (Client client = new QuoteClient()) {
            String keyword = "интеллект";

            client.send(keyword);
            String response = client.waitResponse();

            assertThat(response).isIn("Чем ниже интеллект, тем громче оскорбления", "No response");
        } catch (IOException ignored) {
        }
    }

    @Test
    @Timeout(30)
    @DisplayName("Multiple clients")
    void multipleClients() throws InterruptedException {
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

            try {
                assertThat(request1.get()).isIn("Чем ниже интеллект, тем громче оскорбления", "No response");
            } catch (ExecutionException ignored) {
            }
            try {
                assertThat(request2.get()).isIn("Чем ниже интеллект, тем громче оскорбления", "No response");
            } catch (ExecutionException ignored) {
            }
        }
    }

    @Test
    @Timeout(30)
    @DisplayName("More clients than threads")
    void moreClientsThanThreads() throws InterruptedException {
        List<Future<String>> tasks = new ArrayList<>();
        try (ExecutorService executorService = Executors.newFixedThreadPool(10)) {
            for (int i = 0; i < 10; i++) {
                tasks.add(executorService.submit(() -> {
                    try (Client client = new QuoteClient()) {
                        client.send("личности");
                        return client.waitResponse();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }));
            }
        }

        for (Future<String> task : tasks) {
            try {
                assertThat(task.get()).isIn("Не переходи на личности там, где их нет", "No response");
            } catch (ExecutionException ignored) {
            }
        }
    }

    @Test
    @Timeout(30)
    @DisplayName("No quotes found")
    void noQuotesFound() {
        try (Client client = new QuoteClient()) {
            String keyword = "no quotes for this keyword";

            client.send(keyword);
            String response = client.waitResponse();

            assertThat(response).isIn("No quotes found for this keyword", "No response");
        } catch (IOException ignored) {
        }
    }
}
