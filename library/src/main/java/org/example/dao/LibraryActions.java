package org.example.dao;

import org.example.entity.Book;
import org.example.entity.User;

import java.util.List;

public interface LibraryActions {
    void addBook(Book book);
    void issueBook(int bookId, User user);
    void returnBook(int id, User user);
    void listAllBooks();
    void listAvailableBooks();
    List<Book> getBooksByTitle(String title);
    List<Book> getBooksByAuthor(String author);
}
