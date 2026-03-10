package com.java_hieu.booking_tour.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.java_hieu.booking_tour.entity.Category;
import com.java_hieu.booking_tour.repository.projection.CategoryProjection;


public interface CategoryRepository extends JpaRepository<Category, Integer> {

  @Query("SELECT c.id AS id, c.name AS name, c.description AS description, COUNT(t.id) AS tourCount " +
          "FROM Category c LEFT JOIN c.tours t " +
          "GROUP BY c.id, c.name, c.description " +
          "ORDER BY c.id ASC")
  List<CategoryProjection> findAllWithTourCount();
}
