package ru.newsfeed.trendsoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.newsfeed.trendsoft.dao.NewsDao;
import ru.newsfeed.trendsoft.model.Category;
import ru.newsfeed.trendsoft.model.News;

import java.util.List;

@Controller
public class FeedController {

    private final NewsDao newsDao;

    @Autowired
    public FeedController(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Category> categoryList = newsDao.getCategories();
        List<News> allNews = newsDao.getAllNews();
        model.addAttribute("news", allNews);
        model.addAttribute("categories", categoryList);
        return "index";
    }

    @GetMapping( value = "/filter", params = {"keywords", "category"})
    String filter(@RequestParam("keywords") String keywords, @RequestParam("category") String category, Model model) {
        List<Category> categoryList = newsDao.getCategories();
        List<News> allNews = newsDao.getNewsByCategoryAndKeywords(keywords, category);
        model.addAttribute("news", allNews);
        model.addAttribute("categories", categoryList);
        return "index";
    }

    @GetMapping( value = "/remove/{id}")
    String remove(@PathVariable("id") long id){

        newsDao.deleteNews(id);
        return "redirect:/";
    }

    @PostMapping(value = "/", params = {"name", "content", "category"})
    String add(@RequestParam("name") String name, @RequestParam("content") String content, @RequestParam("category") String category, Model model) {
        newsDao.addNews(name, content, category);
        List<Category> categoryList = newsDao.getCategories();
        List<News> allNews = newsDao.getAllNews();
        model.addAttribute("news", allNews);
        model.addAttribute("categories", categoryList);
        return "index";
    }
}
