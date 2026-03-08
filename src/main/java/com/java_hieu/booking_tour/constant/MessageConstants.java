package com.java_hieu.booking_tour.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageConstants {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Auth {
    public static final String LOGIN_ERROR = "Tên đăng nhập hoặc mật khẩu không đúng. Vui lòng thử lại.";
    public static final String LOGIN_FORBIDDEN = "Tài khoản này không có quyền truy cập trang quản trị.";
    public static final String LOGOUT_SUCCESS = "Bạn đã đăng xuất thành công.";
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Validation {
    // Auth validation
    public static final String USERNAME_BLANK = "Tên đăng nhập không được để trống";
    public static final String USERNAME_SIZE = "Tên đăng nhập phải từ 3-50 ký tự";
    public static final String PASSWORD_BLANK = "Mật khẩu không được để trống";
    public static final String PASSWORD_SIZE = "Mật khẩu phải ít nhất 6 ký tự";
    public static final String FULLNAME_BLANK = "Họ tên không được để trống";
    public static final String EMAIL_INVALID = "Email không hợp lệ";

    // Tour validation
    public static final String TOUR_TITLE_REQUIRED = "Tên tour không được để trống";
    public static final String TOUR_CATEGORY_REQUIRED = "Danh mục không được để trống";
    public static final String TOUR_PRICE_REQUIRED = "Giá không được để trống";
    public static final String TOUR_PRICE_MIN = "Giá phải lớn hơn hoặc bằng 0";
    public static final String TOUR_START_DATE_REQUIRED = "Ngày bắt đầu không được để trống";
    public static final String TOUR_START_DATE_FUTURE = "Ngày bắt đầu không được nhỏ hơn ngày hiện tại";
    public static final String TOUR_MAX_SLOTS_REQUIRED = "Số lượng slot không được để trống";
    public static final String TOUR_MAX_SLOTS_MIN = "Số lượng slot phải lớn hơn 0";
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Action {
    public static final String ADD_SUCCESS = "Thêm mới dữ liệu thành công!";
    public static final String UPDATE_SUCCESS = "Cập nhật dữ liệu thành công!";
    public static final String DELETE_SUCCESS = "Xóa dữ liệu thành công!";
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Error {
    public static final String NOT_FOUND = "Dữ liệu không tồn tại!";
    public static final String TOUR_TITLE_DUPLICATE = "Tên tour đã tồn tại";
    public static final String TOUR_STATUS_FULL_CANNOT_AVAILABLE = "Tour đang FULL không thể chuyển về AVAILABLE";
    public static final String TOUR_MAX_SLOTS_LESS_THAN_BOOKED = "Số lượng slot không được nhỏ hơn số lượng đã đặt";
  }
}
