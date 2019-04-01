/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

import com.sun.net.httpserver.HttpExchange;
import com.tropin.tablephone.interfaces.Controller;

import java.io.IOException;
import java.util.Collections;

/**
 *
 * @author Danila
 */
public class NotFoundController implements Controller {

    @Override
    public void process(HttpExchange he) throws IOException {
        String responseStr = (new NotFoundView()).generate(Collections.emptyMap());
        he.sendResponseHeaders(404, responseStr.length());
        he.getResponseBody().write(responseStr.getBytes());
        he.close();
    }
    
}
