/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;


/**
 *
 * @author Danila
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        
        final Router router = new StupidRouter();
        final Controller errorController = new ErrorController();
        final Controller notFoundController = new NotFoundController();
        
        httpServer.createContext("/", he -> {
            try {
                router.resolve(he.getRequestURI().getPath())
                    .orElse(notFoundController).process(he);
            } catch (Exception e) {
                he.setAttribute(HttpExchangeAttributesEnum.Error.name(), e);
                errorController.process(he);
            }
        });
        httpServer.start();
        
        
    }
    
}
