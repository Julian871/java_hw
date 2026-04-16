package com.education.hw.oop;

import java.util.Scanner;

import static com.education.hw.oop.Publication.getPublicationCount;

public class Main {
    public static void main(String[] args) {
        boolean flag = true;

        Library library = createLibrary();
        Scanner sc = new Scanner(System.in);

        while (flag) {
            library.printMenu();

            switch (checkInt(sc)) {

                case 1:
                    System.out.println("Choose publication type: ");
                    library.printTypePublication();
                    library.createPublication(sc, library);
                    break;

                case 2:
                    library.listPublications();
                    break;

                case 3:
                    System.out.println("Enter author name: ");
                    library.searchByAuthor(checkString(sc));
                    break;

                case 4:
                    System.out.println("Publication count: " + getPublicationCount());
                    break;

                case 5:
                    System.out.println("Enter  publication id: ");
                    library.deletePublicationById(checkInt(sc));
                    break;

                case 6:
                    System.out.println("exit...");
                    flag = false;
                    break;

                default:
                    System.out.println("Incorrect input");
                    break;
            }
        }

    }

    private static Library createLibrary() {
        Library library = new Library();

        // BOOKS
        Book book0 = new Book("The Hobbit", "J.R.R. Tolkien", 1937, "978-0547928227");
        Book book1 = new Book("The Hobbit", "J.R.R. Tolkien", 1937, "978-0547928227");
        Book book2 = new Book("1984", "George Orwell", 1949, "978-0451524935");
        Book book3 = new Book("Pride and Prejudice", "Jane Austen", 1813, "978-0141439518");
        Book book4 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "978-0743273565");

        // Magazines
        Magazine magazine1 = new Magazine("National Geographic", "Various", 2023, 482);
        Magazine magazine2 = new Magazine("Time", "Various", 2024, 125);
        Magazine magazine3 = new Magazine("Forbes", "Various", 2023, 789);
        Magazine magazine4 = new Magazine("The Economist", "Various", 2024, 56);

        // NEWSPAPERS
        Newspaper newspaper1 = new Newspaper("The Wall Street Journal", "News Corp", 2024, "Monday");
        Newspaper newspaper2 = new Newspaper("The New York Times", "A.G. Sulzberger", 2024, "Wednesday");
        Newspaper newspaper3 = new Newspaper("The Guardian", "Guardian Media Group", 2023, "Friday");
        Newspaper newspaper4 = new Newspaper("The Washington Post", "Jeff Bezos", 2024, "Sunday");

        library.addPublication(book1);
        library.addPublication(book2);
        library.addPublication(book3);
        library.addPublication(book4);

        library.addPublication(magazine1);
        library.addPublication(magazine2);
        library.addPublication(magazine3);
        library.addPublication(magazine4);

        library.addPublication(newspaper1);
        library.addPublication(newspaper2);
        library.addPublication(newspaper3);
        library.addPublication(newspaper4);

        return library;
    }

    public static int checkInt(Scanner sc) {
        while (true) {
            if (sc.hasNextInt()) {
                int number = sc.nextInt();
                sc.nextLine();
                return number;
            } else {
                System.out.print("It is not a number");
                sc.next();
            }
        }
    }

    public static String checkString(Scanner sc) {
        while (true) {
            String text = sc.nextLine();

            if (!text.trim().isEmpty()) {
                return text;
            } else {
                System.out.print("Is empty");
            }
        }
    }
}
