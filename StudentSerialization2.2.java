import java.io.*;

class Student implements Serializable {
    private int studentID;
    private String name;
    private String grade;
    
    public Student(int studentID, String name, String grade) {
        this.studentID = studentID;
        this.name = name;
        this.grade = grade;
    }
    
    public void displayInfo() {
        System.out.println("Student ID: " + studentID);
        System.out.println("Name: " + name);
        System.out.println("Grade: " + grade);
    }
}

public class StudentSerialization {
    public static void main(String[] args) {
        String filename = "student.ser";
        
        System.out.println("Part b: Serialization and Deserialization of a Student Object");
        
        Student student = new Student(101, "John Doe", "A");
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(student);
            System.out.println("\nStudent object serialized successfully.");
        } catch (IOException e) {
            System.out.println("Error during serialization: " + e.getMessage());
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Student deserializedStudent = (Student) ois.readObject();
            System.out.println("\nStudent object deserialized successfully.");
            System.out.println("\nDeserialized Student Data:");
            deserializedStudent.displayInfo();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error during deserialization: " + e.getMessage());
        }
    }
}
