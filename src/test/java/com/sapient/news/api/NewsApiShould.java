package com.sapient.news.api;

import com.sapient.news.exception.BadRequestException;
import com.sapient.news.service.NewsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NewsApiShould {
    @InjectMocks private NewsApi newsApi;
    @Mock private NewsService newsService;

    @Test(expected = BadRequestException.class)
    public void shouldThrowBadRequestExceptionIfOneOfIntervalDataIsPassed(){
        newsApi.getNews("keyword", 1, null);
    }


}
