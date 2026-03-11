package com.java_hieu.booking_tour.service;

import org.springframework.data.domain.Page;

import com.java_hieu.booking_tour.entity.Tour;

public interface TourService {
  Page<Tour> getPage(int page, int size);
  Tour findById(Integer id);
  Tour create(Tour tour);
  Tour update(Integer id, Tour tour);
  void delete(Integer id);
}
