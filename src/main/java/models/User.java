package models;

import java.util.Objects;

public class User {
    private String user_name;
    private String user_position;
    private String user_role;
    private int id;

    public User(String user_name, String user_position, String user_role) {
        this.user_name = user_name;
        this.user_position = user_position;
        this.user_role = user_role;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_position() {
        return user_position;
    }

    public void setUser_position(String user_position) {
        this.user_position = user_position;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return  Objects.equals(user_name, user.user_name) &&
                Objects.equals(user_position, user.user_position) &&
                Objects.equals(user_role, user.user_role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_name, user_position, user_role, id);
    }
}
