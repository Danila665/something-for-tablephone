/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

import com.sun.net.httpserver.HttpExchange;
import com.tropin.tablephone.interfaces.ContactStorage;
import com.tropin.tablephone.interfaces.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Danila
 */
public class IndexController implements Controller {

    private final ContactStorage contactStorage;

    private String regex = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";

    public IndexController(ContactStorage contactStorage) {
        //java.util.Objects.requireNonNull(contactStorage);
        //java.util.Objects.requireNonNull(contactStorage, "Contact storage must not be null");
        //java.util.Objects.requireNonNullElse(contactStorage, new ContactMemoryStorage());
        if (contactStorage == null) {
            throw new IllegalArgumentException("Contact storage must not be null");
        }
        this.contactStorage = contactStorage;
    }

    @Override
    public void process(HttpExchange he) throws IOException {
        Map<String, String> errors = new HashMap<>();
        Contact contact = new Contact();
        
        if (he.getRequestMethod().equals("POST")){
            String requestBody = new String(he.getRequestBody().readAllBytes());

            contact.setName(Utils.parseQueryString(requestBody).get("name").get(0));
            contact.setNumber(Utils.parseQueryString(requestBody).get("number").get(0));
            
            if (!contact.getName().isPresent() || contact.getName().get().isEmpty()){
                errors.put("name", "Поле для имени должно быть заполнено");
            }
            if (!contact.getNumber().isPresent() || contact.getNumber().get().isEmpty()){
                errors.put("number", "Пожалуйста, введите номер телефона");
            }
            if (contact.getNumber().isPresent() && !contact.getNumber().get().matches(regex)){
                errors.put("number", "Введенный телефонный номер (" + contact.getNumber().get() + ") не является верным");
            }
            if (errors.isEmpty()) {
                contactStorage.add(contact);
                contact = new Contact();
            }
        }

        String contactStr = "";
        for (Contact c : contactStorage.getAll()) {
            contactStr += "<tr><td>" + Utils.escapeHTML(c.getName().orElse("")) + "</td><td>" + Utils.escapeHTML(c.getNumber().orElse("")) + "</td></tr>";
        }

        String errorsStr = "";
        for (String s : errors.values()) {
            errorsStr += "<li><font color=\"Red\">" + s.toString() + "</font></li>";
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
                    "<ul>",
                        errorsStr,
                    "</ul>",
                     "<table>",
                     contactStr,
                      "</table>",
                      "<form method=\"post\">",
                            "<input type=\"text\" placeholder=\"Введите ваше имя\" name=\"name\" value=\"" + contact.getName().orElse("") + "\" " + (errors.containsKey("name") ? " style=\"border-color:red;\" " : "") + " >",
                            "<input type=\"text\" placeholder=\"Введите номер телефона\" name=\"number\" value=\"" + contact.getNumber().orElse("") + "\" " + (errors.containsKey("number") ? " style=\"border-color:red;\" " : "") + " >",
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
