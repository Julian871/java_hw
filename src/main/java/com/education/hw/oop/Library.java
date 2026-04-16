package com.education.hw.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.education.hw.oop.Main.checkInt;
import static com.education.hw.oop.Main.checkString;
import static com.education.hw.oop.Publication.reducePublicationCount;

public class Library {

    private final List<Publication> publications = new ArrayList<>();

    public void addPublication(Publication pub) {
        publications.add(pub);
    }

    public void deletePublicationById(int id) {
        int pubIndex = -1;

        for (Publication publication: publications) {
            if(publication.getId() == id) {
                pubIndex = publications.indexOf(publication);
                break;
            }
        }

        if(pubIndex < 0) {
            System.out.println("Publication Not Found");
            return;
        }

        publications.remove(pubIndex);
        reducePublicationCount();
        System.out.println("Publication removed");
    }

    public void listPublications() {
        if(publications.isEmpty()) System.out.println("No publications");

        System.out.println("=================================");
        System.out.println();
        for (Publication publication: publications) {
            System.out.println(publication);
        }
        System.out.println();
        System.out.println("==================================");
    }

    public void searchByAuthor(String author) {
        int countPub = 0;
        for (Publication publication: publications) {
            if(publication.getAuthor().equalsIgnoreCase(author)) {
                countPub++;
                System.out.println(publication);
            }
        }

        if(countPub == 0) {
            System.out.println("Book Not Found");
        }
    }

    public void createPublication(Scanner sc, Library library) {
        int number = checkInt(sc);

        System.out.println("Enter title");
        String title = checkString(sc);
        System.out.println("Enter author");
        String author = checkString(sc);
        System.out.println("Enter year");
        int year = checkInt(sc);

        switch (number) {

            case 1:
                System.out.println("Enter ISBN");
                String ISBN = checkString(sc);
                Book book = new Book(title, author, year, ISBN);
                savePublication(book, library);
                break;

            case 2:
                System.out.println("Enter issue number");
                int issueNumber = checkInt(sc);
                Magazine magazine = new Magazine(title, author, year, issueNumber);
                savePublication(magazine, library);
                break;

            case 3:
                System.out.println("Enter publication day");
                String publicationDay = checkString(sc);
                Newspaper newspaper = new Newspaper(title, author, year, publicationDay);
                savePublication(newspaper, library);
                break;
        }
    }

    private void savePublication(Publication pub, Library library) {
        library.addPublication(pub);
    }

    public void printMenu() {
        System.out.println("1. Add new publication");
        System.out.println("2. Print list of publications");
        System.out.println("3. Get publication by author");
        System.out.println("4. Get publication count");
        System.out.println("5. Delete publication by id");
        System.out.println("6. Exit");
    }

    public void printTypePublication() {
        System.out.println("1. Book");
        System.out.println("2. Magazine");
        System.out.println("3. Newspaper");
    }

    @Override
    public String toString() {
        return "Library{" +
                "publications=" + publications +
                '}';
    }
}
