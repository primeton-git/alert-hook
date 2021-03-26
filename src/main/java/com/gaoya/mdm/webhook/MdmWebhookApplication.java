package com.gaoya.mdm.webhook;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@SpringBootApplication
public class MdmWebhookApplication {

	@Value("${ws.port:12346}")
	private int port;

	public static void main(String[] args) {
		SpringApplication.run(MdmWebhookApplication.class, args);
	}

	private static final int LIMIT = 20;

	private List<Map<String, Object>> latest = new LinkedList<>();

	@Autowired(required = false)
	private HelloSocketEndpoint socketEndpoint;

	@GetMapping(value = "/api/test/hook", consumes = ALL_VALUE)
	public String testCallWebService() throws Exception {
		MdmDataModelWebhookService service = new MdmDataModelWebhookService(new URL("http", "0.0.0.0", port, "/ws/webhook?wsdl"));
		com.gaoya.mdm.webhook.cxf.MdmDataModelWebhook webhook = service.getPort(com.gaoya.mdm.webhook.cxf.MdmDataModelWebhook.class);
		return webhook.hook("Foo", new Date().toString());
	}

	@GetMapping(value = "/api/mdm/hook", consumes = ALL_VALUE)
	public List<Map<String, Object>> latest() {
		return latest;
	}

	@GetMapping(value = "/api/mdm/socket", consumes = ALL_VALUE)
	public List<Map<String, Object>> socketLatestPush() {
		return socketEndpoint.getLatest();
	}

	@PostMapping(value = "/api/mdm/hook", consumes = ALL_VALUE, produces = APPLICATION_JSON_VALUE)
	public String hook(@RequestBody String body, @RequestParam("model") String code) {
		System.out.println("MDM: " + code);
		System.out.println(body);
		System.out.println("\n");

		while (latest.size() >= LIMIT) {
			latest.remove(latest.size() - 1);
		}
		Map<String, Object> data = new HashMap<>();
		data.put("time", new Date());
		data.put("data", body);data.put("data", body);
		data.put("model", code);
		latest.add(0, data);
		return body;
	}

}
