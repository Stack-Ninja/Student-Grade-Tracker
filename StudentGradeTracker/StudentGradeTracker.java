import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeTracker {
    private static final ArrayList<Student> students = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void addStudent() {
        System.out.print("\nEnter student name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Name cannot be empty."); return; }
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                System.out.println("A student with this name already exists."); return;
            }
        }
        double grade = getValidGrade();
        students.add(new Student(name, grade));
        System.out.println("Student added successfully!");
    }

    public static double getValidGrade() {
        while (true) {
            System.out.print("Enter grade (0-100): ");
            if (scanner.hasNextDouble()) {
                double grade = scanner.nextDouble(); scanner.nextLine();
                if (grade >= 0 && grade <= 100) return grade;
                System.out.println("Grade must be between 0 and 100.");
            } else { System.out.println("Please enter a valid number."); scanner.nextLine(); }
        }
    }

    public static void displayStudents() {
        if (students.isEmpty()) { System.out.println("\nNo students available."); return; }
        System.out.println("\n================================================");
        System.out.printf("%-5s %-20s %-12s %-10s%n", "No.", "Name", "Score", "Grade");
        System.out.println("================================================");
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            System.out.printf("%-5d %-20s %-12.2f %-10s%n", i + 1, student.getName(), student.getGrade(), student.getGradeLetter());
        }
        System.out.println("================================================");
    }

    public static double calculateAverage() {
        if (students.isEmpty()) return 0;
        double total = 0;
        for (Student student : students) total += student.getGrade();
        return total / students.size();
    }

    public static Student findHighest() {
        if (students.isEmpty()) return null;
        Student highest = students.get(0);
        for (Student student : students) if (student.getGrade() > highest.getGrade()) highest = student;
        return highest;
    }

    public static Student findLowest() {
        if (students.isEmpty()) return null;
        Student lowest = students.get(0);
        for (Student student : students) if (student.getGrade() < lowest.getGrade()) lowest = student;
        return lowest;
    }

    public static void calculateStatistics() {
        if (students.isEmpty()) { System.out.println("\nNo student data available."); return; }
        Student highest = findHighest(), lowest = findLowest();
        System.out.println("\n========== STATISTICS ==========");
        System.out.printf("Average Grade : %.2f%n", calculateAverage());
        System.out.printf("Highest Grade : %.2f (%s) - Grade %s%n", highest.getGrade(), highest.getName(), highest.getGradeLetter());
        System.out.printf("Lowest Grade  : %.2f (%s) - Grade %s%n", lowest.getGrade(), lowest.getName(), lowest.getGradeLetter());
        System.out.println("================================");
    }

    public static void searchStudent() {
        if (students.isEmpty()) { System.out.println("\nNo students available."); return; }
        System.out.print("\nEnter student name to search: ");
        String searchName = scanner.nextLine().trim();
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(searchName)) {
                System.out.println("\n-------- STUDENT FOUND --------");
                System.out.println("Name  : " + student.getName());
                System.out.printf("Score : %.2f%n", student.getGrade());
                System.out.println("Grade : " + student.getGradeLetter());
                System.out.println("-------------------------------"); return;
            }
        }
        System.out.println("Student not found.");
    }

    public static void updateStudent() {
        if (students.isEmpty()) { System.out.println("\nNo students available."); return; }
        System.out.print("\nEnter student name to update: ");
        String name = scanner.nextLine().trim();
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                System.out.println("\nStudent found:");
                System.out.println("Name  : " + student.getName());
                System.out.printf("Score : %.2f%n", student.getGrade());
                System.out.println("Grade : " + student.getGradeLetter());
                System.out.println("\nEnter new grade:");
                student.setGrade(getValidGrade());
                System.out.println("Student grade updated successfully!"); return;
            }
        }
        System.out.println("Student not found.");
    }

    public static void deleteStudent() {
        if (students.isEmpty()) { System.out.println("\nNo students available."); return; }
        System.out.print("\nEnter student name to delete: ");
        String name = scanner.nextLine().trim();
        Student target = null;
        for (Student student : students) if (student.getName().equalsIgnoreCase(name)) { target = student; break; }
        if (target == null) { System.out.println("Student not found."); return; }
        System.out.println("\nStudent found:");
        System.out.println("Name  : " + target.getName());
        System.out.printf("Score : %.2f%n", target.getGrade());
        System.out.print("\nAre you sure you want to delete this student? (Y/N): ");
        if (scanner.nextLine().equalsIgnoreCase("Y")) { students.remove(target); System.out.println("Student deleted successfully!"); }
        else System.out.println("Delete operation cancelled.");
    }

    public static void displaySummaryReport() {
        if (students.isEmpty()) { System.out.println("\nNo student data available."); return; }
        Student highest = findHighest(), lowest = findLowest();
        System.out.println("\n==============================================");
        System.out.println("           STUDENT SUMMARY REPORT");
        System.out.println("==============================================");
        System.out.println("Total Students : " + students.size());
        System.out.printf("Average Grade  : %.2f%n", calculateAverage());
        System.out.printf("Highest Grade  : %.2f (%s)%n", highest.getGrade(), highest.getName());
        System.out.printf("Lowest Grade   : %.2f (%s)%n", lowest.getGrade(), lowest.getName());
        System.out.println("==============================================");
        displayStudents();
    }

    public static void displayMenu() {
        System.out.println("\n=================================");
        System.out.println("       STUDENT GRADE TRACKER");
        System.out.println("=================================");
        System.out.println("1. Add Student");
        System.out.println("2. Display All Students");
        System.out.println("3. Calculate Statistics");
        System.out.println("4. Display Summary Report");
        System.out.println("5. Search Student");
        System.out.println("6. Update Student");
        System.out.println("7. Delete Student");
        System.out.println("8. Exit");
        System.out.println("=================================");
    }

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            System.out.print("Enter your choice: ");
            if (!scanner.hasNextInt()) { System.out.println("Please enter a valid number."); scanner.nextLine(); continue; }
            int choice = scanner.nextInt(); scanner.nextLine();
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> displayStudents();
                case 3 -> calculateStatistics();
                case 4 -> displaySummaryReport();
                case 5 -> searchStudent();
                case 6 -> updateStudent();
                case 7 -> deleteStudent();
                case 8 -> { System.out.println("\nThank you for using Student Grade Tracker!"); scanner.close(); return; }
                default -> System.out.println("Invalid choice. Please select 1-8.");
            }
        }
    }
}
