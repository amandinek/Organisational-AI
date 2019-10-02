package dao;

import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oNewsDao implements NewsDao {
    private final Sql2o sql2o;
    public Sql2oNewsDao(Sql2o sql2o){
        this.sql2o=sql2o;
    }
    @Override
    public void add(News news) {
        String data ="INSERT INTO news(title,body,dept_Id) VALUES(:title,:body,:dept_Id)";
        try(Connection con =sql2o.open()){
            int id=(int) con.createQuery(data,true)
                    .bind(news)
                    .executeUpdate()
                    .getKey();
            news.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<News> all() {
       try(Connection con= sql2o.open()){
           return con.createQuery("SELECT * FROM news")
                   .throwOnMappingFailure(false)
                   .executeAndFetch(News.class);
       }
    }


    @Override
    public List<News> allNewsOfDepartements(int dept_Id) {
        try(Connection con= sql2o.open()){
            return con.createQuery("SELECT * FROM news WHERE dept_Id=:dept_Id")
                    .addParameter("dept_Id",dept_Id)
                    .executeAndFetch(News.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String data ="DELETE FROM WHERE id=:id";
        try(Connection con =sql2o.open()){
             con.createQuery(data)
                     .addParameter("id", id)
                     .executeUpdate()
                     .getKey();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    @Override
    public void clearAll() {

    }
    @Override
    public News findById(int user_Id) {
        try(Connection con =sql2o.open()){
            return con.createQuery("SELECT * FROM news WHERE id=:id")
                    .addParameter("id",user_Id)
                    .executeAndFetchFirst(News.class);
        }

    }
}
