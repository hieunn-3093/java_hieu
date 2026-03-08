package com.java_hieu.booking_tour.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java_hieu.booking_tour.dto.admin.category.CategoryListResponse;
import com.java_hieu.booking_tour.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

  @Query("SELECT new com.java_hieu.booking_tour.dto.admin.category.CategoryListResponse(c.id, c.name, c.description, COUNT(t.id)) " +
          "FROM Category c LEFT JOIN c.tours t " +
          "GROUP BY c.id, c.name, c.description " +
          "ORDER BY c.id ASC")
  List<CategoryListResponse> findAllWithTourCount();
}
