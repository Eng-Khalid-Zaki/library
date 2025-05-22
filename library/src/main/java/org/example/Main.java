package org.example;

import org.example.entity.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        User khalid = new Student(1, "Khalid");
        User Aya = new Teacher(10, "Aya");

        Library library = new Library();
        library.addBook(new Book(1, "Java programming", "Abdelrahman"));
        library.addBook(new Book(2, "C++ programming", "Abdelrahman"));
        library.addBook(new Book(3, "Python programming", "Abdelrahman"));
        library.addBook(new Book(4, "Go programming", "Abdelrahman"));
        library.addBook(new Book(5, "Angular programming", "Abdelrahman"));
        library.addBook(new Book(6, "React programming", "Abdelrahman"));
        library.addBook(new Book(7, "JS programming", "Abdelrahman"));
        library.addBook(new Book(8, "C# programming", "Abdelrahman"));
        library.addBook(new Book(9, "Assembly programming", "Abdelrahman"));
        library.addBook(new Book(10, "Kotlin programming", "Abdelrahman"));
        library.addBook(new Book(11, "Vue programming", "Abdelrahman"));

        library.listAvailableBooks();

        Book issuedBook = library.issueBook(1, khalid);
        System.out.println(issuedBook);
        Book issuedBook1 = library.issueBook(2, khalid);
        System.out.println(issuedBook1);
        Book issuedBook2 = library.issueBook(3, khalid);
        System.out.println(issuedBook2);
        Book issuedBook3 = library.issueBook(4, khalid);
        System.out.println(issuedBook3);

        library.returnBook(1, khalid);

    }
}