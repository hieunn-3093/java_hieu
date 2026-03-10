package com.java_hieu.booking_tour.dto.tour;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourListResponse {
  private Integer id;
  private String title;
  private CategoryInfo category;
  private BigDecimal price;
  private LocalDate startDate;
  private String duration;
  private Integer maxSlots;
}
