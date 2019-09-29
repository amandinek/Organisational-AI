package dao;

import models.Departements;
import models.User;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oDepartementsDao implements DepartementsDao {
    private final Sql2o sql2o;
    public Sql2oDepartementsDao(Sql2o sql2o){
        this.sql2o=sql2o;
    }
    @Override
    public void add(Departements departements) {
    }

    @Override
    public void addUserDepartements(Departements departements, User user) {

    }

    @Override
    public List<Departements> all() {
        return null;
    }

    @Override
    public List<User> allusers(int dId) {
        return null;
    }

    @Override
    public List<Departements> allDeptNews(int newsId) {
        return null;
    }

    @Override
    public Departements findById(int id) {
        return null;
    }

    @Override
    public void update(String dept_name, String description, int size, int id) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void clearAll() {

    }
}
