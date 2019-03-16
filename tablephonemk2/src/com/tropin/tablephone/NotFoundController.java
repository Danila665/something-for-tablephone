/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

import com.sun.net.httpserver.HttpExchange;
import com.tropin.tablephone.interfaces.Controller;

import java.io.IOException;

/**
 *
 * @author Danila
 */
public class NotFoundController implements Controller {

    @Override
    public void process(HttpExchange he) throws IOException {
        String responseStr = String.join(
            "<!DOCTYPE html>",
            "<html>",
                "<head>",
                    "<title>Not Found</title>",
                "</head>",
                "<body>",
                    "<h1>Not Found</h1>",
                "</body>",
            "</html>"
        );
        he.sendResponseHeaders(404, responseStr.length());
        he.getResponseBody().write(responseStr.getBytes());
        he.close();
    }
    
}
