package com.education.hw.exceptions;


import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean flag = true;

        Library library = new Library();
        Scanner sc = new Scanner(System.in);

        while (flag) {
            library.getMenu();

            switch (enterNumber(sc)) {

                case 1:
                    try {
                        library.getAllBooks();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Enter book title: ");
                    String title = enterString(sc);
                    System.out.println("Enter book author: ");
                    String author = enterString(sc);
                    System.out.println("Enter book copies: ");
                    int copies = enterNumber(sc);
                    library.addBook(new Book(title, author, copies));
                    break;

                case 3:
                    try {
                        System.out.println("Enter book title: ");
                        library.takeBook(enterString(sc));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    break;

                case 4:
                    try {
                        System.out.println("Enter book title: ");
                        library.returnBook(enterString(sc));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:
                    flag = false;
                    System.out.println("exit...");
                    break;

                default:
                    System.out.println("Incorrect input");
                    break;
            }
        }

    }

    public static int enterNumber(Scanner sc) {
        while (true) {
            try {
                int number = sc.nextInt();
                sc.nextLine();
                return number;
            } catch (InputMismatchException e) {
                System.out.println("Enter number: ");
                sc.nextLine();
            }
        }
    }

    public static String enterString(Scanner sc) {
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
