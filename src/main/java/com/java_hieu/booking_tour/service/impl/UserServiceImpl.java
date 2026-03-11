package com.java_hieu.booking_tour.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.java_hieu.booking_tour.dto.user.UserProfileResponse;
import com.java_hieu.booking_tour.entity.User;
import com.java_hieu.booking_tour.exception.ResourceNotFoundException;
import com.java_hieu.booking_tour.repository.UserRepository;
import com.java_hieu.booking_tour.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public UserProfileResponse getUserProfile() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    User user = userRepository.findByUsername(username)
      .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

    return UserProfileResponse.builder()
      .id(user.getId())
      .username(user.getUsername())
      .fullName(user.getFullName())
      .email(user.getEmail())
      .role(user.getRole().getName().name())
      .build();
  }

  @Override
  public Page<User> getUsers(int page, int size, String keyword) {
    PageRequest pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

    if (keyword != null && !keyword.isBlank()) {
      return userRepository.findByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCase(
        keyword, keyword, pageable
      );
    }

    return userRepository.findAll(pageable);
  }
}
