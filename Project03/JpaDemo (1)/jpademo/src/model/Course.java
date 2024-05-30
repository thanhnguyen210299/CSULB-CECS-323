package model;

import java.util.*;
import jakarta.persistence.*;

@Entity(name = "COURSES")
//Course: unique (department ID, number).
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "departmentId", "number"
        })})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;

    @Column(nullable = false, length = 8)
    private String number;

    @Column(nullable = false, length = 64)
    private String title;

    @Column(nullable = false)
    // the maximum value of byte is 127, so a byte is possible to handle all the units value
    private byte units;

    @ManyToOne
    @JoinColumn(name = "departmentId")
    private Department department;

    @OneToMany
    @JoinColumn(name = "courseId")
    private List<Prerequisite> prerequisiteList;

    public Course() {
    }

    public Course(String number, String title, byte units) {
        this.number = number;
        this.title = title;
        this.units = units;
        this.prerequisiteList = new ArrayList<>();
    }

    public int getCourseId() {
        return courseId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte getUnits() {
        return units;
    }

    public void setUnits(byte units) {
        this.units = units;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Prerequisite> getPrerequisiteList() {
        return prerequisiteList;
    }

    public void setPrerequisiteList(List<Prerequisite> prerequisiteList) {
        this.prerequisiteList = prerequisiteList;
    }

    public void addDepartment(Department d) {
        this.department = d;
        d.addCourse(this);
    }

    public void addPrerequisite(Prerequisite p) {
        this.prerequisiteList.add(p);
    }
}