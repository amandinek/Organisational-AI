package dao;

import models.Departements;
import models.User;

import java.util.List;

public interface UserDAo {
    void add (User user);
    void addUserDepartement(User user, Departements  departements);
    List<User>all();
    List<Departements>allDepartements(int id);

    void deleteById(int id);
    void clearAll();

    User findById(int id);
}
