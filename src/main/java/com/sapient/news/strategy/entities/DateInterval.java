package com.sapient.news.strategy.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

@Builder
@Getter
public class DateInterval {
  @NonNull private final LocalDateTime startDate;
  @NonNull private final LocalDateTime endDate;

  public boolean overlaps(@NonNull LocalDateTime localDateTime) {
    return (startDate.isEqual(localDateTime) || startDate.isBefore(localDateTime))
        && (endDate.isEqual(localDateTime) || endDate.isAfter(localDateTime));
  }
}
