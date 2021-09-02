package com.sapient.news.strategy;

import com.sapient.news.client.model.common.NewsArticle;
import com.sapient.news.request.NewsRequest;
import com.sapient.news.response.NewsArticleInterval;
import com.sapient.news.response.NewsGroup;
import com.sapient.news.strategy.entities.DateInterval;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IntervalBasedGrouping implements NewsResponseGroupingStrategy {
  @Override
  public List<NewsGroup> groupNews(
      @NonNull final NewsRequest newsRequest, @NonNull final List<NewsArticle> newsClientResponse) {
    newsClientResponse.sort(
        Comparator.comparing(NewsArticle::getPublishedAt, Comparator.reverseOrder()));
    List<DateInterval> intervals = getIntervals(newsRequest, newsClientResponse);
    int currNewsIndex = 0;
    List<NewsGroup> newsGroups = new ArrayList<>();
    for (DateInterval interval : intervals) {
      List<NewsArticle> overlappingNewsArticles =
          getOverlappingNewsArticles(interval, newsClientResponse, currNewsIndex);
      currNewsIndex += overlappingNewsArticles.size();
      if (!overlappingNewsArticles.isEmpty()) {
        newsGroups.add(
            NewsGroup.builder()
                .interval(
                    NewsArticleInterval.builder()
                        .startTime(interval.getStartDate())
                        .endTime(interval.getEndDate())
                        .build())
                .articles(groupArticles(overlappingNewsArticles))
                .build());
      }
    }

    return newsGroups;
  }

  private List<com.sapient.news.response.NewsArticle> groupArticles(
      @NonNull List<NewsArticle> newsArticles) {
    return newsArticles.stream()
        .map(
            article ->
                com.sapient.news.response.NewsArticle.builder()
                    .author(article.getAuthor())
                    .title(article.getTitle())
                    .description(article.getDescription())
                    .source(article.getDescription())
                    .content(article.getContent())
                    .url(article.getUrl())
                    .imageUrl(article.getImageUrl())
                    .publishedAt(article.getPublishedAt())
                    .build())
        .collect(Collectors.toList());
  }

  private List<NewsArticle> getOverlappingNewsArticles(
      @NonNull DateInterval interval, List<NewsArticle> newsClientResponse, int currNewsIndex) {
    List<NewsArticle> overlappingNewsArticles = new ArrayList<>();
    while (currNewsIndex < newsClientResponse.size()
        && interval.overlaps(newsClientResponse.get(currNewsIndex).getPublishedAt())) {
      overlappingNewsArticles.add(newsClientResponse.get(currNewsIndex));
      currNewsIndex++;
    }
    return overlappingNewsArticles;
  }

  private List<DateInterval> getIntervals(
      @NonNull final NewsRequest newsRequest, @NonNull final List<NewsArticle> newsClientResponse) {
    LocalDateTime intervalEndTime = LocalDateTime.now();
    LocalDateTime intervalStartTime =
        intervalEndTime.minus(newsRequest.getInterval(), newsRequest.getChronoUnit());
    LocalDateTime earliestArticlePublishedAt =
        newsClientResponse.get(newsClientResponse.size() - 1).getPublishedAt();

    List<DateInterval> intervals = new ArrayList<>();
    while (intervalEndTime.isAfter(earliestArticlePublishedAt)) {
      intervals.add(
          DateInterval.builder()
              .startDate(
                  LocalDateTime.of(
                      intervalStartTime.toLocalDate(), intervalStartTime.toLocalTime()))
              .endDate(
                  LocalDateTime.of(intervalEndTime.toLocalDate(), intervalEndTime.toLocalTime()))
              .build());
      intervalStartTime =
          intervalStartTime.minus(newsRequest.getInterval(), newsRequest.getChronoUnit());
      intervalEndTime =
          intervalEndTime.minus(newsRequest.getInterval(), newsRequest.getChronoUnit());
    }
    return intervals;
  }
}
