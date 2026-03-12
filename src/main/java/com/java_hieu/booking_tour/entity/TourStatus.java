package com.java_hieu.booking_tour.entity;

public enum TourStatus {
  AVAILABLE, FULL, CANCELLED;

  public boolean canTransitionTo(TourStatus nextState) {
    return switch (this) {
      case AVAILABLE -> nextState == FULL || nextState == CANCELLED;
      case FULL      -> nextState == AVAILABLE || nextState == CANCELLED;
      case CANCELLED -> false;
    };
  }
}
