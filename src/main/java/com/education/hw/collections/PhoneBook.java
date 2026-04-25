package com.education.hw.collections;

import java.util.*;

public class PhoneBook {

    private final List<Contact> contactList = new ArrayList<>();

    private final Set<Contact> contactSet = new HashSet<>();

    private final Map<String, List<Contact>> contactMap = new HashMap<>();

    public void findContactsByGroup(String group) {
        List<Contact> contacts = contactMap.get(group.toLowerCase());
        if (contacts == null || contacts.isEmpty()) {
            System.out.println("No contacts in group");
            return;
        }

        for (Contact contact: contacts) {
            System.out.println(contact);
        }
    }

    // if contacts before 1000
    public void findContactsByName(String name) {
        System.out.println();
        System.out.println("===========================================");
        for (Contact contact: contactList) {
            if(contact.getName().equalsIgnoreCase(name)) {
                System.out.println(contact);
            }
        }
    }

    public void deleteContact(String name, String number) {
        Contact findContact = null;

        // Clear List
        Iterator<Contact> listIterator = contactList.listIterator();
        while (listIterator.hasNext()){
            Contact con = listIterator.next();
            if(con.getName().equals(name) && con.getPhone().equals(number)) {
                findContact = con;
                listIterator.remove();
                break;
            }
        }

        if(findContact == null) {
            System.out.println("Contact Not Found");
            return;
        }

        // Clear Set
        contactSet.remove(findContact);

        // Clear Map
        String group = findContact.getGroup();
        List<Contact> groupList = contactMap.get(group);
        if (groupList != null) {
            Iterator<Contact> groupIterator = groupList.iterator();
            while (groupIterator.hasNext()) {
                Contact c = groupIterator.next();
                if (c.getName().equals(name) && c.getPhone().equals(number)) {
                    groupIterator.remove();
                    break;
                }
            }
            if (groupList.isEmpty()) {
                contactMap.remove(group);
            }
        }

        System.out.println("Contact cleared");

    }

    public void createContact(String name, String number, String email, String group) {
        Contact contact = new Contact(name, number, email, group);

        if(contactSet.contains(contact)) {
            System.out.println("Contact exist");
            return;
        }

        saveContact(contact);
    }

    private void saveContact(Contact contact) {
        contactList.add(contact);

        contactSet.add(contact);

        contactMap.computeIfAbsent(contact.getGroup(), k -> new ArrayList<>()).add(contact);
    }

    public void printAllContacts(){
        System.out.println();
        System.out.println("===========================================");
        if(contactList.isEmpty()) {
            System.out.println("No contacts");
            return;
        }

        for (Contact contact: contactList) {
            System.out.println(contact);
        }
        System.out.println("===========================================");
    }

    public void printMenu() {
        System.out.println("1. Add contact");
        System.out.println("2. Delete contact");
        System.out.println("3. Show all contacts");
        System.out.println("4. Find contact");
        System.out.println("5. Show contacts in group");
        System.out.println("0. Exit");
    }
}
