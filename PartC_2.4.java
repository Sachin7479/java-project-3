import java.sql.*;
import java.util.Scanner;

class Student {
    private int studentID;
    private String name;
    private String department;
    private double marks;
    
    public Student(int studentID, String name, String department, double marks) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }
    
    public int getStudentID() { return studentID; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getMarks() { return marks; }
}

class StudentController {
    private static final String URL = "jdbc:mysql://localhost:3306/college";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    
    public void addStudent(Student student) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "INSERT INTO Student VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, student.getStudentID());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getDepartment());
            pstmt.setDouble(4, student.getMarks());
            pstmt.executeUpdate();
            conn.close();
            System.out.println("Student added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void viewAllStudents() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Student");
            
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("StudentID") + 
                                 ", Name: " + rs.getString("Name") + 
                                 ", Department: " + rs.getString("Department") + 
                                 ", Marks: " + rs.getDouble("Marks"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateStudent(int id, Student student) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "UPDATE Student SET Name=?, Department=?, Marks=? WHERE StudentID=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getDepartment());
            pstmt.setDouble(3, student.getMarks());
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            conn.close();
            System.out.println("Student updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteStudent(int id) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "DELETE FROM Student WHERE StudentID=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            conn.close();
            System.out.println("Student deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class PartC {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentController controller = new StudentController();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            while (true) {
                System.out.println("\n1. Add Student");
                System.out.println("2. View All Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:
                        System.out.print("Enter Student ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Department: ");
                        String dept = scanner.nextLine();
                        System.out.print("Enter Marks: ");
                        double marks = scanner.nextDouble();
                        controller.addStudent(new Student(id, name, dept, marks));
                        break;
                    case 2:
                        controller.viewAllStudents();
                        break;
                    case 3:
                        System.out.print("Enter Student ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter new Name: ");
                        String newName = scanner.nextLine();
                        System.out.print("Enter new Department: ");
                        String newDept = scanner.nextLine();
                        System.out.print("Enter new Marks: ");
                        double newMarks = scanner.nextDouble();
                        controller.updateStudent(updateId, new Student(updateId, newName, newDept, newMarks));
                        break;
                    case 4:
                        System.out.print("Enter Student ID to delete: ");
                        int deleteId = scanner.nextInt();
                        controller.deleteStudent(deleteId);
                        break;
                    case 5:
                        scanner.close();
                        return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
