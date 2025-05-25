package org.example.entity;

import org.example.dao.LibraryActions;
import org.example.exception.BookNotAvailableException;
import org.example.exception.BookNotIssuedException;
import org.example.exception.DuplicatedBookIdException;
import org.example.exception.UserLimitExceededException;

import java.util.*;

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
        if (bookList.stream().anyMatch(b -> b.getId() == book.getId())) {
            throw new DuplicatedBookIdException("There is a book with this id: " + book.getId());
        }

        bookList.add(book);
    }

    @Override
    public void issueBook(int bookId, User user) throws BookNotAvailableException, UserLimitExceededException, NoSuchElementException {

        int currentNumberOfIssuedBooks = user.getNumberOfIssuedBooks();
        if (currentNumberOfIssuedBooks >= user.getMaxBooksAllowed()) {
            throw new UserLimitExceededException("You have reached your limits as a " + user.getClass().getSimpleName() + " please return a book before issuing another one!");
        }

        Book issuedBook = getBookById(bookId);

        if (issuedBook == null) {
            throw new NoSuchElementException("There is no book with this id " + bookId + " in the library");
        } else if (issuedBook.isIssued()) {
            throw new BookNotAvailableException("This book is already issued");
        }

        try {
            user.addBook(issuedBook);
            issuedBook.setIssued(true);
        } catch (DuplicatedBookIdException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void returnBook(int bookId, User user) throws BookNotAvailableException, NoSuchElementException, BookNotIssuedException {
        Book issuedBook = getBookById(bookId);

        if (issuedBook == null) {
            throw new NoSuchElementException("There is no book with this id " + bookId + " in the library");
        }

        if (!issuedBook.isIssued()) {
            throw new BookNotIssuedException("This book is not issued to be returned");
        }

        boolean issuedByUser = user.getBookList().stream()
                .filter(curBook -> curBook.getId() == bookId).toList().isEmpty();
        if (issuedByUser) {
            throw new BookNotAvailableException("This book is issued by another user");
        }

        try {
            user.removeBook(issuedBook);
            issuedBook.setIssued(false);
        } catch (BookNotAvailableException e) {
            System.out.println(e.getMessage());
        }
    }

    public void returnAllBooks(User user) {
        List<Book> bookList = new ArrayList<>(user.getBookList());
        Iterator<Book> iterator = bookList.iterator();

        while (iterator.hasNext()) {
            Book currentBook = iterator.next();
            System.out.println(currentBook + " " + currentBook.getId());
            this.returnBook(currentBook.getId(), user);
            iterator.remove();
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
