import com.google.gson.Gson;
import dao.*;
import exception.ApiException;
import models.Departements;
import models.News;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {

        port(getHerokuAssignedPort());
        staticFileLocation("/public");

 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Sql2oNewsDao newsDao;
        Sql2oDepartementsDao departementsDao;
        Sql2oUserDao userDao;
        Connection con;
        Gson gson = new Gson();

        String connectionString = "jdbc:postgresql://localhost:5432/organisationapi";
        Sql2o sql2o = new Sql2o(connectionString, "karambizi", "12345");
        departementsDao = new Sql2oDepartementsDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        con = sql2o.open();

 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/departments/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "department-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/departments/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String dept_name = request.queryParams("dept_name");
            String dept_descriptions = request.queryParams("dept_descriptions");
            int dept_size =Integer.parseInt(request.queryParams("dept_size"));
            Departements departements = new Departements(dept_name,dept_descriptions, dept_size);
            departementsDao.add(departements);
            model.put("departments",departementsDao.all());
            return new ModelAndView(model, "departement.hbs");
        }, new HandlebarsTemplateEngine());

        get("/new/user",(request, response) -> {
            Map<String,Object> model = new HashMap<>();
            return new ModelAndView(model,"user-form.hbs");
        },new HandlebarsTemplateEngine());

        post("/new/user", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String user_name = req.queryParams("user_name");
            String user_position = req.queryParams("user_position");
            String user_role = req.queryParams("user_role");
            User users = new User( user_name,user_position, user_role);
            userDao.add(users);
            model.put("users",userDao.all());
            return new ModelAndView(model,"user.hbs");
        },new HandlebarsTemplateEngine());

        get("/news/new",(request, response) -> {
            Map<String,Object> model = new HashMap<>();
            model.put("news", newsDao.all());
            return new ModelAndView(model,"news-form.hbs");
        },new HandlebarsTemplateEngine());

        post("/news/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String title = req.queryParams("title");
            String body = req.queryParams("body");
            int dept_Id = Integer.parseInt(req.queryParams("dept_Id"));
            News news = new News(title,body, dept_Id);
            newsDao.add(news);
            model.put("news",newsDao.all());
            return new ModelAndView(model,"news.hbs");
        },new HandlebarsTemplateEngine());

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        post("/departement/:dept_Id/user/:id", "application/json", (req, res) -> {

            int dept_Id = Integer.parseInt(req.params("dept_Id"));
            int user_Id = Integer.parseInt(req.params("id"));
            Departements departements = departementsDao.findById(dept_Id);
            User user = userDao.findById( user_Id);

            if (departements!= null && user != null){
                //both exist and can be associated
                userDao.addUserDepartement(user, departements);
                res.status(201);
                return gson.toJson(String.format("no result",user.getUser_name(), departements.getDept_name()));
            }
            else {
                throw new ApiException(404, String.format("no result"));
            }
        });

        get("/departments/:id/users", "application/json", (req, res) -> {
            int dept_Id = Integer.parseInt(req.params("id"));
            Departements departmentFind = departementsDao.findById(dept_Id);
            if (departmentFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            else if (departementsDao.allUsers(dept_Id).size()==0){
                return "{\"message\":\"I'm sorry, but no users were found for this department.\"}";
            }
            else {
                return gson.toJson(departementsDao.allUsers(dept_Id));
            }
        });

        post("/departments/new", "application/json", (req, res) -> {
            Departements department = gson.fromJson(req.body(), Departements.class);
            departementsDao.add(department);
            res.status(201);
            return gson.toJson(department);
        });

        post("/departments/:department_id/news/new", "application/json", (req, res) -> {
            int dept_Id = Integer.parseInt(req.params("dept_Id"));
            News news = gson.fromJson(req.body(), News.class);
            news.setdept_Id(dept_Id); //we need to set this separately because it comes from our route, not our JSON input.
            newsDao.add(news);
            res.status(201);
            return gson.toJson(news);
        });


        get("/departments", "application/json", (req, res) -> {
            return gson.toJson(departementsDao.all());
        });

        get("/departments/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int department_id = Integer.parseInt(req.params("id"));
            return gson.toJson(departementsDao.findById(department_id));
        });

        get("/departments/:id/news", "application/json", (req, res) -> {
            int dept_Id = Integer.parseInt(req.params("id"));
            Departements departmentToFind = departementsDao.findById(dept_Id);
            List<News> allNews;
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            allNews = newsDao.allNewsOfDepartements(dept_Id);
            return gson.toJson(allNews);
        });
        get("/news", "application/json", (req, res) -> {
            return gson.toJson(newsDao.all());
        });

        post("/News/new", "application/json", (req, res) -> {
            News news = gson.fromJson(req.body(), News.class);
            newsDao.add(news);
            res.status(201);
            return gson.toJson(news);
        });

        get("/users", "application/json", (req, res) -> {
            return gson.toJson(userDao.all());
        });

        post("/users/new", "application/json", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            userDao.add(user);
            res.status(201);
            return gson.toJson(user);
        });

//        exception(ApiException.class, (exception, req, res) -> {
//            ApiException err = exception;
//            Map<String, Object> jsonMap = new HashMap<>();
//            jsonMap.put("status", err.getStatusCode());
//            jsonMap.put("errorMessage", err.getMessage());
//            res.type("application/json");
//            res.status(err.getStatusCode());
//            res.body(gson.toJson(jsonMap));
//        });
//
//        after((req, res) ->{
//            res.type("application/json");
//        });
    }

    }

