package com.java_hieu.booking_tour.dto.auth;

import com.java_hieu.booking_tour.constant.MessageConstants;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

  @NotBlank(message = MessageConstants.Validation.USERNAME_BLANK)
  @Size(min = 3, max = 50, message = MessageConstants.Validation.USERNAME_SIZE)
  private String username;

  @NotBlank(message = MessageConstants.Validation.PASSWORD_BLANK)
  @Size(min = 6, message = MessageConstants.Validation.PASSWORD_SIZE)
  private String password;

  @NotBlank(message = MessageConstants.Validation.FULLNAME_BLANK)
  private String fullName;

  @Email(message = MessageConstants.Validation.EMAIL_INVALID)
  private String email;
}
