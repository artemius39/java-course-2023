package edu.hw8.task1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

final class Util {
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

        return stream.toString(StandardCharsets.UTF_16);
    }

    private Util() {
    }
}
