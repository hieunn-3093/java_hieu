package com.java_hieu.booking_tour.dto.tour;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TourListRequest {
  private String keyword;
  private Integer categoryId;
  private BigDecimal minPrice;
  private BigDecimal maxPrice;
  private int page = 0;
  private int size = 10;
}
