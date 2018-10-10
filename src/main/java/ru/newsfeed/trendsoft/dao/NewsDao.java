package ru.newsfeed.trendsoft.dao;

import ru.newsfeed.trendsoft.model.Category;
import ru.newsfeed.trendsoft.model.News;

import java.util.List;

public interface NewsDao {

    List<News> getAllNews();
    List<News> getNewsByCategoryAndKeywords(String keywords, String categories);
    void addNews(String name, String content, String category);
    void deleteNews(Long id);
    void updateNews(Long id, String name, String content, String category);
    List<Category> getCategories();

    News getNewsById(long id);
}
