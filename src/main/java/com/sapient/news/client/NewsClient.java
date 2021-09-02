package com.sapient.news.client;

import com.sapient.news.client.model.common.NewsArticle;
import com.sapient.news.request.NewsRequest;
import lombok.NonNull;

import java.util.List;

public interface NewsClient {
    List<NewsArticle> getNews(@NonNull NewsRequest newsRequest);
}
