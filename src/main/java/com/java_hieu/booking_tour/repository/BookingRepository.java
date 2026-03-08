package com.java_hieu.booking_tour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.java_hieu.booking_tour.entity.Booking;
import com.java_hieu.booking_tour.entity.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
  @Query("SELECT COALESCE(SUM(b.quantity), 0) FROM Booking b WHERE b.tour.id = :tourId AND b.status != :status")
  int sumQuantityByTourIdAndStatusNot(@Param("tourId") Integer tourId, @Param("status") BookingStatus status);
}
