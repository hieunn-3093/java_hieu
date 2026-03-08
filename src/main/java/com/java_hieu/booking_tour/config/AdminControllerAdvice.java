package com.java_hieu.booking_tour.config;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


@ControllerAdvice(basePackages = "com.java_hieu.booking_tour.controller.admin")
public class AdminControllerAdvice {

  @ModelAttribute
  public void addCommonAttributes(Model model, Authentication auth) {
    if (auth != null && auth.isAuthenticated()
          && !auth.getPrincipal().equals("anonymousUser")) {
      model.addAttribute("username", auth.getName());
    }
  }
}
