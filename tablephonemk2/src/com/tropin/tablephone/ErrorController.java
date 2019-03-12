/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author Danila
 */
public class ErrorController implements Controller {
    public static String ERROR_ATTR = "error";

    @Override
    public void process(HttpExchange he) throws IOException {
        Exception e = (Exception)he.getAttribute(HttpExchangeAttributesEnum.Error.name());
                
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String responseStr = sw.toString();
        he.sendResponseHeaders(500, responseStr.length());
        he.getResponseBody().write(responseStr.getBytes());
        he.close();
    }
    
}
