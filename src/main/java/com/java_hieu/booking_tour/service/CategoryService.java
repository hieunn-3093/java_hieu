package com.java_hieu.booking_tour.service;

import java.util.List;

import com.java_hieu.booking_tour.entity.Category;
import com.java_hieu.booking_tour.repository.projection.CategoryProjection;

public interface CategoryService {
  List<CategoryProjection> findAllWithTourCount();
  Category findById(Integer id);
  Category create(Category category);
  Category update(Integer id, Category category);
  void delete(Integer id);
}
