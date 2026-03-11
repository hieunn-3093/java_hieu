package com.java_hieu.booking_tour.service;

import org.springframework.data.domain.Page;

import com.java_hieu.booking_tour.dto.user.UserProfileResponse;
import com.java_hieu.booking_tour.entity.User;

public interface UserService {
  UserProfileResponse getUserProfile();
  Page<User> getUsers(int page, int size, String keyword);
}
