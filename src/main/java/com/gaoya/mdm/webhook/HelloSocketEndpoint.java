package com.gaoya.mdm.webhook;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(value = "socket.enabled", havingValue = "true")
@Component
public class HelloSocketEndpoint implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${socket.host:localhost}")
    private String host;

    @Value("${socket.port:12347}")
    private int port;

    private DatagramSocket socket;

    private static final int LIMIT = 20;

    private List<Map<String, Object>> latest = new LinkedList<>();

    public void boot() {
        try {
            socket = new DatagramSocket(port, InetAddress.getByName(host));
            System.out.println("[INFO] UDP Server started.");
        } catch (SocketException e) {
            e.printStackTrace();
            return;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }
        // Runtime.getRuntime().addShutdownHook(new Thread(() -> socket.close()));

        while (true) {
            try {
                // test small message
                DatagramPacket request = new DatagramPacket(new byte[1024000], 1024000);

                socket.receive(request);

                int len = request.getLength();
                if (len < 1) {
                    continue;
                }

                byte[] binary = request.getData();

                String content = new String(binary, 0, len, "UTF-8");
                while (latest.size() >= LIMIT) {
                    latest.remove(latest.size() - 1);
                }
                Map<String, Object> data = new HashMap<>();
                data.put("time", new Date());
                data.put("data", content);
                latest.add(0, data);

                System.out.printf("###\n###\nReceive a UDP message, content:\n %s\n\n###\n###\n", content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @PreDestroy
    public void destroy() {
        if (null == socket || socket.isClosed()) {
            return;
        }
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> getLatest() {
        return latest;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        Thread thread = new Thread(this::boot);
        thread.setName("socket-daemon");
        thread.setDaemon(true);
        thread.start();
    }

}
