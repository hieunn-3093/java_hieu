package com.java_hieu.booking_tour.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java_hieu.booking_tour.entity.Category;
import com.java_hieu.booking_tour.exception.ResourceNotFoundException;
import com.java_hieu.booking_tour.repository.CategoryRepository;
import com.java_hieu.booking_tour.repository.projection.CategoryProjection;
import com.java_hieu.booking_tour.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  @Override
  public List<CategoryProjection> findAllWithTourCount() {
    return categoryRepository.findAllWithTourCount();
  }

  @Override
  public Category findById(Integer id) {
    return categoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
  }

  @Override
  public Category create(Category category) {
    return categoryRepository.save(category);
  }

  @Override
  public Category update(Integer id, Category category) {
    if (!categoryRepository.existsById(id)) {
      throw new ResourceNotFoundException("Category", "id", id);
    }
    category.setId(id);
    return categoryRepository.save(category);
  }

  @Override
  public void delete(Integer id) {
    if (!categoryRepository.existsById(id)) {
      throw new ResourceNotFoundException("Category", "id", id);
    }
    categoryRepository.deleteById(id);
  }
}
