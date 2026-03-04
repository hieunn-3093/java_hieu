package com.java_hieu.booking_tour.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java_hieu.booking_tour.constant.ApiConstants;
import com.java_hieu.booking_tour.dto.auth.AuthResponse;
import com.java_hieu.booking_tour.dto.auth.LoginRequest;
import com.java_hieu.booking_tour.dto.auth.RegisterRequest;
import com.java_hieu.booking_tour.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiConstants.AUTH_PREFIX)
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
    return ResponseEntity.created(null).body(authService.register(request));
  }
}
