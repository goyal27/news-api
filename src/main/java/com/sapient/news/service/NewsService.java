package com.sapient.news.service;

import com.sapient.news.client.NewsClient;
import com.sapient.news.client.model.common.NewsArticle;
import com.sapient.news.request.NewsRequest;
import com.sapient.news.response.NewsGroup;
import com.sapient.news.strategy.NewsResponseGroupingStrategy;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class NewsService {
  @Autowired
  private NewsClient newsApiClient;
  @Autowired
  private NewsResponseGroupingStrategy intervalBasedGrouping;

  public List<NewsGroup> getNews(@NonNull final NewsRequest newsRequest) {
    List<NewsArticle> newsClientResponse = newsApiClient.getNews(newsRequest);
    if (newsClientResponse.isEmpty())return Collections.emptyList();
    return intervalBasedGrouping.groupNews(newsRequest, newsClientResponse);
  }
}
