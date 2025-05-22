package org.example.entity;

import org.example.dao.LibraryActions;
import org.example.exception.BookNotAvailableException;
import org.example.exception.UserLimitExceededException;

import java.util.ArrayList;
import java.util.List;

public class Library implements LibraryActions {
    List<Book> bookList;

    public Library() {
        this.bookList = new ArrayList<>();
    }

    public Library(List<Book> bookList) {
        this.bookList = bookList;
    }

    public void addBook(Book book) {
        bookList.add(book);
    }

    public Book issueBook(int id, User user) {
        int currentNumberOfIssuedBooks = user.getNumberOfIssuedBooks();
        if(currentNumberOfIssuedBooks >= user.getMaxBooksAllowed()) {
            throw new UserLimitExceededException("You have reached your limits, please return a book before issuing another one!");
        }
        List<Book> filteredBooks = bookList.stream()
                .filter(book -> book.getId() == id)
                .toList();

        Book issuedBook = filteredBooks.isEmpty() ? null : filteredBooks.getFirst();

        if(issuedBook == null ) {
            throw new BookNotAvailableException("This book is unavailable!");
        }

        issuedBook.setIssued(true);
        user.setNumberOfIssuedBooks(++currentNumberOfIssuedBooks);
        return issuedBook;
    }

    public void returnBook(int BookId, User user) {
        int currentNumberOfIssuedBooks = user.getNumberOfIssuedBooks();

        List<Book> filteredBooks = bookList.stream()
                .filter(book -> book.getId() == BookId)
                .toList();

        Book issuedBook = filteredBooks.isEmpty() ? null : filteredBooks.getFirst();

        if(issuedBook == null ) {
            throw new BookNotAvailableException("This book is unavailable!");
        }

        issuedBook.setIssued(false);

        user.setNumberOfIssuedBooks(--currentNumberOfIssuedBooks);
    }

    public void listAvailableBooks() {
        bookList.forEach(System.out::println);
    }
}
