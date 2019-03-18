package Controllers;

import EntityClasses.Education;
import EntityClasses.Student;
import UI.UI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class StudentController extends AbstractController {

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
        Query q = em.createQuery("SELECT s from Student s WHERE LOWER(s.firstName) like LOWER(:firstName)");
        q.setParameter("firstName", "%" + name + "%");

        List<Student> students = (List<Student>) q.getResultList();
        printList(students);
    }

    @Override
    public Object findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Student s = em.find(Student.class, id);
        System.out.println(s);
        return s;

    }

    @Override
    public boolean delete(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Student s = em.find(Student.class, id);
        s.getEducation().removeStudent(s);
        em.remove(em.find(Student.class, id));
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
        Query q = em.createQuery("SELECT s from Student s");
        List<Student> students = (List<Student>) q.getResultList();
        printList(students);

    }

    public void showStudentAmount() {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT s from Student s");
        Query q1 = em.createQuery("SELECT e from Education e");
        Query q2 = em.createQuery("SELECT AVG(s.age) FROM Student s");

        int allStudents = q.getResultList().size();

        List<Education> educations = (List<Education>) q1.getResultList();

        if (!educations.isEmpty()) {
            int studentPercent = 0;
            for (Education education : educations) {
                int studentAmount = education.getStudents().size();
                studentPercent = (int) ((studentAmount * 100.0f) / allStudents);
                System.out.println("Education id: " + education.getId() + " / Student amount: " + studentAmount + " / Percent of all students: " + studentPercent + "%");
            }
            System.out.println("Total: " + allStudents);
            System.out.println("Average age: " + q2.getSingleResult());
        } else {
            System.out.println("No educations");
        }
    }
}
