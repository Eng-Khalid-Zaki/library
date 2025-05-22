package org.example.entity;

public class Teacher extends User{

    public Teacher(int id, String name) {
        super(id, name);
        setMaxBooksAllowed(10);
    }
}
