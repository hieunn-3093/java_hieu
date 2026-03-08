package com.java_hieu.booking_tour.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.java_hieu.booking_tour.constant.MessageConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tours")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonBackReference
    @NotNull(message = MessageConstants.Validation.TOUR_CATEGORY_REQUIRED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Category category;

    @NotBlank(message = MessageConstants.Validation.TOUR_TITLE_REQUIRED)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = MessageConstants.Validation.TOUR_PRICE_REQUIRED)
    @DecimalMin(value = "0", inclusive = true, message = MessageConstants.Validation.TOUR_PRICE_MIN)
    private BigDecimal price;

    private String location;

    @NotNull(message = MessageConstants.Validation.TOUR_START_DATE_REQUIRED)
    @FutureOrPresent(message = MessageConstants.Validation.TOUR_START_DATE_FUTURE)
    private LocalDate startDate;

    private String duration;

    @NotNull(message = MessageConstants.Validation.TOUR_MAX_SLOTS_REQUIRED)
    @Min(value = 1, message = MessageConstants.Validation.TOUR_MAX_SLOTS_MIN)
    private Integer maxSlots;

    @Enumerated(EnumType.STRING)
    private TourStatus status;
}
