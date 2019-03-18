package Controllers;

import EntityClasses.Course;
import EntityClasses.Education;
import EntityClasses.Teacher;
import UI.UI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class TeacherController extends AbstractController {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");

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
        Query q = em.createQuery("SELECT t FROM Teacher t WHERE LOWER(t.firstName) like LOWER(:firstName)");
        q.setParameter("firstName", name);
        List<Teacher> teachers = (List<Teacher>) q.getResultList();
        printList(teachers);
    }

    @Override
    public Object findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Teacher t = em.find(Teacher.class, id);
        System.out.println(t);
        return t;

    }

    @Override
    public boolean delete(Long id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Teacher teacher = em.find(Teacher.class, id);
        teacher.getCourses().clear();
        em.remove(em.find(Teacher.class, id));
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
        Query q = em.createQuery("SELECT t FROM Teacher t");
        List<Teacher> teachers = (List<Teacher>) q.getResultList();
        printList(teachers);

    }

    public void showTeacherAmont() {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT c from Course c");
        Query q1 = em.createQuery("SELECT e from Education e");
        List<Education> educations = (List<Education>) q1.getResultList();
        List<Course> courses = (List<Course>) q.getResultList();
        if (!educations.isEmpty()) {
            int teacherByEducation = 0;
            int allTeachers = q.getResultList().size();
            int teachersPercent = 0;
            for (Education education : educations) {
                for (Course course : courses) {
                    teacherByEducation += course.getTeachers().size();

                }
                int teacherAmount = teacherByEducation;
                teachersPercent = (int) ((teacherByEducation * 100.0f) / allTeachers);
                System.out.println("Education id: " + education.getId() + " / Teacher amount: " + teacherByEducation + " / Percent of all teachers: " + teachersPercent + "%");
            }
            System.out.println("Total: " + allTeachers);
        } else {
            System.out.println("No educations");
        }
    }
}
