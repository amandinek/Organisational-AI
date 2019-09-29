package dao;

import models.Departements;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oUserDao implements UserDAo{
    private final Sql2o sql2o;
    public Sql2oUserDao(Sql2o sql2o){
        this.sql2o=sql2o;
    }
    @Override
    public void add(User user) {
        String data ="INSERT INTO users(user_name,user_position,user_role) VALUES(:user_name,:user_position,:user_role)";
        try(Connection con =sql2o.open()){
            int id=(int) con.createQuery(data,true)
                    .bind(user)
                    .executeUpdate()
                    .getKey();
            user.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    @Override
    public void addUserDepartement(User user, Departements departements) {
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
    public List<User> all() {
        try(Connection con= sql2o.open()){
            return con.createQuery("SELECT * FROM users")
                    .executeAndFetch(User.class);
            }
    }

    @Override
    public List<Departements> allDepartements(int userId) {
        List<Departements>departements=new ArrayList<>();
        String joinCode ="SELECT departementsId FROM user_in_departement WHERE userId=:userId";
        try(Connection con =sql2o.open()){
            List<Integer>allDepartementIds =con.createQuery(joinCode)
                    .addParameter("userId",userId)
                    .executeAndFetch(Integer.class);
            for (Integer deptId:allDepartementIds){
                String departementQuery ="SELECT * FROM departements WHERE id = :deptId";
                departements.add(
                        con.createQuery(departementQuery)
                            .addParameter("deptId",deptId)
                            .executeAndFetchFirst(Departements.class)
                );

            }

        }catch (Sql2oException ex){
            System.out.println(ex);

        }
        return departements;
    }

    @Override
    public void deleteById(int id) {
        String sql ="DELETE *from users WHERE id =:id";
        String delete ="DELETE *from user_in_departement WHERE userId =:userId";
        try(Connection con =sql2o.open()){
            con.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
            con.createQuery(delete)
                    .addParameter("userId",id)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);

        }

    }

    @Override
    public void clearAll() {
        String data ="DELETE * FROM users";
        try(Connection con= sql2o.open()){
            con.createQuery(data).executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);

        }

    }

    @Override
    public User findById(int user_id) {
        try(Connection con =sql2o.open()){
         return con.createQuery("SELECT * FROM users WHERE id=:id")
                  .addParameter("id",user_id)
                 .executeAndFetchFirst(User.class);
        }

    }
}
