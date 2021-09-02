package com.sapient.news.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@ToString
@Getter
public class NewsArticle {
    private final String author;
    private final String title;
    private final String description;
    private final String source;
    private final String url;
    private final String imageUrl;
    private final String content;
    private final LocalDateTime publishedAt;
}
