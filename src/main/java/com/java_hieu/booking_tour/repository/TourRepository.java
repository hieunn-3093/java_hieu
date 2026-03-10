package com.java_hieu.booking_tour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.java_hieu.booking_tour.entity.Tour;

@Repository
public interface TourRepository extends JpaRepository<Tour, Integer>, JpaSpecificationExecutor<Tour> {
  boolean existsByTitle(String title);
  boolean existsByTitleAndIdNot(String title, Integer id);
}
