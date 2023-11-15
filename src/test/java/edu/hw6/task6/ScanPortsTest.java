package edu.hw6.task6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.ServerSocket;
import static edu.hw6.task6.ScanPorts.scanPorts;
import static org.assertj.core.api.Assertions.assertThat;

class ScanPortsTest {
    @Test
    @DisplayName("Used port")
    void usedPortIsDisplayed() throws IOException {
        ServerSocket socket = new ServerSocket(8080);

        assertThat(scanPorts(10000).lines()).anyMatch(line -> line.matches("TCP\\s+8080\\s+HTTP Proxy"));
        
        socket.close();
    }

    @Test
    @DisplayName("Unused port is not displayed")
    void unusedPortIsNotDisplayed() {
        assertThat(scanPorts(10).lines()).noneMatch(line -> line.matches("(TCP|UDP)\\s+0.*"));
    }
}
