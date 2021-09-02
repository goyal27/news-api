package com.sapient.news.client.model.common;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
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
