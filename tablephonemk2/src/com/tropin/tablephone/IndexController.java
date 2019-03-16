/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

/**
 *
 * @author Danila
 */
public class IndexController implements Controller {

    @Override
    public void process(HttpExchange he) throws IOException {
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
