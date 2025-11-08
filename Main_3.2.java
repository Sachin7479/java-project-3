package com.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class Course {
    private String courseName;
    
    public Course(String courseName) {
        this.courseName = courseName;
    }
    
    public String getCourseName() {
        return courseName;
    }
}

class Student {
    private String name;
    private Course course;
    
    public Student(String name, Course course) {
        this.name = name;
        this.course = course;
    }
    
    public void displayInfo() {
        System.out.println("Student: " + name + ", Course: " + course.getCourseName());
    }
}

@Configuration
class AppConfig {
    @Bean
    public Course course() {
        return new Course("Spring Framework");
    }
    
    @Bean
    public Student student() {
        return new Student("John Doe", course());
    }
}

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Student student = context.getBean(Student.class);
        student.displayInfo();
        context.close();
    }
}
