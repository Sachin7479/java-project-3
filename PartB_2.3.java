import java.util.*;
import java.util.stream.*;

class Student {
    String name;
    double marks;
    
    Student(String name, double marks) {
        this.name = name;
        this.marks = marks;
    }
    
    @Override
    public String toString() {
        return "Student{name='" + name + "', marks=" + marks + "}";
    }
}

public class PartB {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Alice", 85.5));
        students.add(new Student("Bob", 72.0));
        students.add(new Student("Charlie", 90.5));
        students.add(new Student("David", 65.0));
        students.add(new Student("Eve", 78.5));
        students.add(new Student("Frank", 88.0));
        
        System.out.println("Original List:");
        students.forEach(System.out::println);
        
        System.out.println("\nStudents with marks > 75% (Sorted by Marks):");
        students.stream()
                .filter(s -> s.marks > 75)
                .sorted((s1, s2) -> Double.compare(s2.marks, s1.marks))
                .forEach(s -> System.out.println(s.name + ": " + s.marks));
    }
}
