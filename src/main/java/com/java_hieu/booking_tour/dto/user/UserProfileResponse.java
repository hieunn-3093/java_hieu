package com.java_hieu.booking_tour.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {
  private Integer id;
  private String username;
  private String fullName;
  private String email;
  private String role;
}
