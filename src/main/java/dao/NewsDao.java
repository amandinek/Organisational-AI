package dao;

import models.News;

import java.util.List;

public interface NewsDao {
    void add(News news);
    List<News> all();
    List<News> allNewsOfDepartements(int dId);
    void deleteById(int id);
    void clearAll();
}
