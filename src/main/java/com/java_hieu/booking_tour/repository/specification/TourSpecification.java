package com.java_hieu.booking_tour.repository.specification;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.java_hieu.booking_tour.entity.Tour;

import jakarta.persistence.criteria.JoinType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TourSpecification {
  public static Specification<Tour> hasKeyword(String keyword) {
    return (root, query, cb) -> {
      if (keyword == null || keyword.isBlank()) return null;
      String pattern = "%" + keyword.toLowerCase() + "%";
      return cb.or(
          cb.like(cb.lower(root.get("title")), pattern),
          cb.like(cb.lower(root.get("description")), pattern),
          cb.like(cb.lower(root.get("location")), pattern)
      );
    };
  }

  public static Specification<Tour> hasCategoryId(Integer categoryId) {
    return (root, query, cb) -> {
      if (categoryId == null) return null;
      return cb.equal(root.get("category").get("id"), categoryId);
    };
  }

  public static Specification<Tour> hasMinPrice(BigDecimal minPrice) {
    return (root, query, cb) -> {
      if (minPrice == null) return null;
      return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    };
  }

  public static Specification<Tour> hasMaxPrice(BigDecimal maxPrice) {
    return (root, query, cb) -> {
      if (maxPrice == null) return null;
      return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    };
  }

  public static Specification<Tour> withCategory() {
    return (root, query, cb) -> {
      if (query != null && Long.class != query.getResultType()) {
        root.fetch("category", JoinType.LEFT);
      }
      return null;
    };
  }
}
