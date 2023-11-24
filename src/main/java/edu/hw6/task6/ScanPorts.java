package edu.hw6.task6;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Map;

public final class ScanPorts {
    private static final int FIRST_PORT = 0;
    private static final int LAST_PORT = 49151;

    private static final Map<Integer, String> POSSIBLE_TCP_USERS = Map.of(
            80, "HTTP",
            21, "FTP",
            25, "SMP",
            22, "SSH",
            443, "HTTPS",
            53, "DNS",
            3306, "MySQL Database",
            5432, "PostgreSQL Database",
            3389, "Remote Desktop Protocol (RDP)",
            8080, "HTTP Proxy"
    );

    private static final Map<Integer, String> POSSIBLE_UDP_USERS = Map.of(
            53, "Domain Name System (DNS)",
            67, "Bootstrap Protocol Server (BOOTP) / DHCP Server",
            68, "Bootstrap Protocol Client (BOOTP) / DHCP Client",
            69, "Trivial File Transfer Protocol (TFTP)",
            123, "Network Time Protocol (NTP)",
            161, "Simple Network Management Protocol (SNMP)",
            162, "Simple Network Management Protocol Trap (SNMP Trap)",
            1900, "Simple Service Discovery Protocol (SSDP)",
            5353, "Multicast DNS (mDNS)",
            6771, "BitTorrent Local Peer Discovery"
    );
    private static final String ENTRY_FORMAT = "%s      %5d  %s\n";

    public static String scanPorts() {
        return scanPorts(LAST_PORT);
    }

    public static String scanPorts(int upTo) {
        StringBuilder sb = new StringBuilder("Currently used ports:\nProtocol  Port  Service\n");

        for (int port = FIRST_PORT; port <= upTo; port++) {
            if (scanTCPPort(port)) {
                String possibleUser = POSSIBLE_TCP_USERS.getOrDefault(port, "");
                sb.append(ENTRY_FORMAT.formatted("TCP", port, possibleUser));
            }
            if (scanUDPPort(port)) {
                String possibleUser = POSSIBLE_UDP_USERS.getOrDefault(port, "");
                sb.append(ENTRY_FORMAT.formatted("UDP", port, possibleUser));
            }
        }

        return sb.toString();
    }

    private static boolean scanTCPPort(int port) {
        try (ServerSocket ignored = new ServerSocket(port)) {
            return false;
        } catch (BindException e) {
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean scanUDPPort(int port) {
        try (DatagramSocket ignored = new DatagramSocket(port)) {
            return false;
        } catch (BindException e) {
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ScanPorts() {
    }
}
