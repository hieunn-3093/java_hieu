package com.java_hieu.booking_tour.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java_hieu.booking_tour.constant.WebConstants;
import com.java_hieu.booking_tour.entity.User;
import com.java_hieu.booking_tour.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

  private final UserService userService;

  @GetMapping
  public String index(
    @RequestParam(defaultValue = "" + WebConstants.DEFAULT_PAGE) int page,
    @RequestParam(defaultValue = "" + WebConstants.DEFAULT_PAGE_SIZE) int size,
    @RequestParam(required = false) String keyword,
    Model model) {

    Page<User> userPage = userService.getUsers(page, size, keyword);

    model.addAttribute("userPage", userPage);
    model.addAttribute("users", userPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("pageSize", size);
    model.addAttribute("keyword", keyword);

    return "admin/user/index";
  }
}
