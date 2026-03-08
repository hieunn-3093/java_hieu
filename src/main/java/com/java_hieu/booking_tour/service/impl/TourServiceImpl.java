package com.java_hieu.booking_tour.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.java_hieu.booking_tour.constant.MessageConstants;
import com.java_hieu.booking_tour.entity.BookingStatus;
import com.java_hieu.booking_tour.entity.Tour;
import com.java_hieu.booking_tour.entity.TourStatus;
import com.java_hieu.booking_tour.exception.BusinessException;
import com.java_hieu.booking_tour.exception.DuplicateResourceException;
import com.java_hieu.booking_tour.exception.ResourceNotFoundException;
import com.java_hieu.booking_tour.repository.BookingRepository;
import com.java_hieu.booking_tour.repository.TourRepository;
import com.java_hieu.booking_tour.service.TourService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

  private final TourRepository tourRepository;
  private final BookingRepository bookingRepository;

  @Override
  public List<Tour> findAll() {
    return tourRepository.findAll();
  }

  @Override
  public Tour findById(Integer id) {
    return tourRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", id));
  }

  @Override
  public Tour create(Tour tour) {
    if (tourRepository.existsByTitle(tour.getTitle())) {
      throw new DuplicateResourceException(MessageConstants.Error.TOUR_TITLE_DUPLICATE);
    }
    if (tour.getStartDate() != null && tour.getStartDate().isBefore(LocalDate.now())) {
      throw new BusinessException(HttpStatus.BAD_REQUEST, MessageConstants.Validation.TOUR_START_DATE_FUTURE);
    }
    if (tour.getStatus() == null) {
      tour.setStatus(TourStatus.AVAILABLE);
    }
    return tourRepository.save(tour);
  }

  @Override
  public Tour update(Integer id, Tour tour) {
    Tour existing = tourRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", id));

    if (tourRepository.existsByTitleAndIdNot(tour.getTitle(), id)) {
      throw new DuplicateResourceException(MessageConstants.Error.TOUR_TITLE_DUPLICATE);
    }

    if (tour.getStartDate() != null && tour.getStartDate().isBefore(LocalDate.now())) {
      throw new BusinessException(HttpStatus.BAD_REQUEST, MessageConstants.Validation.TOUR_START_DATE_FUTURE);
    }

    if (existing.getStatus() == TourStatus.FULL && tour.getStatus() == TourStatus.AVAILABLE) {
      boolean isAddingSlots = tour.getMaxSlots() != null
          && existing.getMaxSlots() != null
          && tour.getMaxSlots() > existing.getMaxSlots();
      if (!isAddingSlots) {
        throw new BusinessException(HttpStatus.BAD_REQUEST, MessageConstants.Error.TOUR_STATUS_FULL_CANNOT_AVAILABLE);
      }
    }

    if (tour.getMaxSlots() != null) {
      int bookedQuantity = bookingRepository.sumQuantityByTourIdAndStatusNot(id, BookingStatus.CANCELLED);
      if (tour.getMaxSlots() < bookedQuantity) {
        throw new BusinessException(HttpStatus.BAD_REQUEST, MessageConstants.Error.TOUR_MAX_SLOTS_LESS_THAN_BOOKED);
      }
    }

    tour.setId(id);
    return tourRepository.save(tour);
  }

  @Override
  public void delete(Integer id) {
    if (!tourRepository.existsById(id)) {
      throw new ResourceNotFoundException("Tour", "id", id);
    }
    tourRepository.deleteById(id);
  }
}
