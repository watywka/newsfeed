package ru.newsfeed.trendsoft.dao;

import org.springframework.stereotype.Repository;
import ru.newsfeed.trendsoft.model.Category;
import ru.newsfeed.trendsoft.model.News;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class NewsDaoImpl implements NewsDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<News> getAllNews() {
        return entityManager.createQuery("from News", News.class).getResultList();
    }

    @Override
    public List<News> getNewsByCategoryAndKeywords(String keywords, String categories) {
        String f = categories.equals("") ? "" : " and category.name=\'"+categories+"\'";
        return entityManager.createQuery("FROM News where (content like :p1 or name like :p2)" + f, News.class)
                .setParameter("p1","%" + keywords + "%")
                .setParameter("p2", "%" + keywords + "%")
                .getResultList();
    }

    @Override
    public void addNews(String name, String content, String category) {
        List<Category> categoryList = entityManager.createQuery("From Category where name = :p1",Category.class)
                .setParameter("p1",category)
                .getResultList();
        Category category1;
        if (categoryList.isEmpty()) {
            category1 = new Category();
            category1.setName(category);
        } else {
            category1 = categoryList.get(0);
        }
        News news = new News();
        news.setContent(content);
        news.setDate(new Date());
        news.setName(name);
        news.setCategory(category1);
        entityManager.persist(news);
    }


    @Override
    public void deleteNews(Long id) {
        News news = entityManager.find(News.class, id);
        entityManager.remove(news);
    }

    @Override
    public void updateNews(Long id, String name, String content, String category) {
        List<Category> categoryList = entityManager.createQuery("From Category where name = :p1",Category.class)
                .setParameter("p1",category)
                .getResultList();
        Category category1;
        boolean newCat;
        if (categoryList.isEmpty()) {
            category1 = new Category();
            category1.setName(category);
        } else {
            category1 = categoryList.get(0);
        }
        News news = entityManager.find(News.class, id);
        long oldCategoryId = news.getCategory().getId();
        news.setName(name);
        news.setContent(content);
        news.setCategory(category1);
        entityManager.merge(news);
        List<News> newsList = entityManager.createQuery("From News where category_id = :p1",News.class)
                .setParameter("p1",oldCategoryId)
                .getResultList();
        if (newsList.isEmpty()) {
            entityManager.remove(entityManager.find(Category.class, oldCategoryId ));
        }
    }


    @Override
    public List<Category> getCategories() {
        return entityManager.createQuery("from Category ", Category.class).getResultList();
    }

    @Override
    public News getNewsById(long id) {
        return entityManager.createQuery("FROM News where id = :p1", News.class)
                .setParameter("p1",id)
                .getResultList().get(0);
    }
}
