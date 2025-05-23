package org.example.dao;

import org.example.entity.Book;
import org.example.entity.User;

import java.util.List;

public interface LibraryActions {
    void addBook(Book book); //todo tested
    void issueBook(int bookId, User user); //todo tested
    void returnBook(int id, User user);
    void listAllBooks();  //todo tested
    void listAvailableBooks();
    List<Book> getBooksByTitle(String title);
    List<Book> getBooksByAuthor(String author);
}
