package johnfinalproject;

import Controllers.*;
import EntityClasses.*;
import UI.UI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Menu {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
    EntityManager em = emf.createEntityManager();

    TeacherController t = new TeacherController();
    CourseController c = new CourseController();
    EductionController e = new EductionController();
    StudentController s = new StudentController();
    UI ui = new UI();
    int choice;
    
    public void mainMenu() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("*************************");
            System.out.println("School Management System");
            System.out.println("*************************\n");
            System.out.println("-------------------------");
            System.out.println("Main Menu\n");
            System.out.println("1. Display");
            System.out.println("2. Register new");
            System.out.println("3. Find");
            System.out.println("4. Update");
            System.out.println("5. Delete");
            System.out.println("6. Add entity to entity");
            System.out.println("7. Remove entity from entity");
            System.out.println("8. Statistic"); // todo
            System.out.println("0. Exit");
            System.out.println("------------------------");
            System.out.print("Enter: ");
            choice = ui.inputInt();

            switch (choice) {
                case 1:
                    showMenu(); // Display menu
                    break;
                case 2:
                    registerMenu(); // Register menu
                    break;
                case 3:
                    findMenu(); // Find menu
                    break;
                case 4:
                    updateMenu(); // Update menu
                    break;
                case 5:
                    deleteMenu(); // Delete menu
                    break;
                case 6:
                    addMenu(); // Add entity to entity
                    break;
                case 7:
                    removeMenu(); // Remove entity from entity
                    break;
                case 8:
                    statisticMenu(); // Statistic mennu
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Not a valid input");
                    break;
            }
        }
    }
    
    /**
     * Display students, teachers, courses, educations or go back to previous menu   
     */
    public void showMenu() {
        boolean loop1 = true;
        while (loop1) {
            System.out.println("------------------------");
            System.out.println("Show\n");
            prinSubMenu2(); // // Print menu with choices
            switch (choice) {
                case 1:
                    System.out.println("Students");
                    s.displayall();
                    break;
                case 2:
                    System.out.println("Teachers");
                    t.displayall();
                    break;
                case 3:
                    System.out.println("Courses");
                    c.displayall();
                    break;
                case 4:
                    System.out.println("Educations");
                    e.displayall();
                    break;
                case 5:
                    System.out.println("\nEducations\n");
                    e.displayall();
                    System.out.println("\nCourses\n");
                    c.displayall();
                    System.out.println("\nTeachers\n");
                    t.displayall();
                    System.out.println("\nStudents\n");
                    s.displayall();
                    break;
                case 0:
                    loop1 = false;
                    break;
                default:
                    System.out.println("Not a valid input");
                    break;
            }
        }
    }
    
    /**
     * Register new students, teachers, courses, educations or go back to previous menu   
     */
    public void registerMenu() {
        boolean loop2 = true;
        while (loop2) {
            System.out.println("------------------------");
            System.out.println("Register new\n");
            prinSubMenu();// Print menu with choices
            switch (choice) {
                case 1:
                    s.create(new Student(ui.getFirstName(), ui.getLastName(), ui.getAge()));
                    break;
                case 2:
                    t.create(new Teacher(ui.getFirstName(), ui.getLastName(), ui.getAge()));
                    break;
                case 3:
                    c.create(new Course(ui.getName(), ui.getDescription(), ui.getCoursePoints()));
                    break;
                case 4:
                    e.create(new Education(ui.getName(), ui.getDescription(), ui.getDuration(), ui.getMaxAmount()));
                    break;

                case 0:
                    loop2 = false;
                    break;
                default:
                    System.out.println("Not a valid input");
                    break;
            }

        }
    }
    /**
     * Find students, teachers, courses, educations or go back to previous menu   
     */
    public void findMenu() {
        boolean loop3 = true;
        while (loop3) {
            System.out.println("------------------------");
            System.out.println("Find\n");
            prinSubMenu();// Print menu with choices
            switch (choice) {
                case 1:
                    findStudent();
                    break;
                case 2:
                    findTeacher();
                    break;
                case 3:
                    findCourse();
                    break;
                case 4:
                    findEducation();
                    break;
                case 0:
                    loop3 = false;
                    break;
                default:
                    System.out.println("Not a valid input");
                    break;

            }

        }
    }
    /**
     * Update students, teachers, courses, educations or go back to previous menu 
     */
    public void updateMenu() {
        boolean loop4 = true;
        while (loop4) {
            System.out.println("------------------------");
            System.out.println("Update\n");
            prinSubMenu(); // Print menu with choices
            switch (choice) {
                case 1:
                    Query q1 = em.createQuery("SELECT s FROM Student s");
                    List<Student> students = q1.getResultList();
                    /**
                     * Do next actions if we have students else write in output "No students"
                     */
                    if (!students.isEmpty()) { 
                        s.displayall();
                        System.out.println("What do you want to do?");
                        System.out.println("1. Update all \n2. Update Firstname \n3. Update Lastname \n4. Update Age \n0.Exit");
                        int choice2 = ui.inputInt();
                        Student student = (Student) s.findById(ui.getId());
                        boolean loop = true;
                        while (loop) {
                            switch (choice2) {
                                case 1:
                                    student.setFirstName(ui.getFirstName());
                                    student.setLastName(ui.getLastName());
                                    student.setAge(ui.getAge());
                                    s.update(student);
                                    loop = false;
                                    break;
                                case 2:
                                    student.setFirstName(ui.getFirstName());
                                    s.update(student);
                                    loop = false;
                                    break;
                                case 3:
                                    student.setLastName(ui.getLastName());
                                    s.update(student);
                                    loop = false;
                                    break;
                                case 4:
                                    student.setAge(ui.getAge());
                                    s.update(student);
                                    loop = false;
                                    break;
                                case 0:
                                    loop = false;
                                    break;
                            }
                        }
                    } else { // If we does not have students
                        System.out.println("No students");
                    }

                    break;
                case 2:
                    Query q2 = em.createQuery("SELECT t FROM Teacher t");
                    List<Teacher> teachers = q2.getResultList();
                    /**
                     * Do next actions if we have teachers else write in output "No teachers"
                     */
                    if (!teachers.isEmpty()) { 
                        t.displayall();
                        System.out.println("What do you want to do?");
                        System.out.println("1. Update all \n2. Update Firstname \n3. Update Lastname \n4. Update Age\n0.Exit");
                        int choice3 = ui.inputInt();
                        Teacher teacher = (Teacher) t.findById(ui.getId());
                        boolean loop2 = true;
                        while (loop2) {
                            switch (choice3) {
                                case 1:
                                    teacher.setFirstName(ui.getFirstName());
                                    teacher.setLastName(ui.getLastName());
                                    teacher.setAge(ui.getAge());
                                    t.update(teacher);
                                    loop2 = false;
                                    break;
                                case 2:
                                    teacher.setFirstName(ui.getFirstName());
                                    t.update(teacher);
                                    loop2 = false;
                                    break;
                                case 3:
                                    teacher.setLastName(ui.getLastName());
                                    t.update(teacher);
                                    loop2 = false;
                                    break;
                                case 4:
                                    teacher.setAge(ui.getAge());
                                    t.update(teacher);
                                    loop2 = false;
                                    break;
                                case 0:
                                    loop2 = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("No teachers");
                    }

                    break;
                case 3:
                    Query q3 = em.createQuery("SELECT c FROM Course c");
                    List<Course> courses = q3.getResultList();
                    /**
                     * Do next actions if we have courses else write in output "No courses"
                     */
                    if (!courses.isEmpty()) {
                        c.displayall();
                        System.out.println("What do you want to do?");
                        System.out.println("1. Update all \n2. Update Name \n3. Update Description \n4. Update Points\n0.Exit");
                        int choice5 = ui.inputInt();
                        Course course = (Course) c.findById(ui.getId());
                        boolean loop5 = true;
                        while (loop5) {
                            switch (choice5) {
                                case 1:
                                    course.setName(ui.getCourseName());
                                    course.setDescription(ui.getDescription());
                                    course.setPoints(ui.getPoints());
                                    c.update(course);
                                    loop5 = false;
                                    break;
                                case 2:
                                    course.setName(ui.getCourseName());
                                    c.update(course);
                                    loop5 = false;
                                    break;
                                case 3:
                                    course.setDescription(ui.getDescription());
                                    c.update(course);
                                    loop5 = false;

                                    break;
                                case 4:
                                    course.setPoints(ui.getPoints());
                                    c.update(course);
                                    loop5 = false;
                                    break;
                                case 0:
                                    loop5 = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("No courses");
                    }
                    break;

                case 4:
                    Query q4 = em.createQuery("SELECT e from Education e");
                    List<Education> educations = (List<Education>) q4.getResultList();
                    /**
                     * Do next actions if we have educations else write in output "No educations"
                     */
                    if (!educations.isEmpty()) {
                        e.displayall();
                        System.out.println("What do you want to do?");
                        System.out.println("1. Update all \n2. Update Name \n3. Update Description \n4. Update Duration\n5. Update Max amount of students\n0.Exit");
                        int choice4 = ui.inputInt();
                        Education education = (Education) e.findById(ui.getId());
                        boolean loop3 = true;
                        while (loop3) {
                            switch (choice4) {
                                case 1:
                                    education.setName(ui.getCourseName());
                                    education.setDescription(ui.getDescription());
                                    education.setDuration(ui.getDuration());
                                    education.setMaxAntalStudenter(ui.getMaxAmount());
                                    e.update(education);
                                    loop3 = false;
                                    break;
                                case 2:
                                    education.setName(ui.getCourseName());
                                    e.update(education);
                                    loop3 = false;
                                    break;
                                case 3:
                                    education.setDescription(ui.getDescription());
                                    e.update(education);
                                    loop3 = false;
                                    break;
                                case 4:
                                    education.setDuration(ui.getDuration());
                                    e.update(education);
                                    loop3 = false;
                                    break;
                                case 5:
                                    education.setMaxAntalStudenter(ui.getMaxAmount());
                                    e.update(education);
                                    loop3 = false;
                                    break;
                                case 0:
                                    loop3 = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("No educations");
                    }
                    break;
                case 0:
                    loop4 = false;
                    break;
                default:
                    System.out.println("Not a valid input");
                    break;
            }
        }
    }
    /**
     * Delete students, teachers, courses, educations or go back to previous menu 
     */
    public void deleteMenu() {
        boolean loop5 = true;
        while (loop5) {
            System.out.println("------------------------");
            System.out.println("Delete\n");
            prinSubMenu();// Print menu with choices
            switch (choice) {
                case 1:
                    s.displayall();
                    Query q1 = em.createQuery("SELECT s from Student s");
                    List<Student> students = (List<Student>) q1.getResultList();
                    if (!students.isEmpty()) {

                        System.out.print("Enter student's id: ");
                        s.delete(ui.inputLong());
                    } else {
                    }
                    break;
                case 2:
                    t.displayall();
                    Query q2 = em.createQuery("SELECT t from Teacher t");
                    List<Teacher> teachers = (List<Teacher>) q2.getResultList();
                    if (!teachers.isEmpty()) {
                        System.out.print("Enter teacher's id: ");
                        t.delete(ui.inputLong());
                    } else {
                    }
                    break;
                case 3:
                    c.displayall();
                    Query q3 = em.createQuery("SELECT c from Course c");
                    List<Course> courses = (List<Course>) q3.getResultList();
                    if (!courses.isEmpty()) {
                        System.out.print("Enter course id: ");
                        c.delete(ui.inputLong());
                    } else {
                    }
                    break;
                case 4:
                    e.displayall();
                    Query q4 = em.createQuery("SELECT e from Education e");
                    List<Education> educations = (List<Education>) q4.getResultList();
                    if (!educations.isEmpty()) {
                        System.out.print("Enter education id: ");
                        e.delete(ui.inputLong());
                    } else {
                    }
                    break;
                case 0:
                    loop5 = false;
                    break;
                default:
                    System.out.println("Not a valid input");
                    break;
            }
        }

    }
    /**
     * Add course/student to education, teacher to course 
     */
    public void addMenu() {
        boolean loop6 = true;
        while (loop6) {
            System.out.println("------------------------");
            System.out.println("Add\n");
            printAddMenu(); // Print menu with choices
            switch (choice) {
                case 1:
                    if(e.checkEducationsForStudents()){
                        System.out.println("Which education?");
                        Long education = ui.getId();
                        if(e.checkAvailableStudents(education)){
                            System.out.println("Which student?");
                            Long student = ui.getId();
                            e.addStudentToEducation(education, student);
                        }
                    }
                    break;
                case 2:
                    if(e.cheackEducationsForCourse()){
                        System.out.println("Which education?");
                        Long education = ui.getId();
                        if(e.checkAvailableCourses(education)){
                            System.out.println("Which course?");
                            Long course = ui.getId();
                            e.addToCoursetoEducation(education, course);
                        }
                    }
                    break;
                case 3:
                    if(c.availableCourses()){
                        System.out.println("Which Course?");
                        Long course = ui.getId();
                        if(c.checkAvailableTeachers(course)){
                            System.out.println("Which teacher?");
                            Long teacher = ui.getId();
                            e.addToCoursetoEducation(course, teacher);
                        }
                    }
                    break;
                case 0:
                    loop6 = false;
                    break;
                default:
                    System.out.println("Not a valid input");
                    break;

            }

        }
    }
    /**
     * Remove course/student from education, teacher from course 
     */
    public void removeMenu() {
        boolean loop7 = true;
        while (loop7) {
            System.out.println("------------------------");
            System.out.println("Remove\n");
            printRemoveMenu(); // Print menu with choices
            switch (choice) {
                case 1:
                   if(e.checkEducationsWithStudents()){
                        System.out.println("Which education?");
                        Long education = ui.getId();
                        if(e.checkEducationStudents(education)){
                            System.out.println("Which student?");
                            Long student = ui.getId();
                            e.removeStudentFromEducation(education, student);
                        }
                    }
                    break;
                case 2:
                  if(e.checkEducationWithCourses()){
                        System.out.println("Which education?");
                        Long education = ui.getId();
                        if(e.checkEducationCourses(education)){
                            System.out.println("Which course?");
                            Long course = ui.getId();
                            e.removeCourseFromEducation(education, course);
                        }
                    }
                    break;
                case 3:
                    if(c.checkCoursesWithTeachers()){
                        System.out.println("Which course?");
                        Long course = ui.getId();
                        if(c.checkCoursesTeachers(course)){
                            System.out.println("Which course?");
                            Long teacher = ui.getId();
                            c.removeTeacherFromCourse(course, teacher);
                        }
                    }
                    break;
                case 0:
                    loop7 = false;
                    break;
                default:
                    System.out.println("Not a valid input");
                    break;

            }

        }
    }
    /**
     * Print menu with choices for removeMenu
     */
    public void printRemoveMenu() {
        System.out.println("1. Student from education");
        System.out.println("2. Course from education");
        System.out.println("3. Teacher from course");
        System.out.println("0. Exit");
        System.out.println("------------------------");
        System.out.print("Enter: ");
        choice = ui.inputInt();
    }
    /**
     * Print menu with choices for addMenu
     */
    public void printAddMenu() {
        System.out.println("1. Student to education");
        System.out.println("2. Course to education");
        System.out.println("3. Teacher to course");
        System.out.println("0. Exit");
        System.out.println("------------------------");
        System.out.print("Enter: ");
        choice = ui.inputInt();
    }
    /**
     * Print menu with choices for findMenu,updateMenu,deleteMenu
     */
    public void prinSubMenu() {
        System.out.println("1. Students");
        System.out.println("2. Teachers");
        System.out.println("3. Courses");
        System.out.println("4. Educations");
        System.out.println("0. Exit");
        System.out.println("------------------------");
        System.out.print("Enter: ");
        choice = ui.inputInt();
    }
    /**
     * Print menu with choices for showMenu
     */
    public void prinSubMenu2() {
        System.out.println("1. Students");
        System.out.println("2. Teachers");
        System.out.println("3. Courses");
        System.out.println("4. Educations");
        System.out.println("5. All");
        System.out.println("0. Exit");
        System.out.println("------------------------");
        System.out.print("Enter: ");
        choice = ui.inputInt();
    }
    /**
     * Find student
     */
    public void findStudent() {
        boolean loopS = true;
        while (loopS) {
            System.out.println("Find student\n");
            System.out.println("1. Find student by id");
            System.out.println("2. Find student by name");
            System.out.println("0. Exit");
            System.out.println("------------------------");
            System.out.print("Enter: ");
            choice = ui.inputInt();

            switch (choice) {
                case 1:
                    s.findById(ui.getId());

                    break;
                case 2:
                    s.findByName(ui.getFirstName());
                    break;
                case 0:
                    loopS = false;
                    break;
                default:
                    System.out.println("Not a valid input");
                    break;

            }
        }
    }
    /**
     * Find teacher
     */
    public void findTeacher() {
        boolean loopT = true;
        while (loopT) {
            System.out.println("Find teacher\n");
            System.out.println("1. Find teacher by id");
            System.out.println("2. Find teacher by name");
            System.out.println("0. Exit");
            System.out.println("------------------------");
            System.out.print("Enter: ");
            choice = ui.inputInt();

            switch (choice) {
                case 1:
                    t.findById(ui.getId());

                    break;
                case 2:
                    t.findByName(ui.getFirstName());

                    break;
                case 0:
                    loopT = false;
                    break;
                default:
                    System.out.println("Not a valid input");
                    break;
            }
        }

    }
    /**
     * Find course
     */
    public void findCourse() {
        boolean loopC = true;
        while (loopC) {
            System.out.println("Find course\n");
            System.out.println("1. Find course by id");
            System.out.println("2. Find course by name");
            System.out.println("0. Exit");
            System.out.println("------------------------");
            System.out.print("Enter: ");
            choice = ui.inputInt();

            switch (choice) {
                case 1:
                    c.findById(ui.getId());

                    break;
                case 2:
                    c.findByName(ui.getName());

                    break;
                case 0:
                    loopC = false;
                    break;
                default:
                    System.out.println("Not a valid input");
                    break;
            }
        }

    }
    /**
     * Find education
     */
    public void findEducation() {
        boolean loopE = true;
        while (loopE) {
            System.out.println("Find education\n");
            System.out.println("1. Find education by id");
            System.out.println("2. Find education by name");
            System.out.println("0. Exit");
            System.out.println("------------------------");
            System.out.print("Enter: ");
            choice = ui.inputInt();

            switch (choice) {
                case 1:
                    e.findById(ui.getId());

                    break;
                case 2:
                    e.findByName(ui.getName());

                    break;
                case 0:
                    loopE = false;
                    break;
                default:
                    System.out.println("Not a valid input");
                    break;
            }
        }
    }
    /**
     * Show amount of students,teachers,courses,all / full educations and with places
     */
    public void statisticMenu() {
        boolean loop = true;
        while (loop) {
            System.out.println("------------------------");
            System.out.println("Statisctic\n");
            printStatisticMenu(); // Print menu with choices
            switch (choice) {
                case 1:
                    s.showStudentAmount();
                    break;
                case 2:
                    t.showTeacherAmont();
                    break;
                case 3:
                    c.showCoursesAmont();
                    break;
                case 5:
                    e.showEducationsFullAndWithPlaces();
                    break;
                case 4:
                    e.CountOfAll();
                    break;
                case 0:
                    loop = false;
                    break;
                default:
                    System.out.println("Not a valid input");
                    break;

            }

        }
    }
    /**
     * Print menu with choices for statisticMenu
     */
    public void printStatisticMenu() {
        System.out.println("1. Show student amount");
        System.out.println("2. Show teacher amount");
        System.out.println("3. Show course amount");

        System.out.println("4. Show all amount");
        System.out.println("5. Show full educations and with places");
        System.out.println("0. Exit");
        System.out.println("------------------------");
        System.out.print("Enter: ");
        choice = ui.inputInt();
    }
}
