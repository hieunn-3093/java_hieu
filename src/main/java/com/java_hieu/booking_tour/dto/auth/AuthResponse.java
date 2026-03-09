package com.java_hieu.booking_tour.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

  private String token;
  private String username;
  private String role;
}
