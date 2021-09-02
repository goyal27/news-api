package com.sapient.news.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@ToString
public class NewsGroup {
    private final NewsArticleInterval interval;
    private final List<NewsArticle> articles;
}
