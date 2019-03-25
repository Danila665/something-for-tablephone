/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

import com.sun.net.httpserver.HttpExchange;
import com.tropin.tablephone.interfaces.Controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Danila
 */
public class FaviconController implements Controller {
    
    @Override
    public void process(HttpExchange he) throws IOException {
        try {
            Path file = Paths.get(getClass().getResource("/resources/img/favicon.ico").toURI());
            he.getResponseHeaders().set("content-type", "image/x-icon");
            he.sendResponseHeaders(200, file.toFile().length());
            Files.copy(file, he.getResponseBody());
            he.close();    
        } catch (URISyntaxException e) {
           throw new IOException(e);
        }
    }
}
