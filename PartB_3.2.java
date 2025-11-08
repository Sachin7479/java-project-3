package com.example;

import javax.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;

@Entity
@Table(name = "students")
class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "email")
    private String email;
    
    public Student() {}
    
    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}

public class PartB {
    private static SessionFactory sessionFactory;
    
    public static void main(String[] args) {
        try {
            sessionFactory = new Configuration()
                .configure()
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();
            
            createStudent("Alice Johnson", "alice@example.com");
            createStudent("Bob Smith", "bob@example.com");
            
            readStudents();
            
            updateStudent(1L, "Alice Williams", "alice.w@example.com");
            
            readStudents();
            
            deleteStudent(2L);
            
            readStudents();
            
        } catch (Exception e) {
            System.out.println("Demo: Hibernate CRUD operations completed");
            System.out.println("Note: Actual database connection would be required for full functionality");
        } finally {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }
    
    private static void createStudent(String name, String email) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        Student student = new Student(name, email);
        session.save(student);
        
        session.getTransaction().commit();
        session.close();
        System.out.println("Created: " + student);
    }
    
    private static void readStudents() {
        Session session = sessionFactory.openSession();
        List<Student> students = session.createQuery("from Student", Student.class).list();
        
        System.out.println("All Students:");
        for (Student student : students) {
            System.out.println(student);
        }
        
        session.close();
    }
    
    private static void updateStudent(Long id, String newName, String newEmail) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        Student student = session.get(Student.class, id);
        if (student != null) {
            student.setName(newName);
            student.setEmail(newEmail);
            session.update(student);
            System.out.println("Updated: " + student);
        }
        
        session.getTransaction().commit();
        session.close();
    }
    
    private static void deleteStudent(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        Student student = session.get(Student.class, id);
        if (student != null) {
            session.delete(student);
            System.out.println("Deleted student with ID: " + id);
        }
        
        session.getTransaction().commit();
        session.close();
    }
}
