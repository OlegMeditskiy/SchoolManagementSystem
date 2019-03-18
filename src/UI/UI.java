package UI;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {

    static Scanner input = new Scanner(System.in);

    public String getFirstName() {
        System.out.println("Enter first name: ");
        String firstname = inputString();
        return firstname;

    }

    public String getLastName() {

        System.out.println("Enter last name: ");
        String lastname = inputString();

        return lastname;

    }

    public int getAge() {

        System.out.println("Enter age: ");
        int age = inputInt();
        return age;
    }

    public String getCourseName() {
        System.out.println("Enter course name: ");
        String courseName = inputString();
        return courseName;
    }

    public double getPoints() {

        System.out.println("Enter points: ");
        double points = inputDouble();

        return points;
    }

    public String getDescription() {
        System.out.println("Enter description: ");
        String description = inputString();
        return description;
    }

    public Long getId() {

        System.out.println("Enter id: ");
        Long id = inputLong();
        return id;

    }

    public int getMaxAmount() {
        System.out.println("Max amount of students: ");
        int max = inputInt();

        return max;
    }

    public String getEducationName() {
        System.out.println("Enter education name: ");
        String courseName = inputString();
        return courseName;
    }

    public double getDuration() {

        System.out.println("Enter duration of education: ");
        double points = inputDouble();

        return points;
    }

    public int inputInt() {
        try {
            int choiceInt = input.nextInt();
            input.nextLine();
            return choiceInt;
        } catch (InputMismatchException ex) {
            System.out.println("Only numbers");
            input.next();
            return inputInt();
        }
    }

    public String inputString() {

        String choiceString = input.nextLine();
        return choiceString;

    }

    public Long inputLong() {
        try {
            Long choiceLong = input.nextLong();
            input.nextLine();
            return choiceLong;
        } catch (InputMismatchException ex) {
            System.out.println("Only numbers");
            input.next();
            return inputLong();
        }
    }

    public double inputDouble() {
        try {
            double choiceDouble = input.nextDouble();
            input.nextLine();
            return choiceDouble;
        } catch (InputMismatchException ex) {
            System.out.println("Only numbers");
            input.next();
            return inputDouble();
        }
    }

    public String getName() {
        System.out.println("Enter name: ");
        String firstname = inputString();
        return firstname;
    }

    public double getCoursePoints() {

        System.out.println("Enter Course points: ");
        double coursePoints = inputDouble();
        return coursePoints;
    }

}
