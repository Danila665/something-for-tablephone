package com.tropin.tablephone.interfaces;

import com.tropin.tablephone.Contact;

public interface ContactStorage {

    public void add(Contact contact);

    public Iterable<Contact> getAll();
}