package com.java_hieu.booking_tour.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import com.java_hieu.booking_tour.constant.WebConstants;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final AdminAuthenticationSuccessHandler adminSuccessHandler;

  @Bean
  @Order(2)
  public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
    http
      .securityMatcher(WebConstants.SECURITY_MATCHER_PATHS)
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(WebConstants.PUBLIC_PATHS).permitAll()
        .requestMatchers(WebConstants.ADMIN_PREFIX).authenticated()
        .anyRequest().authenticated()
      )
      .formLogin(form -> form
        .loginPage(WebConstants.ADMIN_LOGIN)
        .loginProcessingUrl(WebConstants.ADMIN_LOGIN)
        .successHandler(adminSuccessHandler)
        .failureUrl(WebConstants.ADMIN_LOGIN + "?error=true")
        .permitAll()
      )
      .logout(logout -> logout
        .logoutUrl(WebConstants.ADMIN_LOGOUT)
        .logoutSuccessUrl(WebConstants.ADMIN_LOGIN + "?logout=true")
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID")
        .permitAll()
      )
      .exceptionHandling(ex -> ex
        .accessDeniedHandler(new AccessDeniedHandlerImpl())
      );

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
