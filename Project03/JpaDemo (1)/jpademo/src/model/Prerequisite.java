package model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "PREREQUISITES")
public class Prerequisite {
    @Id
    @ManyToOne
    @JoinColumn(referencedColumnName = "courseId")
    private Course course;

    @Id
    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course prerequisite;

    @Column(nullable = false)
    private char minimumGrade;

    public Prerequisite() {
    }

    public Prerequisite(Course course, Course prerequisite, char minimumGrade) {
        this.course = course;
        this.prerequisite = prerequisite;
        this.minimumGrade = minimumGrade;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(Course prerequisite) {
        this.prerequisite = prerequisite;
    }

    public char getMinimumGrade() {
        return minimumGrade;
    }

    public void setMinimumGrade(char minimumGrade) {
        this.minimumGrade = minimumGrade;
    }

    public boolean checkPrerequisite(String gradeEarned) {
        if (gradeEarned.length() != 1)
            return false;
        if (gradeEarned.charAt(0) < minimumGrade)
            return true;
        return false;
    }
}
