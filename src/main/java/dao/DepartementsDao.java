package dao;

import models.Departements;
import models.User;

import java.util.List;

public interface DepartementsDao {
    void add(Departements departements);
    void addUserDepartements(Departements departements, User user);
    List<Departements> all();
    List<User>allusers(int dId);
    List<Departements> allDeptNews(int newsId);
    Departements findById(int id);
    void update(String dept_name,String description,int size,int id);
    void deleteById(int id);
    void clearAll();
}
