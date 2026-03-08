package com.java_hieu.booking_tour.service;

import java.util.List;

import com.java_hieu.booking_tour.entity.Tour;

public interface TourService {
  List<Tour> findAll();
  Tour findById(Integer id);
  Tour create(Tour tour);
  Tour update(Integer id, Tour tour);
  void delete(Integer id);
}
