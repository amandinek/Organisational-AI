package models;

import java.util.Objects;

public class Departements {
    private String dept_name;
    private String dept_descriptions;
    private int dept_size;
    private int id;

    public Departements(String dept_name, String dept_descriptions, int dept_size) {
        this.dept_name = dept_name;
        this.dept_descriptions = dept_descriptions;
        this.dept_size = dept_size;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getDept_descriptions() {
        return dept_descriptions;
    }

    public void setDept_descriptions(String dept_descriptions) {
        this.dept_descriptions = dept_descriptions;
    }

    public int getDept_size() {
        return dept_size;
    }

    public void setDept_size(int dept_size) {
        this.dept_size = dept_size;
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
        if (o == null || getClass() != o.getClass()) return false;
        Departements that = (Departements) o;
        return id == that.id &&
                Objects.equals(dept_name, that.dept_name) &&
                Objects.equals(dept_descriptions, that.dept_descriptions) &&
                Objects.equals(dept_size, that.dept_size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dept_name, dept_descriptions, dept_size, id);
    }
}
