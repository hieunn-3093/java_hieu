package com.java_hieu.booking_tour.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java_hieu.booking_tour.dto.common.PageResponse;
import com.java_hieu.booking_tour.dto.tour.TourDetailResponse;
import com.java_hieu.booking_tour.dto.tour.TourListRequest;
import com.java_hieu.booking_tour.dto.tour.TourListResponse;
import com.java_hieu.booking_tour.service.TourService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
@Tag(name = "Tour", description = "API quản lý tour du lịch")
public class TourController {

  private final TourService tourService;

  @GetMapping
  @Operation(summary = "Danh sách tour", description = "Lấy danh sách tour có hỗ trợ tìm kiếm và phân trang")
  public ResponseEntity<PageResponse<TourListResponse>> getListTour(@ModelAttribute TourListRequest request) {
    return ResponseEntity.ok(tourService.getListTour(request));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Chi tiết tour", description = "Lấy thông tin chi tiết của một tour")
  public ResponseEntity<TourDetailResponse> getTourDetail(
      @Parameter(description = "ID của tour") @PathVariable Integer id) {
    return ResponseEntity.ok(tourService.getTourDetail(id));
  }
}
