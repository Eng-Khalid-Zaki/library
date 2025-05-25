package org.example.entity;

import org.example.exception.BookNotAvailableException;
import org.example.exception.DuplicatedBookIdException;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private static Library library;
    private static List<Book> bookList;
    private static User khalid;
    private static User aya;

    @BeforeAll
    static void setupUsers() {
        khalid = new Student(1, "Khalid");
        aya = new Teacher(11, "Aya");
    }

    @BeforeEach
    void beforeUnitTestSetup() {
        Book StudentBook = new Book(1, "Java", "Bassam");
        Book TeaherBook = new Book(11, "Java Advanced", "Bassam");
        khalid.addBook(StudentBook);
        aya.addBook(TeaherBook);
    }

    @AfterEach
    void afterUnitTestSetup() {
        khalid.getBookList().clear();
        aya.getBookList().clear();
    }

    @Test
    void getId() {
        assertEquals(1, khalid.getId());
        assertEquals(11, aya.getId());
    }

    @Test
    void setId() {
        khalid.setId(5);
        assertEquals(5, khalid.getId());

        aya.setId(15);
        assertEquals(15, aya.getId());
    }

    @Test
    void getName() {
        assertEquals("Khalid", khalid.getName());
        assertEquals("Aya", aya.getName());
    }

    @Test
    void setName() {
        khalid.setName("Ahmed");
        assertEquals("Ahmed", khalid.getName());
        aya.setName("Mariem");
        assertEquals("Mariem", aya.getName());
    }

    @Test
    void addNewBook() {
        System.out.println(khalid.getBookList());
        Book newStudentBook = new Book(20, "Java Spring", "Bassam");
        khalid.addBook(newStudentBook);
        assertEquals(khalid.getBookList().get(1), newStudentBook);
    }

    @Test
    void addDuplicateBook() {
        Book newStudentBook = new Book(1, "Java", "Bassam");
        assertThrows(DuplicatedBookIdException.class,() -> khalid.addBook(newStudentBook));
    }

    @Test
    void removeExistingBook() {
        int numberOfExistingBooks = khalid.getNumberOfIssuedBooks();
        khalid.removeBook(new Book(1, "Java", "Bassam"));
        assertEquals(--numberOfExistingBooks, khalid.getNumberOfIssuedBooks());
    }

    @Test
    void removeNonExistingBook() {
        assertThrows(BookNotAvailableException.class, () -> khalid.removeBook(new Book(2, "Java", "Bassam")));
    }

    @Test
    void getNumberOfIssuedBooks() {
        System.out.println(khalid.getBookList());
        khalid.addBook(new Book(2, "Java8", "Bassam"));
        khalid.addBook(new Book(3, "Java9", "Bassam"));
        assertEquals(3, khalid.getNumberOfIssuedBooks());
        khalid.removeBook(new Book(3, "Java9", "Bassam"));
        assertEquals(2, khalid.getNumberOfIssuedBooks());
    }

    @Test
    void getMaxBooksAllowed() {
        assertEquals(3, khalid.getMaxBooksAllowed());
        assertEquals(10, aya.getMaxBooksAllowed());

    }

    @Test
    void setMaxBooksAllowed() {
        khalid.setMaxBooksAllowed(5);
        assertEquals(5, khalid.getMaxBooksAllowed());
        aya.setMaxBooksAllowed(15);
        assertEquals(15, aya.getMaxBooksAllowed());
    }

    @Test
    void getBookList() {
        System.out.println(khalid.getBookList());
        khalid.addBook(new Book(2, "Java8", "Bassam"));
        khalid.addBook(new Book(3, "Java9", "Bassam"));
        assertEquals("[Book{id=1, title='Java', author='Bassam', isIssued=false}, Book{id=2, title='Java8', author='Bassam', isIssued=false}, Book{id=3, title='Java9', author='Bassam', isIssued=false}]", khalid.getBookList().toString());
    }
}