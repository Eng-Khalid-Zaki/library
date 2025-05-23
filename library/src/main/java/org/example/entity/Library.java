package org.example.entity;

import org.example.dao.LibraryActions;
import org.example.exception.BookNotAvailableException;
import org.example.exception.DuplicatedBookIdException;
import org.example.exception.UserLimitExceededException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Library implements LibraryActions {
    List<Book> bookList;

    public Library() {
        this.bookList = new ArrayList<>();
    }

    public Library(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public void addBook(Book book) throws DuplicatedBookIdException {
        List<Integer> idsList = bookList.stream()
                .map(Book::getId)
                .toList();
        for (int id : idsList) {
            if(id == book.getId()) {
                throw new DuplicatedBookIdException("There is a book with this id: " + book.getId());
            }
        }

        bookList.add(book);
    }

    @Override
    public void issueBook(int BookId, User user) throws BookNotAvailableException {

        int currentNumberOfIssuedBooks = user.getNumberOfIssuedBooks();
        if(currentNumberOfIssuedBooks >= user.getMaxBooksAllowed()) {
            throw new UserLimitExceededException("You have reached your limits as a " + user.getClass().getSimpleName()  + " please return a book before issuing another one!");
        }

        Book issuedBook = getBookById(BookId);

        if(issuedBook == null  || issuedBook.isIssued()) {
            throw new BookNotAvailableException("This book is unavailable!");
        }

        try {
            user.addBook(issuedBook);
            issuedBook.setIssued(true);
        }catch(DuplicatedBookIdException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void returnBook(int BookId, User user) throws BookNotAvailableException {
        Book issuedBook = getBookById(BookId);

        if(issuedBook == null || !issuedBook.isIssued()) {
            throw new BookNotAvailableException("This book was not issued to be returned!");
        }

        try {
            user.removeBook(issuedBook);
            issuedBook.setIssued(false);
        }catch(BookNotAvailableException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void listAllBooks() {
        bookList.forEach(System.out::println);
    }

    @Override
    public void listAvailableBooks() {
        bookList.stream()
                .filter(book -> !book.isIssued())
                .toList()
                .forEach(System.out::println);
    }

    private Book getBookById(int bookId) {
        List<Book> filteredBooks = bookList.stream()
                .filter(book -> book.getId() == bookId)
                .toList();

        return filteredBooks.isEmpty() ? null : filteredBooks.getFirst();
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return bookList.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .toList();
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return bookList.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .toList();
    }

    @Override
    public String toString() {
        return "Library{" +
                "bookList=" + bookList +
                '}';
    }
}
