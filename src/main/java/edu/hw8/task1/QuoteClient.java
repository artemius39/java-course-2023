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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QuoteClient implements Client {
    private static final Logger LOGGER = LogManager.getLogger();
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
        LOGGER.info("Client {} is waiting for response", this);
        selector.select();
        LOGGER.info("Client {} got response", this);
        buffer.clear();
        return Util.readString(channel, buffer);
    }

    @Override
    public void send(String keyword) throws IOException {
        buffer.clear()
                .put(keyword.getBytes(StandardCharsets.UTF_16))
                .flip();
        channel.write(buffer);
        LOGGER.info("Client {} sent request {}", this, keyword);
    }

    @Override
    public void close() throws IOException {
        LOGGER.info("Closed {}", this);
        selector.close();
        channel.close();
    }
}
