package org.example;

import org.example.entity.Book;
import org.example.entity.Library;
import org.example.entity.Student;
import org.example.entity.User;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        User khalid = new Student(1, "Khalid");
        Library library = new Library();
        library.addBook(new Book(1, "java", "Ahmed"));
        library.addBook(new Book(2, "C++", "Mohammed"));
        library.addBook(new Book(3, "C#", "Hassan"));
        library.issueBook(1, khalid);
        library.issueBook(2, khalid);
        library.issueBook(3, khalid);
        System.out.println(khalid.getBookList());
        List<Book> bookList= khalid.getBookList();
        library.listAllBooks();
    }
}