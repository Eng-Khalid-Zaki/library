package org.example.entity;

import java.util.*;

public class Library {
    private int id;
    private String name;
    List<Book> bookList;

    public Library() {
        this.bookList = new ArrayList<>();
    }

    public Library(List<Book> bookList) {
        this.bookList = bookList;
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

    public List<Book> getBookList() {
        return this.bookList;
    }

    @Override
    public String toString() {
        return "Library{" +
                "bookList=" + bookList +
                '}';
    }
}
