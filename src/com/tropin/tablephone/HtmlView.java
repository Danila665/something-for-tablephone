/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

import java.util.Map;

/**
 *
 * @author Danila
 */
public class HtmlView {
    
    public String generate(Map<String, Object> map){
        
        String output = String.join("",
            "<!DOCTYPE html>",
            "<html>",
                "<head>",
                    "<link rel=\"stylesheet\"  type=\"text/css\" href=\"style.css\">",
                    "<meta charset=\"UTF-8\">",
                    "<title>",
                        getTitle(),
                    "</title>",
                "</head>",
                "<body>",
                    getBody(map),
                "</body>",
            "</html>"
        );
        return output;
    }

    protected String getTitle() {
        return "";
    }

    protected String getBody(Map<String, Object> map) {
        return "";
    }
    
}
