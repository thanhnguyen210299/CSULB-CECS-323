package model;

import java.util.*;
import jakarta.persistence.*;

@Entity(name = "SECTIONS")
//Section: unique (semesterID, courseID, sectionNumber)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "semesterId", "courseId", "sectionNumber"
        })})
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sectionId;

    @Column(nullable = false)
    private byte sectionNumber;

    @Column(nullable = false)
    // I think there maybe a class that will include a lots of students
    // It may be bigger than 127 (maximum of byte) and smaller than 32,767 (maximum of short)
    private short maxCapacity;

    @ManyToOne
    @JoinColumn(name = "semesterId")
    private Semester semester;

    @ManyToOne
    @JoinColumn(name = "timeSlotId")
    private TimeSlot timeSlot;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    @OneToMany(mappedBy = "section")
    private Set<Enrollment> enrollments;

    public Section() {
    }

    public Section(byte sectionNumber, short maxCapacity, TimeSlot timeSlot, Course course) {
        this.sectionNumber = sectionNumber;
        this.maxCapacity = maxCapacity;
        this.timeSlot = timeSlot;
        this.course = course;
        this.enrollments = new HashSet<>();
    }

    public Section(byte sectionNumber, short maxCapacity, TimeSlot timeSlot, Semester semester) {
        this.sectionNumber = sectionNumber;
        this.maxCapacity = maxCapacity;
        this.timeSlot = timeSlot;
        this.semester = semester;
        this.enrollments = new HashSet<>();
    }

    public Section(byte sectionNumber, short maxCapacity){
        this.sectionNumber = sectionNumber;
        this.maxCapacity = maxCapacity;
        this.enrollments = new HashSet<>();
    }

    public int getSectionId() {
        return sectionId;
    }

    public byte getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(byte sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public short getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(short maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public void addSemester(Semester s) {
        this.semester = s;
        s.addSection(this);
    }

    public void addStudent(Student s) {
        this.enrollments.add(new Enrollment(s, this));
    }
}