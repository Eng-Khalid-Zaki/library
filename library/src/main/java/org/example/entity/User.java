package org.example.entity;

import org.example.exception.BookNotAvailableException;
import org.example.exception.DuplicatedBookIdException;
import org.w3c.dom.ls.LSOutput;

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

    void addBook(Book book) throws DuplicatedBookIdException {
        if(bookList.contains(book)) {
            throw new DuplicatedBookIdException("This book is in your list already!");
        }

        this.bookList.add(book);
    }

    void removeBook(Book book) throws BookNotAvailableException {
        if(bookList.contains(book)) {
            bookList.remove(book);
        }else {
            throw new BookNotAvailableException("This book is not issued by this user!");
        }
    }

    public int getNumberOfIssuedBooks() {
        return (int) bookList.stream()
                .count();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxBooksAllowed=" + maxBooksAllowed +
                ", numberOfIssuedBooks=" + getBookList() +
                '}';
    }
}
