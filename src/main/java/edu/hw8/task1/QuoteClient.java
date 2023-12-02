package edu.hw8.task1;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class QuoteClient implements Client {
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 8080;
    private static final int BUFFER_CAPACITY = 256;
    private final Selector selector;
    private final SocketChannel channel;
    private final ByteBuffer buffer;

    public QuoteClient() throws IOException {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public QuoteClient(String hostname, int port) throws IOException {
        selector = Selector.open();
        channel = waitForServer(new InetSocketAddress(hostname, port));
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        buffer = ByteBuffer.allocate(BUFFER_CAPACITY);
    }

    private SocketChannel waitForServer(SocketAddress remote) throws IOException {
        while (true) {
            try {
                return SocketChannel.open(remote);
            } catch (ConnectException ignored) {
                // Server hasn't started yet, must wait
            }
        }
    }

    @Override
    public String waitResponse() throws IOException {
        selector.select();
        buffer.clear();
        String response = Util.readString(channel, buffer);
        return response.isEmpty() ? "No response" : response;
    }

    @Override
    public void send(String keyword) throws IOException {
        buffer.clear()
                .put(keyword.getBytes(StandardCharsets.UTF_16))
                .flip();
        channel.write(buffer);
    }

    @Override
    public void close() throws IOException {
        selector.close();
        channel.close();
    }
}
