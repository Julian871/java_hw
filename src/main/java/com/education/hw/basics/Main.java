package com.education.hw.basics;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PhoneBook pb = new PhoneBook();
        Scanner sc = new Scanner(System.in);
        while (pb.getFlag()) {
            pb.printMenuList();
            int menuNumber = checkInt(sc);

            switch (menuNumber) {
                case 1:
                    if(pb.getCount() == 5) {
                        System.out.println("Phonebook is full");
                        break;
                    }

                    System.out.println("Enter name");
                    String name = checkString(sc);
                    System.out.println("Enter number");
                    String number = String.valueOf(checkInt(sc));
                    pb.saveNameAndNumber(name, number);
                    break;

                case 2:
                    pb.printContacts();
                    break;

                case 3:
                    System.out.println("Enter name");
                    pb.printNumberByName(checkString(sc));
                    break;

                case 4:
                    System.out.println("Enter name");
                    pb.deleteNumberByNamePosition(checkString(sc));
                    break;

                case 5:
                    System.out.println("exit...");
                    pb.closePhoneBook();
                    break;

                default:
                    System.out.println("Incorrect input");
                    break;
            }
        }
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
