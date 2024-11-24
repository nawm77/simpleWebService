package com.rus.nawm.webdemo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/greeting")
public class GreetingController {
  @GetMapping("/hello")
  public ResponseEntity<String> getGreeting() {
    return ResponseEntity.ok("Hello, World!");
  }

  @GetMapping("/welcome")
  public ResponseEntity<String> getWelcome() {
    return ResponseEntity.ok("Welcome to the server!");
  }
}
