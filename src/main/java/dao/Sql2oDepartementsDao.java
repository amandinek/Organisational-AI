package dao;

import models.Departements;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oDepartementsDao implements DepartementsDao {
    private final Sql2o sql2o;
    public Sql2oDepartementsDao(Sql2o sql2o){
        this.sql2o=sql2o;
        int id;
    }
    @Override
    public void add(Departements departements) {
        String data="INSERT INTO departements( dept_name,dept_descriptions,dept_size) VALUES (:dept_name,:dept_descriptions,:dept_size);";
        try (Connection con = sql2o.open()){
            int id=(int)con.createQuery(data,true)
                    .bind(departements)
                    .executeUpdate()
                    .getKey();
            departements.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void addUserDepartements(Departements departements, User user) {
        String data ="INSERT INTO users_in_departements(departementsId,userId) VALUES(:dept_id,:userId)";
        try(Connection con =sql2o.open()) {
            con.createQuery(data)
                    .addParameter("dept_id",departements.getId())
                    .addParameter("userId",user.getId())
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    @Override
    public List<Departements> all() {
        try(Connection con= sql2o.open()){
            return con.createQuery("SELECT * FROM departements")
                    .executeAndFetch(Departements.class);
        }
    }

    @Override
    public List<User> allUsers(int dept_Id) {
        return null;
    }

//    @Override
    public List<User> allusers(int dept_Id) {
        return null;
    }

    @Override
    public List<Departements> allDeptNews(int newsId) {

        String data ="SELECT * FROM news WHERE departementId =:deptId";
        try(Connection con=sql2o.open()){
            return con.createQuery(data)
                    .addParameter("newsId",newsId)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Departements.class);
        }
    }

    @Override
    public Departements findById(int id) {
        return null;
    }

    @Override
    public void update(String dept_name, String description, int dept_size, int id) {
        String data ="UPDATE departements SET(dept_name,description,dept_size)=(:dept_name,:description,:dept_size)";
        try(Connection con=sql2o.open()){
            con.createQuery(data)
                    .addParameter("dept_name",dept_name)
                    .addParameter("description",description)
                    .addParameter("dept_size",dept_size)
                    .addParameter("id",id)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void clearAll() {
        String data ="DELETE * FROM departements";
        try(Connection con= sql2o.open()){
            con.createQuery(data).executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);

        }

    }
}
