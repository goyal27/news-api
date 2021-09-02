package com.sapient.news.client;

import com.sapient.news.client.model.common.NewsArticle;
import com.sapient.news.client.model.news.api.NewsAPIResponse;
import com.sapient.news.exception.ServiceException;
import com.sapient.news.request.NewsRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.sapient.news.exception.ErrorCode.ERROR_NEWS_CLIENT;
import static com.sapient.news.util.Constant.NEWS_CLIENT_EXCEPTION;

@Component
@Slf4j
public class NewsApiClient implements NewsClient {
  @Autowired private RestTemplate restTemplate;

  @Value("${news.api.baseUrl}")
  private String baseUrl;

  @Value("${news.api.everythingApiPath}")
  private String everythingAPiPath;

  @Value("${news.api.apiKey}")
  private String apiKey;

  @Override
  public List<NewsArticle> getNews(@NonNull NewsRequest newsRequest) {
    try {
      final String url = createUrl(newsRequest);
      NewsAPIResponse newsAPIResponse = restTemplate.getForObject(url, NewsAPIResponse.class);
      if (Objects.isNull(newsAPIResponse) || newsAPIResponse.getArticles().isEmpty()) {
        log.info("Received empty response for get news for request {}", newsRequest);
        return Collections.emptyList();
      }
      return newsAPIResponse.getArticles().stream()
          .map(
              article ->
                  NewsArticle.builder()
                      .author(article.getAuthor())
                      .content(article.getContent())
                      .description(article.getDescription())
                      .imageUrl(article.getUrlToImage())
                      .url(article.getUrl())
                      .source(article.getSource().getName())
                      .publishedAt(article.getPublishedAt())
                      .build())
          .collect(Collectors.toList());
    } catch (RestClientException restClientException) {
      throw wrapRestClientException(restClientException, newsRequest);
    }
  }

  private ServiceException wrapRestClientException(
      final RestClientException restClientException, final NewsRequest newsRequest) {
    log.error("ResourceAccessException exception : {1}", restClientException);
    return new ServiceException(ERROR_NEWS_CLIENT, NEWS_CLIENT_EXCEPTION, newsRequest);
  }

  private String createUrl(final NewsRequest newsRequest) {
    final String url = baseUrl.concat(everythingAPiPath).concat("?");
    return UriComponentsBuilder.fromHttpUrl(url)
        .replaceQueryParam("apiKey", apiKey)
        .replaceQueryParam("q", newsRequest.getKeyword())
        .toUriString();
  }
}
