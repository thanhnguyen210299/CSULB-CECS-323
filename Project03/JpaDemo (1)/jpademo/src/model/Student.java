package model;

import java.util.*;
import jakarta.persistence.*;

@Entity(name = "STUDENTS")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;

    // The ID of a student is around 9 digits
    // A maximum value of int is 2^31 - 1 which is 2,147,483,647
    // so it may cover the value of student ID
    private int ID;

    @Column(nullable = false, length = 128)
    private String name;

    @OneToMany(mappedBy = "student")
    private Set<Enrollment> enrollments;

    @OneToMany(mappedBy = "student")
    private List<Transcript> transcripts;

    public enum RegistrationResult {
        SUCCESS, ALREADY_PASSED, ENROLLED_IN_SECTION, NO_PREREQUISITES, ENROLLED_IN_ANOTHER, TIME_CONFLICT
    };

    public Student() {
    }

    public Student(String name, int ID) {
        this.name = name;
        this.ID = ID;
        this.transcripts = new ArrayList<>();
        this.enrollments = new HashSet<>();
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public List<Transcript> getTranscripts() {
        return transcripts;
    }

    public void setTranscripts(List<Transcript> transcripts) {
        this.transcripts.clear();
        for (Transcript t : transcripts)
            this.transcripts.add(t);
    }

    public void addSection(Section s) {
        this.enrollments.add(new Enrollment(this, s));
    }

    public void addTranscript(Transcript t) {
        this.transcripts.add(t);
    }

    public double getGpa() {
        int totalGradePoints = 0;
        int gradePoints = 0;
        int units = 0;
        int totalUnits = 0;
        for (Transcript t : transcripts) {
            switch (t.getGradeEarned()) {
                case "A":
                    gradePoints = 4;
                    break;
                case "B":
                    gradePoints = 3;
                    break;
                case "C":
                    gradePoints = 2;
                    break;
                case "D":
                    gradePoints = 1;
                    break;
                default:
                    gradePoints = 0;
            }

            units = t.getSection().getCourse().getUnits();

            totalUnits += units;
            totalGradePoints += gradePoints * units;
        }

        return (double) (totalGradePoints) / totalUnits;
    }

    public RegistrationResult registerForSection(Section s) {
        /*
            1. The student has already received a "C" or better in the course.
            2. The student is already enrolled in the section.
            3. The student has not met the course prerequisites.
            4. The student is enrolled in a different section of that course.
            5. The student is enrolled in another course section with a time conflict:
                the sections meet on the same day, with at least 1 minute of overlap in their start and end times.
         */
        // The student has already received a "C" or better in the course.
        for (Transcript t : transcripts)
            if (t.getSection().getCourse() == s.getCourse()) {
                if (t.getGradeEarned() == "A" || t.getGradeEarned() == "B" || t.getGradeEarned() == "C")
                    return RegistrationResult.ALREADY_PASSED;
            }
        // The student is already enrolled in the section.
        for (Enrollment e : enrollments)
            if (e.getSection() == s) {
                return RegistrationResult.ENROLLED_IN_SECTION;
            }

        // The student has not met the course prerequisites.
        if (s.getCourse().getPrerequisiteList().size() != 0) {
            int count = 0;
            for (Transcript t : transcripts) {
                for (Prerequisite p : s.getCourse().getPrerequisiteList()) {
                    if (t.getSection().getCourse() == p.getPrerequisite() && s.getCourse() == p.getCourse()) {
                        if (!p.checkPrerequisite(t.getGradeEarned())) {
                            return RegistrationResult.NO_PREREQUISITES;
                        } else count++;
                    }
                }
            }
            if (count != s.getCourse().getPrerequisiteList().size()) {
                return RegistrationResult.NO_PREREQUISITES;
            }
        }

        // The student is enrolled in a different section of that course.
        for (Enrollment e : enrollments){
            if (s.getCourse() == e.getSection().getCourse())
                return RegistrationResult.ENROLLED_IN_ANOTHER;
        }

        // The student is enrolled in another course section with a time conflict:
        // the sections meet on the same day, with at least 1 minute of overlap in their start and end times.
        byte temp;
        for (Enrollment sI : enrollments){
            temp = (byte) (sI.getSection().getTimeSlot().getDaysOfWeek() & s.getTimeSlot().getDaysOfWeek());
            if (sI.getSection().getSemester() == s.getSemester()){
                if (temp != 0) {
                    if (sI.getSection().getTimeSlot().getStartTime().isBefore(s.getTimeSlot().getEndTime()) || sI.getSection().getTimeSlot().getEndTime().isAfter(s.getTimeSlot().getStartTime()))
                        return RegistrationResult.TIME_CONFLICT;
                }
            }
        }

        // Add the enrollment into database
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        Enrollment success = new Enrollment(this, s);
        em.persist(success);
        this.addSection(s);
        em.getTransaction().commit();

        return RegistrationResult.SUCCESS;
    }
}
