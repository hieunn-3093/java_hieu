package com.java_hieu.booking_tour.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.java_hieu.booking_tour.constant.WebConstants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AdminAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
    HttpServletResponse response,
    Authentication authentication) throws IOException {

    boolean isAdmin = authentication.getAuthorities()
      .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

    if (isAdmin) {
      response.sendRedirect(WebConstants.ADMIN_DASHBOARD);
    } else {
      HttpSession session = request.getSession(false);
      if (session != null) {
        session.invalidate();
      }
      response.sendRedirect(WebConstants.ADMIN_LOGIN + "?forbidden=true");
    }
  }
}
