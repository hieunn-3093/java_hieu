package com.java_hieu.booking_tour.dto.admin.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryListResponse {
  private final Integer id;
  private final String name;
  private final String description;
  private final Long tourCount;
}
