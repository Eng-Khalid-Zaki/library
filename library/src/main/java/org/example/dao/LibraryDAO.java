package org.example.dao;

import org.example.database.DatabaseConnector;
import org.example.entity.*;
import org.example.exception.BookNotAvailableException;
import org.example.exception.BookNotIssuedException;
import org.example.exception.UserLimitExceededException;

import javax.management.BadAttributeValueExpException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibraryDAO {
    private static final Logger logger = Logger.getLogger(DatabaseConnector.class.getName());

    public static List<Book> fetchBooks() {
        List<Book> fetchedBooks = new ArrayList<>();
        String sql = "SELECT id, title, author, is_issued FROM books";

        try (Connection conn = DatabaseConnector.getConnection();

             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                boolean isIssued = rs.getBoolean("is_issued");

                fetchedBooks.add(new Book(id, title, author, isIssued));
            }

        } catch (SQLException | IOException e) {
            logger.log(Level.SEVERE, "Database connection failed", e);
        }
        return fetchedBooks;
    } // todo finished and tested

    public static void deleteBook(int bookId)  throws NoSuchElementException {

        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);

            int rowsAffected = stmt.executeUpdate();

            if(rowsAffected > 0) {
                System.out.println("Book deleted successfully");
            }else {
                throw new NoSuchElementException("There is no book with this id " + bookId);
            }
        } catch (IOException | SQLException e) {
            System.err.println("Error issuing book: " + e.getMessage());
            logger.log(Level.SEVERE, "Database update failed", e);
        }


    } // todo finished and tested

    public static void addBook(Book book) throws BadAttributeValueExpException {
        if(book.getId() == 0) {String sql = "INSERT INTO books (title, author, is_issued, user_id) VALUES (?, ?, ?, NULL)";
            try (Connection conn = DatabaseConnector.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getAuthor());
                stmt.setBoolean(3, book.isIssued());

                int rowsAffected = stmt.executeUpdate();
                System.out.println(rowsAffected + " Rows Affected!");
            } catch (IOException | SQLException e) {
                System.err.println("Error fetching issued books: " + e.getMessage());
                logger.log(Level.SEVERE, "Database access error", e);
            }} else {
            throw new BadAttributeValueExpException("You can not add book to the library with manully setted id");
        }


    } //todo finished and tested

    public static void issueBook(int bookId, int userId) throws BookNotAvailableException, UserLimitExceededException, NoSuchElementException {
        User currentUser = getUserById(userId);

        if (currentUser == null) {
            throw new NoSuchElementException("There is user with id: " + userId + " in the library");
        }

        int currentNumberOfIssuedBooks = countUserBooks(userId);
        System.out.println(currentNumberOfIssuedBooks + ": :" + currentUser.getMaxBooksAllowed());
        if (currentNumberOfIssuedBooks >= currentUser.getMaxBooksAllowed()) {
            throw new UserLimitExceededException("You have reached your limits as a " + currentUser.getClass().getSimpleName() + " please return a book before issuing another one!");
        }

        Book issuedBook = getBookById(bookId);

        if (issuedBook == null) {
            throw new NoSuchElementException("There is no book with this id " + bookId + " in the library");
        } else if (issuedBook.isIssued()) {
            throw new BookNotAvailableException("This book is already issued");
        }

        String sql = "UPDATE books SET is_issued = 1, user_id = ? where id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book issued to user successfully.");
            } else {
                System.out.println("No book found with ID: " + bookId);
            }

        } catch (IOException | SQLException e) {
            System.err.println("Error issuing book: " + e.getMessage());
            logger.log(Level.SEVERE, "Database update failed", e);
        }


    } //todo finished and tested

    public static void returnBook(int bookId, int userId) throws BookNotAvailableException, NoSuchElementException, BookNotIssuedException {

        User currentUser = getUserById(userId);
        System.out.println(currentUser);
        if (currentUser == null) {
            throw new NoSuchElementException("There is user with id: " + userId + " in the library");
        }
        Book issuedBook = getBookById(bookId);
        System.out.println(issuedBook);
        if (issuedBook == null) {
            throw new NoSuchElementException("There is no book with this id " + bookId + " in the library");
        }

        if (!issuedBook.isIssued()) {
            throw new BookNotIssuedException("This book is not issued to be returned");
        }

        System.out.println(currentUser.getBookList());
        boolean issuedByUser = fetchUserBooks(userId).stream()
                .filter(curBook -> curBook.getId() == bookId).toList().isEmpty();
        if (issuedByUser) {
            throw new BookNotAvailableException("This book is issued by another user");
        }

        String sql = "UPDATE books SET is_issued = 0, user_id = NULL where id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book returned by the user successfully.");
            } else {
                System.out.println("No book found with ID: " + bookId);
            }

        } catch (IOException | SQLException e) {
            System.err.println("Error issuing book: " + e.getMessage());
            logger.log(Level.SEVERE, "Database update failed", e);
        }
    } // todo finished and tested

    public static List<Book> fetchUserBooks(int userId) {
        String sql = """
                    SELECT id, title, author, is_issued
                    FROM books
                    WHERE user_id = ? AND is_issued = 1
                """;


        List<Book> userBooks = new ArrayList<>();

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                boolean isIssued = rs.getBoolean("is_issued");

                userBooks.add(new Book(id, title, author, isIssued));
            }

        } catch (SQLException | IOException e) {
            System.err.println("Error fetching issued books: " + e.getMessage());
            logger.log(Level.SEVERE, "Database access error", e);
        }

        return userBooks;
    } // todo finished and tested

    public static int countUserBooks(int userId) {
        return (int) fetchUserBooks(userId).stream().count();
    } //todo finished and tested

    public static List<Book> fetchUnIssuedBooks() {
        List<Book> currentBookList;
        currentBookList = fetchBooks();
        return currentBookList.stream()
                .filter(book -> !book.isIssued())
                .toList();
    } // todo finished and tested

    public static List<Book> fetchIssuedBooks() {
        List<Book> currentBookList = new ArrayList<>();
        currentBookList = fetchBooks();
        return currentBookList.stream()
                .filter(Book::isIssued)
                .toList();
    } // todo finished and tested

    public static void listAvailableBooks() {
        fetchBooks().stream().filter(book -> !book.isIssued()).forEach(System.out::println);
    } //todo finished and tested

    private static Book getBookById(int bookId) {
        String sql = "SELECT * FROM books WHERE id = ?";
        Book currentBook = null;

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    boolean isIssued = rs.getBoolean("is_issued");

                    currentBook = new Book(bookId, title, author, isIssued);
                }
            }

        } catch (IOException | SQLException e) {
            logger.log(Level.SEVERE, "Database connection failed", e);
        }

        return currentBook;
    } // todo finished and tested

    public static User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = null;

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                String name = rs.getString("name");
                int maxBooksAllowed = rs.getInt("maxBooksAllowed");
                String userType = rs.getString("user_type");

                if (userType.equals("student")) {
                    user = new Student(userId, name, maxBooksAllowed);
                } else {
                    user = new Teacher(userId, name, maxBooksAllowed);
                }

            }

        } catch (IOException | SQLException e) {
            System.err.println("Error fetching user: " + e.getMessage());
            logger.log(Level.SEVERE, "Failed to fetch user by ID", e);
        }

        return user;
    } //todo finished and tested

    public static List<Book> getBooksByTitle(String title) {
        List<Book> books = fetchBooks();
        return books.stream().filter(book -> book.getTitle().equalsIgnoreCase(title)).toList();
    } // todo finished

    public static List<Book> getBooksByAuthor(String author) {
        List<Book> books = fetchBooks();
        return books.stream().filter(book -> book.getAuthor().equalsIgnoreCase(author)).toList();
    } // todo finished
}
