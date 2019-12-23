package org.pureacc.betcentral.infra.security.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@GetMapping("/api/test")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok("Hello World!");
	}
}
