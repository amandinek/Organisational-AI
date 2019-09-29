package models;

import java.util.Objects;

public class News {
    private String title;
    private String body;
    private int id;
    private int dept_Id;

    public News(String title, String body, int dept_Id) {
        this.title = title;
        this.body = body;
        this.dept_Id = dept_Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getdept_Id() {
        return dept_Id;
    }

    public void setdept_Id(int dept_Id) {
        this.dept_Id = dept_Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return id == news.id &&
                dept_Id == news.dept_Id &&
                Objects.equals(title, news.title) &&
                Objects.equals(body, news.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, body, id, dept_Id);
    }
}
