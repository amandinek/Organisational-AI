package models;

import java.util.Objects;

public class News {
    private String title;
    private String body;
    private int id;
    private int dId;

    public News(String title, String body, int dId) {
        this.title = title;
        this.body = body;
        this.dId = dId;
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

    public int getdId() {
        return dId;
    }

    public void setdId(int dId) {
        this.dId = dId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return id == news.id &&
                dId == news.dId &&
                Objects.equals(title, news.title) &&
                Objects.equals(body, news.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, body, id, dId);
    }
}
