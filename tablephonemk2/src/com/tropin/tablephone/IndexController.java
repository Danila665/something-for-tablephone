/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

import com.sun.net.httpserver.HttpExchange;
import com.tropin.tablephone.interfaces.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Danila
 */
public class IndexController implements Controller {
    
    private final List<String> errors = new ArrayList<>();
    private final List<Contact> contacts = new ArrayList<>();
    private boolean isGood = true;
    private String name;
    private String number;
    private String regex = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
    
    @Override
    public void process(HttpExchange he) throws IOException {
        
        if (he.getRequestMethod().equals("POST")){
            InputStream isr = he.getRequestBody();
            Scanner s = new Scanner(isr).useDelimiter("\\A");            
            String requestBody = s.hasNext() ? s.next() : "";
            
            name = Utils.parseQueryString(requestBody).get("name").get(0);
            number = Utils.parseQueryString(requestBody).get("number").get(0);
            
            Contact contact = new Contact();
            
            
            if (name == null || name.isEmpty() || name.equals("")){
                isGood = false;
                errors.add("Поле для имени должно быть заполнено");
            }
            if (number == null || number.isEmpty() || number.equals("")){
                isGood = false;
                errors.add("Пожалуйста, введите номер телефона");
            }
            if (number != null &&  !number.matches(regex)){
                isGood = false;
                errors.add("Введенный телефонный номер (" + number + ") не является верным");
            }
            if (isGood == true){
                contact.setName(name);
                contact.setNumber(number);
                contacts.add(contact);
                errors.clear();
            }
        }
        isGood = true;
        String contactStr = "";
        if (contacts.size() > 0){
            for (Contact c : contacts){
                contactStr += "<tr><td>" + c.getName() + "</td><td>" + c.getNumber() + "</td></tr>";
            }
        }
        String errorsStr = "";
        if (errors.size() > 0){
            for (String s : errors){
                errorsStr += "<ul><li><font color=\"Red\">" + s.toString() + "</font></li></ul>";
            }
        }
        
        String responseStr = String.join(
            "<!DOCTYPE html>",
            "<html>",
                "<head>",
                    "<meta charset=\"UTF-8\">",
                    "<title>Tablephone</title>",
                "</head>",
                "<body>",
                    "<h1>Tablephone</h1>",
                    errorsStr,
                     "<table>",
                     contactStr,
                      "</table>",
                      "<form method=\"post\">",
                            "<input type=\"text\" placeholder=\"Введите ваше имя\" name=\"name\">",
                            "<input type=\"text\" placeholder=\"Введите номер телефона\" name=\"number\">",
                            "<button>Send</button>",
                      "</form>",
                "</body>",
            "</html>"
        );

        he.sendResponseHeaders(200, responseStr.getBytes().length);
        System.out.println(""+responseStr.length());
        he.getResponseBody().write(responseStr.getBytes());
        he.close(); 
    } 
}
