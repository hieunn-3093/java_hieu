package com.java_hieu.booking_tour.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.java_hieu.booking_tour.constant.MessageConstants;
import com.java_hieu.booking_tour.dto.common.PageResponse;
import com.java_hieu.booking_tour.dto.tour.TourDetailResponse;
import com.java_hieu.booking_tour.dto.tour.TourListRequest;
import com.java_hieu.booking_tour.dto.tour.TourListResponse;
import com.java_hieu.booking_tour.entity.BookingStatus;
import com.java_hieu.booking_tour.entity.Tour;
import com.java_hieu.booking_tour.entity.TourStatus;
import com.java_hieu.booking_tour.exception.BusinessException;
import com.java_hieu.booking_tour.exception.DuplicateResourceException;
import com.java_hieu.booking_tour.exception.ResourceNotFoundException;
import com.java_hieu.booking_tour.mapper.TourMapper;
import com.java_hieu.booking_tour.repository.BookingRepository;
import com.java_hieu.booking_tour.repository.TourRepository;
import com.java_hieu.booking_tour.repository.specification.TourSpecification;
import com.java_hieu.booking_tour.service.TourService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

  private final TourRepository tourRepository;
  private final BookingRepository bookingRepository;
  private final TourMapper tourMapper;

  @Override
  public Page<Tour> getPage(int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
    return tourRepository.findAll(pageable);
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

    TourStatus currentStatus = existing.getStatus();
    TourStatus newStatus = tour.getStatus();
    if (currentStatus != null && newStatus != null
        && currentStatus != newStatus
        && !currentStatus.canTransitionTo(newStatus)) {
      throw new BusinessException(HttpStatus.BAD_REQUEST,
          String.format(MessageConstants.Error.TOUR_STATUS_TRANSITION_INVALID, currentStatus, newStatus));
    }

    if (currentStatus == TourStatus.FULL && newStatus == TourStatus.AVAILABLE) {
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

    existing.setCategory(tour.getCategory());
    existing.setTitle(tour.getTitle());
    existing.setDescription(tour.getDescription());
    existing.setPrice(tour.getPrice());
    existing.setLocation(tour.getLocation());
    existing.setStartDate(tour.getStartDate());
    existing.setDuration(tour.getDuration());
    existing.setMaxSlots(tour.getMaxSlots());
    existing.setStatus(tour.getStatus());
    return tourRepository.save(existing);
  }

  @Override
  public void delete(Integer id) {
    if (!tourRepository.existsById(id)) {
      throw new ResourceNotFoundException("Tour", "id", id);
    }
    if (bookingRepository.existsByTourIdAndStatusNot(id, BookingStatus.CANCELLED)) {
      throw new BusinessException(HttpStatus.BAD_REQUEST, MessageConstants.Error.TOUR_HAS_ACTIVE_BOOKINGS);
    }
    tourRepository.deleteById(id);
  }

  @Override
  public PageResponse<TourListResponse> getListTour(TourListRequest request) {
    Specification<Tour> spec = Specification
      .where(TourSpecification.withCategory())
      .and(TourSpecification.hasKeyword(request.getKeyword()))
      .and(TourSpecification.hasCategoryId(request.getCategoryId()))
      .and(TourSpecification.hasMinPrice(request.getMinPrice()))
      .and(TourSpecification.hasMaxPrice(request.getMaxPrice()));

    Page<Tour> page = tourRepository.findAll(spec, PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Direction.DESC, "id")));

    List<TourListResponse> items = page.getContent().stream()
      .map(tourMapper::toListResponse)
      .toList();

    return PageResponse.<TourListResponse>builder()
      .items(items)
      .page(page.getNumber())
      .size(page.getSize())
      .totalElements(page.getTotalElements())
      .totalPages(page.getTotalPages())
      .hasNext(page.hasNext())
      .hasPrevious(page.hasPrevious())
      .build();
  }

  @Override
  public TourDetailResponse getTourDetail(Integer id) {
    Tour tour = tourRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", id));
    return tourMapper.toDetailResponse(tour);
  }
}
