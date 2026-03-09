package com.java_hieu.booking_tour.dto.auth;

import com.java_hieu.booking_tour.constant.MessageConstants;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

  @NotBlank(message = MessageConstants.Validation.USERNAME_BLANK)
  private String username;

  @NotBlank(message = MessageConstants.Validation.PASSWORD_BLANK)
  private String password;
}
