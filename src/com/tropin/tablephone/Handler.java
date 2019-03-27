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

    private final Router router;
    private final Controller errorController;
    private final Controller notFoundController;

    public Handler(Router router, Controller errorController, Controller notFoundController) {
        this.router  = router;
        this.errorController  = errorController;
        this.notFoundController  = notFoundController;
    }
    
    @Override
    public void handle(HttpExchange he) throws IOException {
 
            try {
            router.resolve(he.getRequestURI().getPath())
                .orElse(notFoundController).process(he);
            } catch (IOException e) {
                he.setAttribute(HttpExchangeAttributesEnum.Error.name(), e);
                errorController.process(he);
            }
    }  
}
