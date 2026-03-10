package com.java_hieu.booking_tour.dto.tour;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.java_hieu.booking_tour.entity.TourStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourDetailResponse {
  private Integer id;
  private String title;
  private String description;
  private CategoryInfo category;
  private BigDecimal price;
  private String location;
  private LocalDate startDate;
  private String duration;
  private Integer maxSlots;
  private TourStatus status;
}
