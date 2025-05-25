package org.example.entity;

import org.example.exception.BookNotAvailableException;
import org.example.exception.DuplicatedBookIdException;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Iterator;
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
        for (Book existedBook : this.bookList) {
            if (existedBook.getId() == book.getId()) {
                throw new DuplicatedBookIdException("This book is in your list already!");
            }
        }

        this.bookList.add(book);
    }

    void removeBook(Book book) throws BookNotAvailableException {
        boolean removed = this.bookList.removeIf(existedBook -> existedBook.getId() == book.getId());

        if (!removed) {
            throw new BookNotAvailableException("The book with ID " + book.getId() + " is not in your list.");
        }
    }

    public int getNumberOfIssuedBooks() {
        return (int) this.bookList.stream()
                .count();
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
