package com.java_hieu.booking_tour.repository.projection;

public interface CategoryProjection {
  Integer getId();
  String getName();
  String getDescription();
  Long getTourCount();
}
