package dao;

import models.News;

import java.util.List;

public interface NewsDao {
    void add(News news);
    List<News> all();
    List<News> allNewsOfDepartements(int dept_Id);
    void deleteById(int id);
    void clearAll();

   News findById(int id);
}
