package com.java_hieu.booking_tour.constant;

public final class WebConstants {

  private WebConstants() {}

  public static final String ADMIN_LOGIN     = "/admin/login";
  public static final String ADMIN_LOGOUT    = "/admin/logout";
  public static final String ADMIN_DASHBOARD = "/admin/dashboard";
  public static final String ADMIN_PREFIX    = "/admin/**";

  public static final String[] SECURITY_MATCHER_PATHS = {
    ADMIN_PREFIX,
    "/css/**",
    "/js/**",
    "/images/**",
    "/webjars/**",
  };

  public static final String[] PUBLIC_PATHS = {
    ADMIN_LOGIN,
    "/css/**",
    "/js/**",
    "/images/**",
    "/webjars/**",
  };
}
