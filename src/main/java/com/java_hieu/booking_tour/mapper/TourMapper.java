package com.java_hieu.booking_tour.mapper;

import org.mapstruct.Mapper;

import com.java_hieu.booking_tour.dto.tour.TourDetailResponse;
import com.java_hieu.booking_tour.dto.tour.TourListResponse;
import com.java_hieu.booking_tour.entity.Tour;

@Mapper(componentModel = "spring")
public interface TourMapper {
  TourListResponse toListResponse(Tour tour);
  TourDetailResponse toDetailResponse(Tour tour);
}
