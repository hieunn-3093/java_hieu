package com.java_hieu.booking_tour.constant;

public final class ApiConstants {
  private ApiConstants() {}

  public static final String API_PATTERN = "/api/**";
  public static final String HEALTH_PATH = "/health";

  // public api
  public static final String AUTH_PREFIX = "/api/auth";
  public static final String AUTH_LOGIN = "/api/auth/login";
  public static final String AUTH_REGISTER = "/api/auth/register";

  // private api
  public static final String AUTH_LOGOUT = "/api/auth/logout";

  public static final String[] SECURITY_MATCHER_PATHS = {
    API_PATTERN,
    HEALTH_PATH,
  };

  public static final String[] PUBLIC_PATHS = {
    AUTH_LOGIN,
    AUTH_REGISTER,
    HEALTH_PATH,
  };

  public static final String[] AUTHENTICATED_PATHS = {
    AUTH_LOGOUT,
  };
}
