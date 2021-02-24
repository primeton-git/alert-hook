package com.gaoya.mdm.webhook;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	public static void main(String[] args) {
		SpringApplication.run(MdmWebhookApplication.class, args);
	}

	private List<Map<String, Object>> latest = new ArrayList<>(10);

	@GetMapping(value = "/api/mdm/hook", consumes = ALL_VALUE)
	public List<Map<String, Object>> latest() {
		return latest;
	}

	@PostMapping(value = "/api/mdm/hook", consumes = ALL_VALUE, produces = APPLICATION_JSON_VALUE)
	public String hook(@RequestBody String body, @RequestParam("model") String code) {
		System.out.println("MDM: " + code);
		System.out.println(body);
		System.out.println("\n");

		while (latest.size() >= 10) {
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
