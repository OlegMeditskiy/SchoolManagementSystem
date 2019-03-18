package Controllers;

import EntityClasses.*;

import UI.UI;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;

public class EductionController extends AbstractController {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
    Scanner input = new Scanner(System.in);
    UI ui = new UI();

    @Override
    public void create(Object o) {

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public <T> void printList(List<T> t) {

        if (t.size() > 0) {
            t.forEach(System.out::println);

        } else {
            System.out.println("does not exist");
        }
    }

    @Override
    public void findByName(String name) {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT e from Education e WHERE LOWER(e.name) like LOWER(:name)");
        q.setParameter("name", "%" + name + "%");

        List<Education> education = (List<Education>) q.getResultList();
        printList(education);
    }

    @Override
    public Object findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Education e = em.find(Education.class, id);
        return e;

    }

    @Override
    public boolean delete(Long id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Education education = em.find(Education.class, id);
        List<Student> students = (List<Student>) education.getStudents();
        List<Course> courses = (List<Course>) education.getCourses();
        for (Course course : courses) {
            course.removeEducation(education);
        }
        for (Student student : students) {
            student.setEducation(null);
        }

        em.remove(em.find(Education.class, id));
        em.getTransaction().commit();
        em.close();

        return true;

    }

    @Override
    public boolean update(Object o) {

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
        em.close();

        return true;

    }

    public void displayall() {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT e from Education e");
        List<Education> educations = (List<Education>) q.getResultList();
        printList(educations);

    }

    public boolean cheackEducationsForCourse() {
        CourseController courseController = new CourseController();
        EntityManager em = emf.createEntityManager();
        Query qc = em.createQuery("SELECT e from Education e");
        List<Education> educationAvailable = (List<Education>) qc.getResultList();

        if (!educationAvailable.isEmpty()) {
            printList(educationAvailable);
            return true;
        } else {
            System.out.println("No educations");
            return false;
        }
    }

    public boolean checkAvailableCourses(Long educationID) {
        EntityManager em = emf.createEntityManager();
        CourseController courseController = new CourseController();
        try {
            Education e = em.find(Education.class, educationID);
            Query q = em.createQuery("SELECT c FROM Course c WHERE c NOT IN(SELECT c FROM C.educations e where e.id=:id)");
            q.setParameter("id", e.getId());
            List<Course> courses = q.getResultList();
            System.out.println("Availabale courses: ");
            courseController.printList(courses);
            if (!courses.isEmpty()) {
                return true;
            }
        } catch (NullPointerException ex) {
            System.out.println("No education with this id");
            return false;
        }
        return false;
    }

    public void addToCoursetoEducation(Long educationID, Long courseID) {

        CourseController courseController = new CourseController();
        EntityManager em = emf.createEntityManager();
        Education e = em.find(Education.class, educationID);
        Course c = em.find(Course.class, courseID);
        try {
            try {
                e.addCourse(c);
                update(e);
                courseController.update(c);
            } catch (RollbackException ex) {
                System.out.println("Education already has this course");
            }
        } catch (NullPointerException exe) {
            System.out.println("No Course with this id ");
        }
    }

    public void addStudentToEducation(Long educationID, Long studentID) {
        StudentController studentController = new StudentController();
        EntityManager em = emf.createEntityManager();
        Student s = em.find(Student.class, studentID);
        Education e = em.find(Education.class, educationID);
        try {
            if (s.getEducation() != null) {
                System.out.println("This student already has education");
            } else {
                try {
                    e.addStudent(s);
                    update(e);
                    studentController.update(s);
                } catch (RollbackException ex) {
                    System.out.println("Education already has this student");
                }
            }
        } catch (NullPointerException exe) {
            System.out.println("No Student with this id");
        }
    }

    public boolean checkEducationsForStudents() {
        EntityManager em = emf.createEntityManager();
        Query q1 = em.createQuery("SELECT e from Education e");
        List<Education> educations = (List<Education>) q1.getResultList();
        if (!educations.isEmpty()) {
            for (Education education : educations) {
                if (education.getStudents().size() < education.getMaxAntalStudenter()) {
                    int studentAmount = education.getStudents().size();
                    int maxAmount = education.getMaxAntalStudenter();
                    int places = maxAmount - studentAmount;
                    System.out.println(education + " / Places: " + places);
                } else {
                    System.out.println(education + " is full = NOT AVAILABLE");
                }
            }
            return true;
        } else {
            System.out.println("No educations");
            return false;
        }
    }

    public boolean checkAvailableStudents(Long educationID) {

        EntityManager em = emf.createEntityManager();
        try {
            Education e = em.find(Education.class, educationID);

            Query q = em.createQuery("SELECT s FROM Student s where s.education=null");
            List<Student> students = q.getResultList();
            if (e.getStudents().size() < e.getMaxAntalStudenter()) {
                System.out.println("Availabale students: ");
                printList(students);
                if (!students.isEmpty()) {
                    return true;
                }
            }
        } catch (NullPointerException ex) {
            System.out.println("No education with this id");
            return false;
        }
        return false;

    }

    public void showEducationsFullAndWithPlaces() {

        EntityManager em = emf.createEntityManager();
        Query q1 = em.createQuery("SELECT e from Education e");
        List<Education> educations = (List<Education>) q1.getResultList();
        if (!educations.isEmpty()) {
            for (Education education : educations) {
                if (education.getStudents().size() < education.getMaxAntalStudenter()) {
                    int studentAmount = education.getStudents().size();
                    int maxAmount = education.getMaxAntalStudenter();
                    int places = maxAmount - studentAmount;
                    System.out.println("Education " + education.getName() + " has " + places + " places ");
                } else {
                    System.out.println("Education " + education.getName() + " is full");
                }
            }
        } else {
            System.out.println("No educations");
        }
    }

    public boolean checkEducationWithCourses() {

        EntityManager em = emf.createEntityManager();
        Query qc = em.createQuery("SELECT e FROM Education e WHERE e IN(SELECT e FROM E.courses ec)");
        List<Education> educationAvailable = qc.getResultList();
        printList(educationAvailable);
        if (!educationAvailable.isEmpty()) {
            return true;
        } else {
            System.out.println("No educations");
            return false;
        }
    }

    public boolean checkEducationCourses(Long EducationID) {

        EntityManager em = emf.createEntityManager();
        try {
            Education e = em.find(Education.class, EducationID);
            Query q = em.createQuery("SELECT c FROM Course c WHERE c IN(SELECT c FROM C.educations e where e.id=:id)");
            q.setParameter("id", e.getId());
            List<Course> courses = q.getResultList();
            System.out.println("Availabale courses: ");
            printList(courses);
            if (!courses.isEmpty()) {
                return true;
            } else {
                System.out.println("This education does not has courses");
                return false;
            }
        } catch (NullPointerException ex) {
            System.out.println("No education with this id");
            return false;
        }
    }

    public void removeCourseFromEducation(Long educationID, Long courseID) {

        CourseController courseController = new CourseController();
        EntityManager em = emf.createEntityManager();
        Education e = em.find(Education.class, educationID);
        Course c = em.find(Course.class, courseID);
        if (!e.getCourses().contains(c)) {
            System.out.println("This education does not has this course");
        } else {
            try {
                e.removeCourse(c);
                update(e);
                courseController.update(c);
            } catch (NullPointerException ex) {
                System.out.println("No course with this id");
            }
        }
    }

    public boolean checkEducationsWithStudents() {
        EntityManager em = emf.createEntityManager();
        Query qc = em.createQuery("SELECT e from Education e WHERE e IN(SELECT e FROM E.students es)");
        List<Education> educationAvailable = (List<Education>) qc.getResultList();
        if (!educationAvailable.isEmpty()) {
            printList(educationAvailable);
            return true;
        } else {
            System.out.println("No educations");
            return false;
        }
    }

    public boolean checkEducationStudents(Long educationID) {

        EntityManager em = emf.createEntityManager();
        try {
            Education e = em.find(Education.class, educationID);
            Query q = em.createQuery("SELECT s FROM Student s WHERE s IN(SELECT s FROM S.education se where  se.id=:id)");
            q.setParameter("id", e.getId());
            List<Student> studentsAvailable = q.getResultList();
            printList(studentsAvailable);
            if (!studentsAvailable.isEmpty()) {
                return true;
            } else {
                System.out.println("This education does not has students");
                return false;
            }
        } catch (NullPointerException ex) {
            System.out.println("No education with this id");
            return false;
        }
    }

    public void removeStudentFromEducation(Long educationID, Long studentID) {
        StudentController studentController = new StudentController();
        EntityManager em = emf.createEntityManager();
        Education e = em.find(Education.class, educationID);
        Student s = em.find(Student.class, studentID);
        if (!e.getStudents().contains(s)) {
            System.out.println("This education does not has this student");
        } else {
            try {
                e.removeStudent(s);
                update(e);
                studentController.update(s);
            } catch (NullPointerException ex) {
                System.out.println("No student with this id");
            }
        }
    }

    public void CountOfAll() {
        EntityManager em = emf.createEntityManager();
        Query q1 = em.createQuery("SELECT COUNT(e) from Education e");
        Query q11 = em.createQuery("SELECT COUNT(c) from Course c INNER JOIN C.educations ce");
        Query q12 = em.createQuery("SELECT COUNT(e) from Education e INNER JOIN E.students se");

        Query q2 = em.createQuery("SELECT COUNT(c) from Course c");
        Query q21 = em.createQuery("SELECT COUNT(c) from Course c INNER JOIN C.teachers ct");
        Query q3 = em.createQuery("SELECT COUNT(t) from Teacher t");
        Query q4 = em.createQuery("SELECT COUNT(s) from Student s");
        System.out.println("Total educations: " + q1.getSingleResult());

        System.out.println("Total courses: " + q2.getSingleResult() + " / Total courses with education: " + q11.getSingleResult());

        System.out.println("Total teachers: " + q3.getSingleResult() + " / Total teachers with courses: " + q21.getSingleResult());

        System.out.println("Total students: " + q4.getSingleResult() + " / Total students with education: " + q12.getSingleResult());

    }
}
