import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

import jakarta.persistence.*;
import model.*;

public class App {
    public static int getIntRange( int low, int high ) {
        Scanner in = new Scanner( System.in );
        int input = 0;
        boolean valid = false;
        while( !valid ) {
            if( in.hasNextInt() ) {
                input = in.nextInt();
                if( input <= high && input >= low ) {
                    valid = true;
                } else {
                    System.out.print( "Invalid Range. Enter again: " );
                }
            } else {
                in.next(); //clear invalid string
                System.out.print( "Invalid Input. Enter again: " );
            }
        }
        return input;
    }

    public static void printRegistrationResult(Student.RegistrationResult result) {
        switch (result) {
            case SUCCESS:
                System.out.println("Success");
                break;
            case ALREADY_PASSED:
                System.out.println("Student is already passed this course");
                break;
            case ENROLLED_IN_SECTION:
                System.out.println("Student has already enrolled in this section");
                break;
            case  NO_PREREQUISITES:
                System.out.println("Student doesn't meet the prerequisites");
                break;
            case ENROLLED_IN_ANOTHER:
                System.out.println("Student has already enrolled in another section for this class");
                break;
            case TIME_CONFLICT:
                System.out.println("There is time conflict");
                break;
        }
    }

    public static void addData() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();

        // Semester
        Semester sem1 = new Semester("Spring 2021", LocalDate.of(2021, Month.JANUARY, 19));
        em.persist(sem1);
        Semester sem2 = new Semester("Fall 2021", LocalDate.of(2021, Month.AUGUST, 17));
        em.persist(sem2);
        Semester sem3 = new Semester("Spring 2022", LocalDate.of(2022, Month.JANUARY, 20));
        em.persist(sem3);

        // Departments
        Department d1 = new Department("Computer Engineering and Computer Science", "CECS");
        em.persist(d1);
        Department d2 = new Department("Italian Studies", "ITAL");
        em.persist(d2);

        // Courses & Prerequisite
        Course c1 = new Course("174", "Introduction to Programming and Problem Solving", (byte) 3);
        c1.addDepartment(d1);
        em.persist(c1);

        Course c2 = new Course("274", "Data Structures", (byte) 3);
        c2.addDepartment(d1);
        em.persist(c2);
        Prerequisite p = new Prerequisite(c2, c1, 'C');
        c2.addPrerequisite(p);
        em.persist(p);

        Course c3 = new Course("277", "Object Oriented Application Programming", (byte) 3);
        c3.addDepartment(d1);
        em.persist(c3);
        p = new Prerequisite(c3, c1, 'C');
        c3.addPrerequisite(p);
        em.persist(p);

        Course c4 = new Course("282", "Advanced C++", (byte) 3);
        c4.addDepartment(d1);
        em.persist(c4);
        p = new Prerequisite(c4, c2, 'C');
        c4.addPrerequisite(p);
        em.persist(p);
        p = new Prerequisite(c4, c3, 'C');
        c4.addPrerequisite(p);
        em.persist(p);

        Course c5 = new Course("101A", "Fundamentals of Italian", (byte) 4);
        c5.addDepartment(d2);
        em.persist(c5);
        Course c6 = new Course("101B", "Fundaments of Italian", (byte) 4);
        c6.addDepartment(d2);
        em.persist(c6);
        p = new Prerequisite(c6, c5, 'D');
        c6.addPrerequisite(p);
        em.persist(p);

        // TimeSlots
        /*
            1000000: Sunday
            0100000: Monday
            0010000: Tuesday
            0001000: Wednesday
            0000100: Thursday
            0000010: Friday
            0000001: Saturday
        */
        // MW, 12:30 - 1:45 pm
        TimeSlot time1 = new TimeSlot((byte)((1 << 5) | (1 << 3)), LocalTime.of(12, 30), LocalTime.of(13, 45));
        em.persist(time1);
        // TuTh, 2:00 - 3:15 pm
        TimeSlot time2 = new TimeSlot((byte)((1 << 4) | (1 << 2)), LocalTime.of(14, 0), LocalTime.of(15, 15));
        em.persist(time2);
        // MWF, 12:00 - 12:50 pm
        TimeSlot time3 = new TimeSlot((byte)((1 << 5) | (1 << 3) | (1 << 1)), LocalTime.of(12, 0), LocalTime.of(12, 50));
        em.persist(time3);
        //F, 8:00 - 10:45 am
        TimeSlot time4 = new TimeSlot((byte)(1 << 1), LocalTime.of(8, 0), LocalTime.of(10, 45));
        em.persist(time4);

        // Section
        //(a) CECS 174 section 1, Spring 2021, MW 12:30 - 1:45, capacity 105
        Section sA = new Section((byte) 1, (short) 105, time1, c1);
        sA.addSemester(sem1);
        em.persist(sA);

        // (b) CECS 274 section 1, Fall 2021, TuTh 2:00 - 3:15, capacity 140
        Section sB = new Section((byte) 1, (short) 140, time2, c2);
        sB.addSemester(sem2);
        em.persist(sB);

        //(c) CECS 277 section 3, Fall 2021, F 8:00 - 10:45, capacity 35
        Section sC = new Section((byte) 3, (short) 35, time4, c3);
        sC.addSemester(sem2);
        em.persist(sC);

        //(d) CECS 282 section 5, Spring 2022, TuTh 2:00 - 3:15, capacity 35
        Section sD = new Section((byte) 5, (short) 35, time2, c4);
        sD.addSemester(sem3);
        em.persist(sD);

        //(e) CECS 277 section 1, Spring 2022, MW 12:30 - 1:45, capacity 35
        Section sE = new Section((byte) 1, (short) 35, time1, c3);
        sE.addSemester(sem3);
        em.persist(sE);

        //(f) CECS 282 section 7, Spring 2022, MW 12:30 - 1:45, capacity 35
        Section sF = new Section((byte) 7, (short) 35, time1, c4);
        sF.addSemester(sem3);
        em.persist(sF);

        //(g) ITAL 101A section 1, Spring 2022, MWF 12:00 - 12:50, capacity 25*/
        Section sG = new Section((byte) 1, (short) 25, time3, c5);
        sG.addSemester(sem3);
        em.persist(sG);

        // Students
        // Naomi Nagata, ID 123456789.
        Student s1 = new Student("Naomi Nagata", 123456789);
        em.persist(s1);
        // Transcript: A in section (a). A in section (b). A in section (c).
        Transcript t = new Transcript(s1, sA, "A");
        s1.addTranscript(t);
        em.persist(t);

        t = new Transcript(s1, sB, "A");
        s1.addTranscript(t);
        em.persist(t);

        t = new Transcript(s1, sC, "A");
        s1.addTranscript(t);
        em.persist(t);
        // Currently Enrolled in section (d).
        Enrollment e = new Enrollment(s1, sD);
        s1.addSection(sD);
        em.persist(e);

        // James Holden, ID 987654321.
        Student s2 = new Student("James Holden", 987654321);
        em.persist(s2);
        //Transcript: C in section (a). C in section (b). C in section (c).
        t = new Transcript(s2, sA, "C");
        s2.addTranscript(t);
        em.persist(t);

        t = new Transcript(s2, sB, "C");
        s2.addTranscript(t);
        em.persist(t);

        t = new Transcript(s2, sC, "C");
        s2.addTranscript(t);
        em.persist(t);

        // Amos Burton, ID 555555555.
        Student s3 = new Student("Amos Burton", 555555555);
        em.persist(s3);
        //Transcript: C in section (a). B in section (b). D in section (c).
        t = new Transcript(s3, sA, "C");
        s3.addTranscript(t);
        em.persist(t);

        t = new Transcript(s3, sB, "B");
        s3.addTranscript(t);
        em.persist(t);

        t = new Transcript(s3, sC, "D");
        s3.addTranscript(t);
        em.persist(t);

        em.getTransaction().commit();
    }

    private static void studentLookup() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();

        // User enters the name of a student.
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter a student name: ");
        String studentName = input.nextLine();

        // Using a TypedQuery
        var namedStudent = em.createQuery("SELECT s FROM STUDENTS s WHERE "
            + "s.name = ?1", Student.class);
        namedStudent.setParameter(1, studentName);
        try {
            // Find that student if they exist
            Student student = namedStudent.getSingleResult();

            // Print the transcript in ascending order by semester start date
            List<Transcript> transcript = em.createQuery("SELECT t FROM STUDENTS s JOIN s.transcripts t JOIN t.section sec JOIN sec.semester sem WHERE " + "s.studentId = " + student.getStudentId() + " ORDER BY sem.startDate", Transcript.class).getResultList();
            System.out.println(studentName + "'s Transcript:");
            for (Transcript t : transcript) {
                System.out.println(t);
            }

            // Print the student's GPA
            System.out.printf(studentName + "'s GPA is %.2f\n", student.getGpa());

        }
        catch (NoResultException ex) {
            System.out.println("Student with name '" + studentName + "' not found.");
        }
    }

    private static void studentRegistration() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();

        // User chooses a semester, either by name or from a menu.
        Scanner input = new Scanner(System.in);
        System.out.println("Choose semester:\n1. By name\n2. From menu\nYour choice is: ");
        int choice = getIntRange(1, 2);
        Semester sem = null;
        switch (choice) {
            case 1: {
                System.out.println("Please enter a semester name: ");
                String semesterName = input.nextLine();

                var namedSemester = em.createQuery("SELECT s FROM SEMESTERS s WHERE "
                        + "s.title = ?1", Semester.class);
                namedSemester.setParameter(1, semesterName);

                try {
                    sem = namedSemester.getSingleResult();
                } catch (NoResultException ex) {
                    System.out.println("Semester '" + semesterName + "' does not exist.");
                    return;
                }
                break;
            }
            case 2: {
                List<Semester> semestersList = em.createQuery("SELECT s from SEMESTERS s", Semester.class).getResultList();
                for (Semester s : semestersList) {
                    System.out.println(s);
                }
                System.out.println("Your choice is: ");
                int c = getIntRange(1, semestersList.size());
                sem = semestersList.get(c - 1);
            }
        }

        // User enters the name of a student.
        input = new Scanner(System.in);
        System.out.println("Please enter a student name: ");
        String studentName = input.nextLine();

        // Using a TypedQuery
        var namedStudent = em.createQuery("SELECT s FROM STUDENTS s WHERE "
                + "s.name = ?1", Student.class);
        namedStudent.setParameter(1, studentName);
        try {
            // Find a student
            Student student = namedStudent.getSingleResult();

            // Get their transcript
            List<Transcript> transcript = em.createQuery("SELECT t FROM STUDENTS s JOIN s.transcripts t WHERE " + "s.studentId = " + student.getStudentId(), Transcript.class).getResultList();
            student.setTranscripts(transcript);

            // Get their Enrollments
            List<Enrollment> enrollment = em.createQuery("SELECT e FROM STUDENTS s JOIN s.enrollments e WHERE s.studentId = " + student.getStudentId(), Enrollment.class).getResultList();
            Set<Enrollment> enrollmentSet = new HashSet<>();
            for (Enrollment en : enrollment)
                enrollmentSet.add(en);
            student.setEnrollments(enrollmentSet);

            // User enters the name of a course section
            System.out.println("Please enter the name of course section: ");
            String sectionCourse = input.nextLine();
            String[] temp = sectionCourse.split(" ");
            String department = temp[0];
            temp = temp[1].split("-");
            String courseNumber = temp[0];
            int sectionNumber = (byte) Integer.parseInt(temp[1]);

            try {
                // Find the Section with the matching department abbreviation, course number, and section number
                Section section = em.createQuery("SELECT s FROM SECTIONS s JOIN s.course c JOIN c.department d JOIN s.semester sem WHERE s.sectionNumber = " + sectionNumber + " and c.number = '" + courseNumber + "' and d.abbreviation = '" + department + "' and sem.title = '" + sem.getTitle() + "'", Section.class).getSingleResult();

                // Get a course prerequisites
                List<Prerequisite> prerequisiteList = em.createQuery("SELECT p FROM SECTIONS s JOIN s.course c JOIN c.prerequisiteList p WHERE s.sectionId = " + section.getSectionId(), Prerequisite.class).getResultList();
                List<Prerequisite> prerequisite = new ArrayList<>();
                for (int i = 0; i < prerequisiteList.size(); i++){
                    if (prerequisiteList.get(i).getCourse().getCourseId() == section.getCourse().getCourseId())
                        prerequisite.add(prerequisiteList.get(i));
                }
                section.getCourse().setPrerequisiteList(prerequisite);

                // Call registerForSection on the Student chosen by the user, passing the chosen Section. Print out the result of the operation.
                printRegistrationResult(student.registerForSection(section));

            } catch (NoResultException ex) {
                System.out.println("Section '" + sectionCourse + "' in '" + sem.getTitle() + "' not found.");
                return;
            }

        } catch (NoResultException ex) {
            System.out.println("Student with name '" + studentName + "' not found.");
            return;
        }

    }
    public static void main(String[] args) throws Exception {
        addData();
        studentLookup();
        studentRegistration();
    }
}
