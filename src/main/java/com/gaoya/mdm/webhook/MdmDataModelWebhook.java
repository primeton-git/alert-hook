package com.gaoya.mdm.webhook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class MdmDataModelWebhook {

    private static final int LIMIT = 20;

    private static List<Map<String, Object>> latest = new LinkedList<>();

    @WebMethod
    public String hook(String model, String message) {
        System.out.println("Receive a data model: " + model + " message:\n" + message);

        while (latest.size() >= LIMIT) {
            latest.remove(latest.size() - 1);
        }
        Map<String, Object> event = new HashMap<>();
        event.put("time", System.currentTimeMillis());
        event.put("data", message);
        event.put("model", model);
        latest.add(event);

        return "OK, " + model;
    }

    public static List<Map<String, Object>> getLatest() {
        return latest;
    }

}
