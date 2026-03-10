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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiConstants.AUTH_PREFIX)
@RequiredArgsConstructor
@Tag(name = "Auth", description = "API xác thực người dùng")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  @Operation(summary = "Đăng nhập", description = "Trả về JWT token khi đăng nhập thành công")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }

  @PostMapping("/register")
  @Operation(summary = "Đăng ký", description = "Tạo tài khoản mới và trả về JWT token")
  public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
    return ResponseEntity.ok(authService.register(request));
  }
}
