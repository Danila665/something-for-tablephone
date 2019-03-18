package com.tropin.tablephone;

public interface ContactStorage {
    public void add(Contact contact);
    public Iterable<Contact> getAll();
}