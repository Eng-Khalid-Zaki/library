package org.example.entity;

import org.example.dao.LibraryDAO;
import org.example.exception.BookNotAvailableException;
import org.example.exception.BookNotIssuedException;
import org.example.exception.UserLimitExceededException;
import org.junit.jupiter.api.Test;

import javax.management.BadAttributeValueExpException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    private static Library library;
    private static User khalid;
    private static User aya;

    @Test
    void fetchBooks() {
        List<Book> books = LibraryDAO.fetchBooks();

        assertEquals(8, books.size());
    }

    @Test
    void deleteExistingBook() {
        try{
            LibraryDAO.addBook(new Book("title", "author", false));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        int numberOfBooksBeforeDeletion = LibraryDAO.fetchBooks().size();
        int lastBookId = LibraryDAO.fetchBooks().getLast().getId();
        LibraryDAO.deleteBook(lastBookId);
        int numberOfBooksAfterDeletion = LibraryDAO.fetchBooks().size();

        assertEquals(numberOfBooksAfterDeletion, --numberOfBooksBeforeDeletion);
    }

    @Test
    void deleteNonExistingBook() {
        assertThrows(NoSuchElementException.class, () -> LibraryDAO.deleteBook(10000));
    }

    @Test
    void returnIssuedBookByHisUser() {
        int numberOfUserBooksBeforeReturningBook = LibraryDAO.countUserBooks(3);
        LibraryDAO.returnBook(4, 3);
        int numberOfUserBooksAfterReturningBook = LibraryDAO.countUserBooks(3);
        LibraryDAO.issueBook(4, 3);
        assertEquals(--numberOfUserBooksBeforeReturningBook, numberOfUserBooksAfterReturningBook);
    }

    @Test
    void returnIssuedBookByAnotherUser() {
        assertThrows(BookNotAvailableException.class, () -> LibraryDAO.returnBook(4, 1));
    }

    @Test
    void returnNonExistingBook() {
        assertThrows(NoSuchElementException.class, () -> LibraryDAO.returnBook(10000, 1));
    }

    @Test
    void returnBookByNonExistingUser() {
            assertThrows(NoSuchElementException.class, () -> LibraryDAO.returnBook(8, 500));
    }

    @Test
    void returnNonIssuedBook() {
        assertThrows(BookNotIssuedException.class, () -> LibraryDAO.returnBook(5, 1));
    }

    @Test
    void addBook() {
        int numberOfBooksBeforeAdding = LibraryDAO.fetchBooks().size();
        try {
            LibraryDAO.addBook(new Book("Java advanced 2 test", "Mohammed", false));
        } catch(BadAttributeValueExpException e) {
            System.out.println(e.getMessage());
        }



        assertEquals(++numberOfBooksBeforeAdding, LibraryDAO.fetchBooks().size());
        Book lastBook = LibraryDAO.fetchBooks().getLast();
        LibraryDAO.deleteBook(lastBook.getId());
    }

    @Test
    void issueAvailableBook() {
        int numberOfUserBooksBeforeIssuing = LibraryDAO.fetchUserBooks(1).size();
        LibraryDAO.issueBook(6, 1);
        int numberOfUserBooksAfterIssuing = LibraryDAO.countUserBooks(1);
        assertEquals(++numberOfUserBooksBeforeIssuing, numberOfUserBooksAfterIssuing);

        LibraryDAO.returnBook(6, 1);

    }

    @Test
    void issueExistingBookToNonExistingUser() {
        assertThrows(NoSuchElementException.class, () -> LibraryDAO.issueBook(6, 11));
    }

    @Test
    void issueNonExistingBookToExistingUser() {
        assertThrows(BookNotAvailableException.class, () -> LibraryDAO.issueBook(1, 1));
    }

    @Test
    void overflowIssuingLimitation() {
        LibraryDAO.issueBook(3, 1);
        LibraryDAO.issueBook(7, 1);
        assertThrows(UserLimitExceededException.class, () -> LibraryDAO.issueBook(8, 1));
    }

    @Test
    void fetchUnIssuedBooks() {
        assertEquals(5, LibraryDAO.fetchUnIssuedBooks().size());
    }

    @Test
    void fetchIssuedBooks() {
        assertEquals(4, LibraryDAO.fetchIssuedBooks().size());
    }

    @Test
    void getBooksByTitle() {
        assertEquals(3, LibraryDAO.getBooksByTitle("Java Programming").size());
    }

    @Test
    void getBooksByAuthor() {
        assertEquals(3, LibraryDAO.getBooksByAuthor("Bassam").size());
    }

}