package com.java_hieu.booking_tour.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;


@ControllerAdvice(basePackages = "com.java_hieu.booking_tour.controller.admin")
public class AdminControllerAdvice {

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }

  @ModelAttribute
  public void addCommonAttributes(Model model, Authentication auth) {
    if (auth != null && auth.isAuthenticated()
          && !auth.getPrincipal().equals("anonymousUser")) {
      model.addAttribute("username", auth.getName());
    }
  }
}
