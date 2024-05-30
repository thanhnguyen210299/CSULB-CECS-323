package model;

import java.util.*;
import java.time.*;
import jakarta.persistence.*;

@Entity(name = "SEMESTERS")
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int semesterId;

    @Column(nullable = false, length = 16)
    private String title;

    @Column(nullable = false)
    private LocalDate startDate;

    @OneToMany(mappedBy = "semester")
    private List<Section> sections;

    public Semester() {
    }

    public Semester(String title, LocalDate startDate) {
        this.title = title;
        this.startDate = startDate;
        this.sections = new ArrayList<>();
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public void addSection(Section s) {
        this.sections.add(s);
        s.setSemester(this);
    }

    @Override
    public String toString() {
        return semesterId + ". " + title;
    }
}