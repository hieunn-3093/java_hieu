package com.java_hieu.booking_tour.controller.admin;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java_hieu.booking_tour.constant.MessageConstants;

@Controller
@RequestMapping("/admin")
public class AdminAuthController {

  @GetMapping("/login")
  public String loginPage(
      @RequestParam(value = "error", required = false) String error,
      @RequestParam(value = "logout", required = false) String logout,
      @RequestParam(value = "forbidden", required = false) String forbidden,
      Model model) {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.isAuthenticated()
        && !auth.getPrincipal().equals("anonymousUser")) {
      return "redirect:/admin/dashboard";
    }

    if (error != null) {
      model.addAttribute("errorMessage", MessageConstants.Auth.LOGIN_ERROR);
    }

    if (forbidden != null) {
      model.addAttribute("errorMessage", MessageConstants.Auth.LOGIN_FORBIDDEN);
    }

    if (logout != null) {
      model.addAttribute("successMessage", MessageConstants.Auth.LOGOUT_SUCCESS);
    }

    return "admin/login";
  }

  @GetMapping("/dashboard")
  public String dashboardPage(Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    model.addAttribute("username", auth.getName());
    return "admin/dashboard";
  }
}
