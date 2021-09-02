package com.sapient.news.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ErrorMessage {
    @NonNull private final String code;
    @NonNull private final LocalDateTime timestamp;
    @NonNull private final String message;
    @NonNull private final String reference;
    private final List<String> errors;
}
