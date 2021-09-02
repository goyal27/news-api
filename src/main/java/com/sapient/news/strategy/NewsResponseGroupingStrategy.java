package com.sapient.news.strategy;

import com.sapient.news.client.model.common.NewsArticle;
import com.sapient.news.request.NewsRequest;
import com.sapient.news.response.NewsGroup;
import lombok.NonNull;

import java.util.List;

public interface NewsResponseGroupingStrategy {
  List<NewsGroup> groupNews(
      @NonNull NewsRequest newsRequest, @NonNull List<NewsArticle> newsClientResponse);
}
