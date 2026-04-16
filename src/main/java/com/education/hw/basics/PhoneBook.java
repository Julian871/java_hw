package com.education.hw.basics;

public class PhoneBook {

    private final String[] names = new String[5];
    private final String[] numbers = new String[5];
    private int count = 0;
    private boolean flag = true;

    public boolean getFlag() {
        return flag;
    }

    public int getCount(){
        return count;
    }

    public void saveNameAndNumber(String name, String number) {

        count++;
        for (int i = 0; i < 5; i++) {
            if(names[i] == null) {
                names[i] = name;
                numbers[i] = "+" + number;
                System.out.println("Number saved");
                System.out.println();
                return;
            }
        }

        System.out.println("Phone book is full");
        System.out.println();
    }

    public void printContacts() {
        if(count == 0) {
            System.out.println("No contacts");
            return;
        }

        System.out.println();
        System.out.println("Contacts count: " + count);

        for (int i = 0; i < names.length; i++) {
            if(names[i] == null) {
                return;
            }

            System.out.println(i+1 + ". " + names[i] + " - " + numbers[i]);
            System.out.println();
        }
    }

    private int getNameIndex(String name) {
        int index = -1;

        for (int i = 0; i < names.length; i++) {
            if(names[i] == null) break;
            if(names[i].equalsIgnoreCase(name)) {
                index = i;
                break;
            }
        }

        return index;
    }

    public void printNumberByName(String name) {
        int index = getNameIndex(name);

        if(index < 0) {
            System.out.println("Contact with " + name + " was not found");
            return;
        }

        System.out.println(index + 1 + ". " + names[index] + " - " + numbers[index]);
    }

    public void deleteNumberByNamePosition(String name) {
        int index = getNameIndex(name);
        if(index < 0) {
            System.out.println("Incorrect name: " + name);
            return;
        }

        names[index] = null;
        numbers[index] = null;

        for (int i = index; i < names.length - 1; i++) {
            names[i] = names[i + 1];
            numbers[i] = numbers[i + 1];
        }

        for (int i = count; i < names.length; i++) {
            names[i] = null;
            numbers[i] = null;
        }

        count--;

        System.out.println("Number deleted");
    }

    public void closePhoneBook() {
        flag = false;
    }

    public void printMenuList() {
        System.out.println();
        System.out.println("=============================");
        System.out.println("=============================");
        System.out.println("Select an action");
        System.out.println("1. Add new number");
        System.out.println("2. Show contacts");
        System.out.println("3. Find a contact by name");
        System.out.println("4. Delete number");
        System.out.println("5. Exit");
        System.out.println();
        System.out.println("Enter number");
    }
}
