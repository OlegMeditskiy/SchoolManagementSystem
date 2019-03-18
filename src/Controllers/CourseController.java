package Controllers;

import EntityClasses.Course;
import EntityClasses.Education;
import EntityClasses.Teacher;
import UI.UI;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;

public class CourseController extends AbstractController {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");

    UI ui = new UI();
    Scanner input = new Scanner(System.in);

    /**
     *
     * @param o Lägg Object to database
     */
    @Override
    public void create(Object o) {

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        em.close();
    }

    /**
     *
     * @param <T>
     * @param t If list is not empty -> Print list Else -> write "does not
     * exist"
     */
    @Override
    public <T> void printList(List<T> t) {

        if (t.size() > 0) {
            t.forEach(System.out::println);

        } else {
            System.out.println("does not exist");
        }
    }

    /**
     * Print all courses
     */
    public void displayall() {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT c from Course c");
        List<Course> courses = q.getResultList();
        printList(courses);
    }

    /**
     * Find object by name
     *
     * @param name
     *
     */
    @Override
    public void findByName(String name) {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT c from Course c WHERE LOWER(c.name) like LOWER(:name)");
        q.setParameter("name", "%" + name + "%");

        List<Course> students = (List<Course>) q.getResultList();
        printList(students);
    }

    /**
     * Find object by id
     *
     * @param id
     * @return Object
     */
    @Override
    public Object findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Course c = em.find(Course.class, id);
        System.out.println(c);

        if (c == null) {
            return "Not a valid id";
        } else {
            return c;
        }

    }

    /**
     * Delete object by id
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(Long id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Course course = em.find(Course.class, id);
        List<Teacher> teachers = (List<Teacher>) course.getTeachers();
        List<Education> educations = (List<Education>) course.getEducations();
        for (Education education : educations) {
            course.removeEducation(education);
        }
        for (Teacher teacher : teachers) {
            course.removeTeacher(teacher);
        }
        em.remove(em.find(Course.class, id));
        em.getTransaction().commit();
        em.close();

        return true;
    }

    /**
     * Update object o
     *
     * @param o
     * @return
     */
    @Override
    public boolean update(Object o) {

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
        em.close();

        return true;

    }

    /**
     * Add teacher to course
     *
     */
    public void addTeacherToCourse(Long courseID, Long teacherID) {

        TeacherController teacherController = new TeacherController();
        EntityManager em = emf.createEntityManager();
        Course c = em.find(Course.class, courseID);
        Teacher t = em.find(Teacher.class, teacherID);

        try {
            try {
                c.addTeacher(t);
                update(c);
                teacherController.update(t);
            } catch (RollbackException ex) {
                System.out.println("Course already has this teacher");
            }

        } catch (NullPointerException exe) {
            System.out.println("No Teacher with this id ");
        }
    }

    public boolean availableCourses() {
        TeacherController teacherController = new TeacherController();
        EntityManager em = emf.createEntityManager();
        Query q1 = em.createQuery("SELECT c from Course c");
        List<Course> courses = (List<Course>) q1.getResultList();
        if (!courses.isEmpty()) {
            printList(courses);
            return true;
        } else {
            System.out.println("No courses");
            return false;
        }
    }

    public boolean checkAvailableTeachers(Long x) {
        TeacherController teacherController = new TeacherController();
        EntityManager em = emf.createEntityManager();
        try {
            Course c = em.find(Course.class, x);
            Query q = em.createQuery("SELECT t FROM Teacher t WHERE t NOT IN(SELECT t FROM T.courses c where c.id=:id)");
            q.setParameter("id", c.getId());
            List<Teacher> teachers = q.getResultList();
            System.out.println("Available teachers");
            teacherController.printList(teachers);
            if (!teachers.isEmpty()) {
                return true;
            }
        } catch (NullPointerException ex) {
            System.out.println("No course with this id");
            return false;
        }
        return false;

    }

    public boolean checkCoursesWithTeachers() {
        EntityManager em = emf.createEntityManager();
        TeacherController teacherController = new TeacherController();
        Query q = em.createQuery("SELECT c FROM Course c WHERE c IN(SELECT c FROM C.teachers t)");
        List<Course> courseAvailable = q.getResultList();
        teacherController.printList(courseAvailable);
        if (!courseAvailable.isEmpty()) {
            return true;
        } else {
            System.out.println("No courses");
            return false;
        }
    }

    public boolean checkCoursesTeachers(Long courseID) {
        EntityManager em = emf.createEntityManager();
        try {
            Course c = em.find(Course.class, courseID);
            Query q = em.createQuery("SELECT t FROM Teacher t WHERE t IN(SELECT t FROM T.courses ct where ct.id=:id)");
            q.setParameter("id", c.getId());
            List<Teacher> teacherAvailable = q.getResultList();
            printList(teacherAvailable);
            if (!teacherAvailable.isEmpty()) {
                return true;
            } else {
                System.out.println("This course does not has teachers");
                return false;
            }
        } catch (NullPointerException ex) {
            System.out.println("No course with this id");
            return false;
        }

    }

    public void removeTeacherFromCourse(Long courseID, Long teacherID) {
        EntityManager em = emf.createEntityManager();
        TeacherController teacherController = new TeacherController();
        Course c = em.find(Course.class, courseID);
        Teacher t = em.find(Teacher.class, teacherID);
        if (!c.getTeachers().contains(t)) {
            System.out.println("This course does not has this teacher");
        } else {
            try {
                t.removeCourse(c);
                update(c);
                teacherController.update(t);
            } catch (NullPointerException ex) {
                System.out.println("No teacher with this id");
            }
        }
    }

    /**
     * Show amount of courses,procent of all courses for every education / total
     * amount of courses
     */
    public void showCoursesAmont() {

        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT c from Course c");
        Query q1 = em.createQuery("SELECT e from Education e");
        int allCourses = q.getResultList().size();
        List<Education> educations = (List<Education>) q1.getResultList();
        /**
         * If we dont have courses -> write "No courses" Else -> do next actions
         */
        if (!educations.isEmpty()) {
            int coursesPercent = 0;
            for (Education education : educations) {
                int coursesAmount = education.getCourses().size();
                coursesPercent = (int) ((coursesAmount * 100.0f) / allCourses);
                System.out.println("Education id: " + education.getId() + " / Course amount: " + education.getCourses().size() + " / Percent of all courses: " + coursesPercent + "%");
                allCourses += education.getCourses().size();
            }
            System.out.println("Total: " + allCourses);
        } else {
            System.out.println("No educations");
        }
    }

}
