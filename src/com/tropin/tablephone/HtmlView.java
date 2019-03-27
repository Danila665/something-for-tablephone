/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

import com.tropin.tablephone.interfaces.ContactStorage;
import java.util.Map;

/**
 *
 * @author Danila
 */
public class HtmlView {
    private String output;
    
    public String generate(Map<String, Object> map){
        
        Object cs = map.get("contactStorage");
        ContactStorage contactStorage = (ContactStorage)cs;
        
        Object err = map.get("errors");
        Map<String, String> errors = (Map<String, String>)err;
        
        Object con = map.get("contact");
        Contact contact = (Contact)con;
        
        String contactStr = "";
        for (Contact c : contactStorage.getAll()) {
            contactStr += "<tr><td>" + Utils.escapeHTML(c.getName().orElse("")) + "</td><td>" + Utils.escapeHTML(c.getNumber().orElse("")) + "</td></tr>";
        }

        String errorsStr = "";
        for (String s : errors.values()) {
            errorsStr += "<li class=\"text-error\">" + s.toString() + "</li>";
        }
        
        output = String.join(
            "<!DOCTYPE html>",
            "<html>",
                "<head>",
                    "<link rel=\"stylesheet\"  type=\"text/css\" href=\"style.css\">",
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
                            "<input type=\"text\" placeholder=\"Введите ваше имя\" name=\"name\" value=\"" + contact.getName().orElse("") + "\" " + (errors.containsKey("name") ? " class=\"input-error\" " : "") + " >",
                            "<input type=\"text\" placeholder=\"Введите номер телефона\" name=\"number\" value=\"" + contact.getNumber().orElse("") + "\" " + (errors.containsKey("number") ? " class=\"input-error\" " : "") + " >",
                            "<button>Send</button>",
                      "</form>",
                "</body>",
            "</html>"
        );
        return output;
    }
    
}
