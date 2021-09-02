package com.sapient.news.client.model.news.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class NewsAPIResponse {
    private  Status status;
    private  String message;
    private  int totalResults;
    private  List<Article> articles;
}
