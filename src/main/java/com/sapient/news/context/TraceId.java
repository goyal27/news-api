package com.sapient.news.context;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public final class TraceId {
  private final String value;

  public TraceId() {
    value = UUID.randomUUID().toString();
  }

  public TraceId(String value) {
    this.value = value;
  }
}
