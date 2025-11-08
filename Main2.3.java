import java.util.*;

class Employee {
    String name;
    int age;
    double salary;
    
    Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
    
    @Override
    public String toString() {
        return "Employee{name='" + name + "', age=" + age + ", salary=" + salary + "}";
    }
}

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Alice", 30, 50000));
        employees.add(new Employee("Bob", 25, 45000));
        employees.add(new Employee("Charlie", 35, 60000));
        employees.add(new Employee("David", 28, 52000));
        
        System.out.println("Original List:");
        employees.forEach(System.out::println);
        
        System.out.println("\nSorted by Name (Alphabetically):");
        employees.sort((e1, e2) -> e1.name.compareTo(e2.name));
        employees.forEach(System.out::println);
        
        System.out.println("\nSorted by Age (Ascending):");
        employees.sort((e1, e2) -> Integer.compare(e1.age, e2.age));
        employees.forEach(System.out::println);
        
        System.out.println("\nSorted by Salary (Descending):");
        employees.sort((e1, e2) -> Double.compare(e2.salary, e1.salary));
        employees.forEach(System.out::println);
    }
}
