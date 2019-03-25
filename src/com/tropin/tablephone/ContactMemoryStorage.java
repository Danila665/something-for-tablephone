package com.tropin.tablephone;

import java.util.ArrayList;
import java.util.List;

import com.tropin.tablephone.interfaces.ContactStorage;

public class ContactMemoryStorage implements ContactStorage {
    private final List<Contact> contacts = new ArrayList<>();
    
    @Override
    public void add(Contact contact) {
        contacts.add(contact);
    }

    @Override
    public Iterable<Contact> getAll() {
        return contacts;
    }
}