package com.java_hieu.booking_tour.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java_hieu.booking_tour.dto.user.UserProfileResponse;
import com.java_hieu.booking_tour.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "API quản lý người dùng")
public class UserController {

  private final UserService userService;

  @GetMapping("/profile")
  @Operation(summary = "Thông tin người dùng", description = "Lấy thông tin chi tiết của người dùng đang đăng nhập")
  public ResponseEntity<UserProfileResponse> getUserProfile() {
    return ResponseEntity.ok(userService.getUserProfile());
  }
}
