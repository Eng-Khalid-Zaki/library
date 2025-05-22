package org.example.dao;

import org.example.entity.Book;
import org.example.entity.User;

public interface LibraryActions {
    void addBook(Book book); //todo tested
    Book issueBook(int bookId, User user); //todo tested
    void returnBook(int id, User user);
    void listAvailableBooks();  //todo tested
}
