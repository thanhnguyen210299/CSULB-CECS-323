package model;

import jakarta.persistence.*;

@Entity(name = "TRANSCRIPTS")
public class Transcript {
    @Id
    @JoinColumn(name = "studentId")
    @ManyToOne
    private Student student;

    @Id
    @JoinColumn(name = "sectionId")
    @ManyToOne
    private Section section;

    @Id
    @Column(nullable = false, length = 2)
    private String gradeEarned;

    public Transcript() {
    }

    public Transcript(Student student, Section section, String gradeEarned) {
        this.student = student;
        this.section = section;
        this.gradeEarned = gradeEarned;
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

    public String getGradeEarned() {
        return gradeEarned;
    }

    public void setGradeEarned(String gradeEarned) {
        this.gradeEarned = gradeEarned;
    }

    @Override
    public String toString() {
        return this.section.getCourse().getDepartment().getAbbreviation() + " " + this.section.getCourse().getNumber() + ", " + this.section.getSemester().getTitle() + ". Grade earned: " + this.gradeEarned;
    }
}
