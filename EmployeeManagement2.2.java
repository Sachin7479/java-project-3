import java.io.*;
import java.util.Scanner;

class Employee implements Serializable {
    private String name;
    private int id;
    private String designation;
    private double salary;
    
    public Employee(String name, int id, String designation, double salary) {
        this.name = name;
        this.id = id;
        this.designation = designation;
        this.salary = salary;
    }
    
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Designation: " + designation + ", Salary: $" + salary;
    }
}

public class EmployeeManagement {
    private static final String FILENAME = "employees.dat";
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("Part c: Menu-Based Employee Management System");
        
        while (true) {
            System.out.println("\n--- Employee Management System ---");
            System.out.println("1. Add an Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Exit the Application");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    displayEmployees();
                    break;
                case 3:
                    System.out.println("Exiting application. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void addEmployee() {
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter designation: ");
        String designation = scanner.nextLine();
        
        System.out.print("Enter salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();
        
        Employee employee = new Employee(name, id, designation, salary);
        
        try (FileOutputStream fos = new FileOutputStream(FILENAME, true);
             ObjectOutputStream oos = fos.getChannel().position() == 0 ? 
                 new ObjectOutputStream(fos) : new AppendableObjectOutputStream(fos)) {
            oos.writeObject(employee);
            System.out.println("Employee added successfully!");
        } catch (IOException e) {
            System.out.println("Error saving employee: " + e.getMessage());
        }
    }
    
    private static void displayEmployees() {
        File file = new File(FILENAME);
        if (!file.exists() || file.length() == 0) {
            System.out.println("No employees found.");
            return;
        }
        
        System.out.println("\n--- Employee Records ---");
        try (FileInputStream fis = new FileInputStream(FILENAME);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (true) {
                try {
                    Employee employee = (Employee) ois.readObject();
                    System.out.println(employee);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading employees: " + e.getMessage());
        }
    }
}

class AppendableObjectOutputStream extends ObjectOutputStream {
    public AppendableObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }
    
    protected void writeStreamHeader() throws IOException {
        reset();
    }
}
