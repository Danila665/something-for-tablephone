/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.tropin.tablephone.interfaces.Controller;
import com.tropin.tablephone.interfaces.Router;
import java.io.IOException;

/**
 *
 * @author Danila
 */
public class Handler implements HttpHandler{

    @Override
    public void handle(HttpExchange he) throws IOException {
        
        final Router router = new StupidRouter(new IndexController(new ContactMemoryStorage()), new FaviconController());
        final Controller errorController = new ErrorController();
        final Controller notFoundController = new NotFoundController();
       
        try {
            router.resolve(he.getRequestURI().getPath())
                .orElse(notFoundController).process(he);
            } catch (IOException e) {
                he.setAttribute(HttpExchangeAttributesEnum.Error.name(), e);
                errorController.process(he);
            }
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
