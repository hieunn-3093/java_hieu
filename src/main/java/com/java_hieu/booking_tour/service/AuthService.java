package com.java_hieu.booking_tour.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.java_hieu.booking_tour.dto.auth.AuthResponse;
import com.java_hieu.booking_tour.dto.auth.LoginRequest;
import com.java_hieu.booking_tour.dto.auth.RegisterRequest;
import com.java_hieu.booking_tour.entity.Role;
import com.java_hieu.booking_tour.entity.RoleName;
import com.java_hieu.booking_tour.entity.User;
import com.java_hieu.booking_tour.exception.DuplicateResourceException;
import com.java_hieu.booking_tour.exception.UnauthorizedException;
import com.java_hieu.booking_tour.repository.RoleRepository;
import com.java_hieu.booking_tour.repository.UserRepository;
import com.java_hieu.booking_tour.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public AuthResponse login(LoginRequest request) {
    User user = userRepository.findByUsername(request.getUsername())
      .orElseThrow(() -> new UnauthorizedException("Tên đăng nhập hoặc mật khẩu không đúng"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new UnauthorizedException("Tên đăng nhập hoặc mật khẩu không đúng");
    }

    String role = user.getRole().getName().name();
    String token = jwtUtil.generateToken(user.getUsername(), role);

    return AuthResponse.builder()
      .token(token)
      .username(user.getUsername())
      .role(role)
      .build();
  }

  public AuthResponse register(RegisterRequest request) {
    if (userRepository.findByUsername(request.getUsername()).isPresent()) {
      throw new DuplicateResourceException("Tài khoản", "tên đăng nhập", request.getUsername());
    }

    Role role = roleRepository.findByName(RoleName.ROLE_USER)
      .orElseThrow(() -> new IllegalStateException("Role USER không tồn tại"));

    User user = User.builder()
      .username(request.getUsername())
      .password(passwordEncoder.encode(request.getPassword()))
      .fullName(request.getFullName())
      .email(request.getEmail())
      .role(role)
      .build();

    userRepository.save(user);

    String token = jwtUtil.generateToken(user.getUsername(), role.getName().name());

    return AuthResponse.builder()
      .token(token)
      .username(user.getUsername())
      .role(role.getName().name())
      .build();
  }
}
