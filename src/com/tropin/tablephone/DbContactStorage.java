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

import javax.sql.DataSource;

/**
 *
 * @author Danila
 */
public class DbContactStorage implements ContactStorage{

    final String STMT_INSERT = "INSERT INTO contacts(name,number) VALUES(?,?)";
    final String STMT_SELECT = "SELECT id, name, number FROM contacts";

    private DataSource dataSource;

    public DbContactStorage(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Contact contact) {

        try (Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(STMT_INSERT)) {
            pstmt.setString(1, contact.getName().orElse("null"));
            pstmt.setString(2, contact.getNumber().orElse("null"));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public Iterable<Contact> getAll() {
        List<Contact> contactsView = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(STMT_SELECT)){
            
            // loop through the result set
            while (rs.next()) {
                Contact contact = new Contact();
                contact.setId(rs.getInt("id"));
                contact.setName(rs.getString("name"));
                contact.setNumber(rs.getString("number"));
                contactsView.add(contact);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (Iterable<Contact>)contactsView;
    }
}
