package com.sapient.news.client.model.news.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Article {
    private  Source source;
    private  String author;
    private  String title;
    private  String description;
    private  String url;
    private  String urlToImage;
    private  LocalDateTime publishedAt;
    private  String content;
}
