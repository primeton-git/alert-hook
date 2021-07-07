package com.gaoya.mdm.webhook;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.ws.Endpoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(value = "ws.enabled", havingValue = "true")
@Component
public class WebServiceEndpoint {

    @Value("${ws.port:12346}")
    private int port;

    protected Endpoint endpoint;

    @PostConstruct
    public void init() {
        String address = "http://0.0.0.0:" + port + "/ws/webhook";
        endpoint = Endpoint.publish(address, new MdmDataModelWebhook());
        System.out.println("Endpoint: " + address);
    }

    @PreDestroy
    public void clear() {
        endpoint.stop();
    }

}
