package model;

import java.util.*;
import jakarta.persistence.*;

@Entity(name = "DEPARTMENTS")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentId;

    @Column(nullable = false, length = 128)
    private String name;

    @Column(nullable = false, length = 8)
    private String abbreviation;

    @OneToMany(mappedBy = "department")
    private List<Course> courses;

    public Department() {
    }

    public Department(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
        courses = new ArrayList<>();
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course c) {
        this.courses.add(c);
        c.setDepartment(this);
    }
}
