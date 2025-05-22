package org.example.entity;

public class Student extends User{

    public Student(int id, String name) {
        super(id, name);
        setMaxBooksAllowed(3);
    }




}
