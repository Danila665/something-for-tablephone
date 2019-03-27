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
public class StaticController implements Controller{

    private final String uri;

    public StaticController(String string){
        uri = string;
    }
    
    @Override
    public void process(HttpExchange he) throws IOException {
        try {
            //Path file = Paths.get(getClass().getResource("/resources/img/favicon.ico").toURI());
            Path file = Paths.get(getClass().getResource(uri).toURI());
            he.getResponseHeaders().set("content-type", "text/css");
            he.sendResponseHeaders(200, file.toFile().length());
            Files.copy(file, he.getResponseBody());
            he.close();    
        } catch (URISyntaxException e) {
           throw new IOException(e);
        }
        
        
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
