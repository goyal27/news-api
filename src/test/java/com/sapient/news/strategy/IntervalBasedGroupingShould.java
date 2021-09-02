package com.sapient.news.strategy;

import com.sapient.news.client.model.common.NewsArticle;
import com.sapient.news.request.IntervalUnit;
import com.sapient.news.request.NewsRequest;
import com.sapient.news.response.NewsGroup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class IntervalBasedGroupingShould {
  @InjectMocks private IntervalBasedGrouping intervalBasedGrouping;
  public static final LocalDateTime NOW = LocalDateTime.now();
  public static final NewsArticle NEWS_ARTICLE_1 =
      NewsArticle.builder()
          .author("news-article-1-author")
          .title("news-article-1-title")
          .description("news-article-1-desc")
          .source("news-article-1-source")
          .url("news-article-1-url")
          .imageUrl("news-article-1-image-url")
          .content("news-article-1.content")
          .publishedAt(NOW)
          .build();
  public static final NewsArticle NEWS_ARTICLE_2 =
      NewsArticle.builder()
          .author("news-article-2-author")
          .title("news-article-2-title")
          .description("news-article-2-desc")
          .source("news-article-2-source")
          .url("news-article-2-url")
          .imageUrl("news-article-2-image-url")
          .content("news-article-2-content")
          .publishedAt(NOW.minusMinutes(10))
          .build();
  public static final NewsArticle NEWS_ARTICLE_3 =
      NewsArticle.builder()
          .author("news-article-3-author")
          .title("news-article-3-title")
          .description("news-article-3-desc")
          .source("news-article-3-source")
          .url("news-article-3-url")
          .imageUrl("news-article-3-image-url")
          .content("news-article-3.content")
          .publishedAt(NOW.minusHours(1).minusMinutes(10))
          .build();
  public static final NewsArticle NEWS_ARTICLE_4 =
      NewsArticle.builder()
          .author("news-article-4-author")
          .title("news-article-4-title")
          .description("news-article-4-desc")
          .source("news-article-4-source")
          .url("news-article-4-url")
          .imageUrl("news-article-4-image-url")
          .content("news-article-4.content")
          .publishedAt(NOW.minusHours(2).minusMinutes(10))
          .build();

  @Test
  public void shouldGroupArticlesINHourlyBuckets() {
    NewsRequest hourlyGroupingRequest =
        NewsRequest.builder().keyword("apple").interval(1).intervalUnit(IntervalUnit.Hour).build();
    List<NewsGroup> newsGroups =
        intervalBasedGrouping.groupNews(
            hourlyGroupingRequest,
            Arrays.asList(NEWS_ARTICLE_1, NEWS_ARTICLE_2, NEWS_ARTICLE_3, NEWS_ARTICLE_4));
    assertThat(newsGroups.size(), is(3));
    assertThat(newsGroups.get(0).getArticles().size(), is(2));
    assertThat(newsGroups.get(1).getArticles().size(), is(1));
    assertThat(newsGroups.get(2).getArticles().size(), is(1));

    assertThat(newsGroups.get(0).getArticles().get(0).getTitle(), is("news-article-1-title"));
    assertThat(newsGroups.get(0).getArticles().get(1).getTitle(), is("news-article-2-title"));

    assertThat(newsGroups.get(1).getArticles().get(0).getTitle(), is("news-article-3-title"));

    assertThat(newsGroups.get(2).getArticles().get(0).getTitle(), is("news-article-4-title"));
  }
}
