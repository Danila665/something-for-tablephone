/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

import com.sun.net.httpserver.HttpExchange;
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
    
        public Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
    return result;
}
    @Override
    public void process(HttpExchange he) throws IOException {
        
        InputStream isr = he.getRequestBody();
        Scanner s = new Scanner(isr).useDelimiter("\\A");            
        String result = s.hasNext() ? s.next() : "";
        
        System.out.println("result = " +queryToMap(result));

        
        String responseStr = String.join(
            "<!DOCTYPE html>",
            "<html>",
                "<head>",
                    "<title>Tablephone</title>",
                "</head>",
                "<body>",
                    "<h1>Tablephone</h1>",
                     "<table>",
                        "<tr>",
                            "<td>" +"danila" + "</td>",
                            "<td>" +"7909555555" + "</td>",
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
