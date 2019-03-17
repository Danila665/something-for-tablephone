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
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Danila
 */
public class IndexController implements Controller {
    String name;
    String number;
    
    @Override
    public void process(HttpExchange he) throws IOException {
        
        if (he.getRequestMethod().equals("POST")){
            InputStream isr = he.getRequestBody();
            Scanner s = new Scanner(isr).useDelimiter("\\A");            
            String requestBody = s.hasNext() ? s.next() : "";
            //String decodedBody = URLDecoder.decode(requestBody, "UTF-8");

            Map<String, String> result = new HashMap<>();  
            for (String param : requestBody.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(URLDecoder.decode(entry[0], "UTF-8"), URLDecoder.decode(entry[1], "UTF-8"));
                }else{
                    result.put(URLDecoder.decode(entry[0], "UTF-8"), "");
                }
            }
            name = result.get("name");
            number = result.get("number");
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
                     "<table>",
                        "<tr>",
                            "<td>" +name + "</td>",
                            "<td>" +number + "</td>",
                        "</tr>",
                      "</table>",
                      "<form method=\"post\">",
                            "<input type=\"text\" name=\"name\">",
                            "<input type=\"text\" name=\"number\">",
                            "<button>Send</button>",
                      "</form>",
                "</body>",
            "</html>"
        );

        he.sendResponseHeaders(200, responseStr.length());
        he.getResponseBody().write(responseStr.getBytes());
        he.close(); 
    } 
}
