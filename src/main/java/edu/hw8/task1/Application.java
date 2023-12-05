package edu.hw8.task1;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

@SuppressWarnings("RegexpSingleLineJava")
public final class Application {
    public static void main(String[] args) {
        Server server = new QuoteServer();
        new Thread(server::start).start();
        Scanner scanner = new Scanner(System.in);
        try (Client client = new QuoteClient()) {
            while (true) {
                Optional<String> keyword = readInput(scanner);
                if (keyword.isEmpty()) {
                    System.out.println("Shutting down...");
                    break;
                }

                client.send(keyword.get());
                String response = client.waitResponse();

                System.out.println(response);
            }
        } catch (IOException e) {
            System.out.println("Error occurred, shutting down...");
        }
        server.shutdown();
    }

    private static Optional<String> readInput(Scanner scanner) {
        while (true) {
            System.out.println("Enter keyword to search by or enter Ctrl-D to exit: ");
            if (!scanner.hasNextLine()) {
                return Optional.empty();
            }

            String keyword = scanner.nextLine();
            if (!keyword.isEmpty()) {
                return Optional.of(keyword);
            }
        }
    }

    private Application() {
    }
}
