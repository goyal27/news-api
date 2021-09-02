package com.sapient.news.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.temporal.ChronoUnit;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class NewsRequest {
  @NonNull private final String keyword;
  private final IntervalUnit intervalUnit;
  private final Integer interval;
  @JsonIgnore
  public ChronoUnit getChronoUnit() {
    switch (intervalUnit) {
      case Day:
        return ChronoUnit.DAYS;
      case Hour:
        return ChronoUnit.HOURS;
      case Minute:
        return ChronoUnit.MINUTES;
      case Month:
        return ChronoUnit.MONTHS;
      default:
        return ChronoUnit.YEARS;
    }
  }
}
