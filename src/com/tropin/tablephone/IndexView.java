package com.tropin.tablephone;

import java.util.Map;

import com.tropin.tablephone.interfaces.ContactStorage;

public class IndexView extends HtmlView {

    @Override
    protected String getTitle() {
        return "Tablephone";
    }
    
    @Override
    protected String getBody(Map<String, Object> map) {
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
        
        String output = String.join("",
            "<h1>Tablephone</h1>",
            "<table>",
                contactStr,
            "</table>",
            "<ul>",
                errorsStr,
            "</ul>",
            "<form method=\"post\">",
                "<input type=\"text\" placeholder=\"Введите ваше имя\" name=\"name\" value=\"" + contact.getName().orElse("") + "\" " + (errors.containsKey("name") ? " class=\"input-error\" " : "") + " >",
                "<input type=\"text\" placeholder=\"Введите номер телефона\" name=\"number\" value=\"" + contact.getNumber().orElse("") + "\" " + (errors.containsKey("number") ? " class=\"input-error\" " : "") + " >",
                "<button>Send</button>",
            "</form>"
        );
        return output;
    }

}