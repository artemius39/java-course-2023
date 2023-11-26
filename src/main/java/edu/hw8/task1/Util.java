package edu.hw8.task1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

final class Util {
    private static final Logger LOGGER = LogManager.getLogger();

    public static String readString(SocketChannel channel, ByteBuffer buffer) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int bytesRead;
        while ((bytesRead = channel.read(buffer)) > 0) {
            buffer.flip();
            for (int i = 0; i < bytesRead; i++) {
                stream.write(buffer.get());
            }
            buffer.clear();
        }

        String string = stream.toString(StandardCharsets.UTF_16);
        LOGGER.info("Read '{}'", string);
        return string;
    }

    private Util() {
    }
}
