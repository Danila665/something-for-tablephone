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
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Danila
 */
public class IndexController implements Controller {

    public final ContactStorage contactStorage;

    private String regex = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";

    public IndexController(ContactStorage contactStorage) {
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
            //String requestBody = new String(he.getRequestBody().readAllBytes()); - Java8 не поддерживает;
            InputStream isr = he.getRequestBody();
            Scanner s = new Scanner(isr).useDelimiter("\\A");            
            String requestBody = s.hasNext() ? s.next() : "";

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

        HtmlView htmlview = new IndexView();
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("contactStorage", contactStorage);
        paramsMap.put("errors", errors);
        paramsMap.put("contact", contact);
        
        String responseStr = htmlview.generate(paramsMap);

        he.sendResponseHeaders(200, responseStr.getBytes().length);
        he.getResponseBody().write(responseStr.getBytes());
        he.close(); 
    } 
}