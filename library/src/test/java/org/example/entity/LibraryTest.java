package org.example.entity;

import org.example.exception.BookNotAvailableException;
import org.example.exception.BookNotIssuedException;
import org.example.exception.DuplicatedBookIdException;
import org.example.exception.UserLimitExceededException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    private static Library library;
    private static User khalid;
    private static User aya;

    @BeforeAll
    static void setup() {
        library = new Library();
        khalid = new Student(1, "Khalid");
        aya = new Teacher(11, "Aya");
    }

    @BeforeEach
    void setupBeforeEachUnitTest() {
        library.addBook(new Book(100, "Java advance", "Bassam"));
        library.addBook(new Book(101, "Java advance 2", "Abdelrahman"));
        library.addBook(new Book(102, "Java8", "Bassam"));
        library.addBook(new Book(103, "Java9", "Bassam"));
    }

    @AfterEach
    void cleanAfterEachUnitTest() {
        library.returnAllBooks(khalid);
        library.returnAllBooks(aya);
        library.bookList.clear();
    }

    @Test
    void addNewBook() {
        Book book1 = new Book(1, "Java", "Ahmed");
        library.addBook(book1);
        assertEquals(book1, library.bookList.get(2));
    }

    @Test
    void addExistingBook() {
        Book existingBook = new Book(100, "Java advance", "Bassam");
        assertThrows(DuplicatedBookIdException.class,() -> library.addBook(existingBook));
    }

    @Test
    void issueAvailableBook() {
        library.issueBook(100, khalid);
        assertEquals("[Book{id=100, title='Java advance', author='Bassam', isIssued=true}]", khalid.getBookList().toString());
    }

    @Test
    void issueIssuedBook() {
        library.issueBook(100, aya);
        assertThrows(BookNotAvailableException.class, () -> library.issueBook(100, khalid));
    }

    @Test
    void issueNonExistingBook() {
        assertThrows(NoSuchElementException.class, () -> library.issueBook(202, khalid));
    }

    @Test
    void issueMoreThanLimit() {
        library.issueBook(100, khalid);
        library.issueBook(101, khalid);
        library.issueBook(102, khalid);
        assertThrows(UserLimitExceededException.class, () -> library.issueBook(103, khalid));

    }

    @Test
    void returnIssuedBook() {
        library.issueBook(100, khalid);
        library.issueBook(101, khalid);
        int numberOfIssuedBooks = khalid.getNumberOfIssuedBooks();
        library.returnBook(100, khalid);
        assertEquals(--numberOfIssuedBooks, khalid.getNumberOfIssuedBooks());
    }

    @Test
    void returnNonIssuedBook() {
        assertThrows(BookNotIssuedException.class, () -> library.returnBook(102, khalid));
    }

    @Test
    void returnBookIssuedByAnotherUser() {
        library.issueBook(100, aya);
        assertThrows(BookNotAvailableException.class, () -> library.returnBook(100, khalid));
    }

    @Test
    void returnNonExistingBook() {
        assertThrows(NoSuchElementException.class, () -> library.returnBook(500, khalid));
    }

    @Test
    void getBooksByTitle() {
    }

    @Test
    void getBooksByAuthor() {
    }
}