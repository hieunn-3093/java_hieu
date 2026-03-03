package com.java_hieu.booking_tour.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthyController {

  @GetMapping("/health")
  public String healthCheck() {
    return "OK";
  }
}
