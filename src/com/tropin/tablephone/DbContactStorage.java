/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

import com.tropin.tablephone.interfaces.ContactStorage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Danila
 */
public class DbContactStorage implements ContactStorage{
    
    List<Contact> contacts = new ArrayList<>();
    public Connection connect() { 
        String url = "jdbc:sqlite:c://SQLite3/db.s3db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    @Override
    public void add(Contact contact) {
       String sql = "INSERT INTO contacts(name,number) VALUES(?,?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contact.getName().orElse("null"));
            pstmt.setString(2, contact.getNumber().orElse("null"));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Iterable<Contact> getAll() {
        List<Contact> contactsView = new ArrayList<>();
        String sql = "SELECT id, name, number FROM contacts";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                Contact contact = new Contact();
                contact.setId(rs.getInt("id"));
                contact.setName(rs.getString("name"));
                contact.setNumber(rs.getString("number"));
                contactsView.add(contact);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return contactsView;
    }
}
