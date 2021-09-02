package com.sapient.news.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@ToString
public class NewsArticleInterval {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
}
