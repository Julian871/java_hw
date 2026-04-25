package com.education.hw.exceptions;

import java.util.ArrayList;
import java.util.List;

public class Library {
    List<Book> catalog = new ArrayList<>();

    public void addBook(Book book) {
        catalog.add(book);
    }

    public void takeBook(String title) throws MyCustomException {

        Book foundBook = null;

        for (Book book: catalog) {
            if(book.getTitle().equalsIgnoreCase(title)) {
                foundBook = book;
                break;
            }
        }

        if(foundBook == null) {
            throw new MyCustomException("Book Not Found");
        }

        if(foundBook.getAvailableCopies() == 0) {
            throw new MyCustomException("No available books");
        }

        System.out.println("Have fun reading");
        int bookIndex = catalog.indexOf(foundBook);
        foundBook.reduceAvailableCopies();
        catalog.set(bookIndex, foundBook);
    }

    public void returnBook(String title) throws MyCustomException {
        Book foundBook = null;

        for (Book book: catalog) {
            if(book.getTitle().equalsIgnoreCase(title)) {
                foundBook = book;
                break;
            }
        }

        if(foundBook == null) {
            throw new MyCustomException("This book is not from our catalog");
        }

        int bookIndex = catalog.indexOf(foundBook);
        foundBook.decreaseAvailableCopies();
        catalog.set(bookIndex, foundBook);
        System.out.println("Thanks for the book");
    }

    public void getAllBooks() throws MyCustomException {
        if(catalog.isEmpty()) {
            throw new MyCustomException("Catalog is empty");
        }
        for (Book book: catalog) {
            System.out.println(book);
        }
    }

    public void getMenu() {
        System.out.println();
        System.out.println("1. Get all catalog");
        System.out.println("2. Add book");
        System.out.println("3. Get book");
        System.out.println("4. Return book");
        System.out.println("5. Exit");
    }
}
