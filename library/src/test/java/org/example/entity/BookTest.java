package org.example.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    private static List<Book> bookList = new ArrayList<>();

    @BeforeEach
    void beforeUnitTestSetup() {
        Book book1 = new Book(1, "Java", "Bassam");
        Book book2 = new Book(2, "Java8", "Bassam");
        Book book3 = new Book(3, "Java9", "Bassam");
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
    }

    @AfterEach
    void afterUnitTestSetup() {
        bookList.clear();
    }

    @Test
    void getId() {
        assertEquals(1, bookList.getFirst().getId());
        assertEquals(2, bookList.get(1).getId());
        assertEquals(3, bookList.get(2).getId());
    }

    @Test
    void setId() {
        bookList.getFirst().setId(4);
        assertEquals(4, bookList.getFirst().getId());
    }

    @Test
    void getTitle() {
        assertEquals("Java", bookList.getFirst().getTitle());
    }

    @Test
    void setTitle() {
        bookList.getFirst().setTitle("Java test");
        assertEquals("Java test", bookList.getFirst().getTitle());
    }

    @Test
    void getAuthor() {
        assertEquals("Bassam", bookList.getFirst().getAuthor());
    }

    @Test
    void setAuthor() {
        bookList.getFirst().setAuthor("Aya");
        assertEquals("Aya", bookList.getFirst().getAuthor());
    }

    @Test
    void isIssued() {
        assertFalse(bookList.getFirst().isIssued());
    }

    @Test
    void setIssued() {
        bookList.getFirst().setIssued(true);
        assertTrue(bookList.getFirst().isIssued());
    }
}