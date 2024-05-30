package model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "ENROLLMENTS")
public class Enrollment {
    @Id
    @JoinColumn(name = "studentId")
    @ManyToOne
    private Student student;

    @Id
    @JoinColumn(name = "sectionId")
    @ManyToOne
    private Section section;

    public Enrollment() {
    }

    public Enrollment(Student student, Section section) {
        this.student = student;
        this.section = section;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}