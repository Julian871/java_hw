package com.education.hw.collections;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean flag = true;

        PhoneBook phoneBook = new PhoneBook();

        Scanner sc = new Scanner(System.in);

        while (flag) {
            phoneBook.printMenu();

            switch (enterInt(sc)) {

                case 1:
                    System.out.println("Enter name: ");
                    String name = enterString(sc);
                    System.out.println("Enter number(without +): ");
                    String number = String.valueOf(enterLong(sc));
                    System.out.println("Enter email: ");
                    String email = enterString(sc);
                    System.out.println("Enter group: ");
                    String group = enterString(sc);
                    phoneBook.createContact(name, number, email, group);
                    break;

                case 2:
                    System.out.println("Enter contact name: ");
                    String nameForDelete = enterString(sc);
                    System.out.println("Enter number: ");
                    String numberForDelete = String.valueOf(enterLong(sc));
                    phoneBook.deleteContact(nameForDelete, numberForDelete);
                    break;

                case 3:
                    phoneBook.printAllContacts();
                    break;

                case 4:
                    System.out.println("Enter contact name: ");
                    phoneBook.findContactsByName(enterString(sc));
                    break;

                case 5:
                    System.out.println("Enter group name: ");
                    phoneBook.findContactsByGroup(enterString(sc));
                    break;

                case 0:
                    System.out.println("exit...");
                    flag = false;
                    break;

                default:
                    System.out.println("Incorrect choose");
            }
        }
    }

    private static int enterInt(Scanner sc) {
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

    private static long enterLong(Scanner sc) {
        while (true) {
            if (sc.hasNextLong()) {
                long number = sc.nextLong();
                sc.nextLine();
                return number;
            } else {
                System.out.print("Incorrect phone number");
                sc.next();
            }
        }
    }

    private static String enterString(Scanner sc) {
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
