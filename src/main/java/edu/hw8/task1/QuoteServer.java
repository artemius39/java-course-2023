package edu.hw8.task1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuoteServer implements Server {
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 8080;
    private static final int BUFFER_CAPACITY = 1024;
    private static final CompletableFuture<?>[] FUTURES = new CompletableFuture[0];

    private final ExecutorService executorService;
    private final InetSocketAddress address;
    private boolean running;

    public QuoteServer() {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public QuoteServer(String hostname, int port) {
        this.address = new InetSocketAddress(hostname, port);
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public void start() {
        running = true;
        try (Selector selector = Selector.open();
             ServerSocketChannel acceptor = acceptor(selector)) {
            while (running) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();

                List<CompletableFuture<Void>> tasks = new ArrayList<>();
                for (Iterator<SelectionKey> iterator = keys.iterator(); iterator.hasNext(); iterator.remove()) {
                    SelectionKey key = iterator.next();
                    tasks.add(CompletableFuture.runAsync(
                            () -> {
                                try {
                                    if (key.isAcceptable()) {
                                        accept(acceptor, selector);
                                    } else if (key.isReadable()) {
                                        read(key);
                                    }
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }, executorService
                    ));
                }
                CompletableFuture.allOf(tasks.toArray(FUTURES)).join();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }

    @Override
    public void shutdown() {
        running = false;
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        String keyword;
        try {
            keyword = Util.readString(client, ByteBuffer.allocate(BUFFER_CAPACITY));
        } catch (SocketException e) {
            return;
        }
        List<String> quotes = QuoteRepository.find(keyword);
        String response = quotes.isEmpty() ? "No quotes found for this keyword" : String.join("\n\n", quotes);
        client.write(ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_16)));
    }

    private void accept(ServerSocketChannel channel, Selector selector) throws IOException {
        SocketChannel client = channel.accept();
        client.configureBlocking(false)
                .register(selector, SelectionKey.OP_READ);
    }

    private ServerSocketChannel acceptor(Selector selector) throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.bind(address)
                .configureBlocking(false)
                .register(selector, SelectionKey.OP_ACCEPT);
        return channel;
    }
}
