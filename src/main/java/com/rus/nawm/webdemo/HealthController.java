package com.rus.nawm.webdemo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/health")
@RestController
public class HealthController {
  @GetMapping("/")
  public ResponseEntity<?> getHealth() {
    return ResponseEntity.ok("I am alive");
  }
}
