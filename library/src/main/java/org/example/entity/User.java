package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private int maxBooksAllowed;
    private List<Book> bookList;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        bookList = new ArrayList<>();
    }

    public User(int id, String name, int maxBooksAllowed) {
        this.id = id;
        this.name = name;
        this.maxBooksAllowed = maxBooksAllowed;
        bookList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxBooksAllowed() {
        return maxBooksAllowed;
    }

    public void setMaxBooksAllowed(int maxBooksAllowed) {
        this.maxBooksAllowed = maxBooksAllowed;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxBooksAllowed=" + maxBooksAllowed +
                ", numberOfIssuedBooks=" + getBookList().size() +
                '}';
    }
}
