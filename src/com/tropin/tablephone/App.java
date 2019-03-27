/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

import com.sun.net.httpserver.HttpServer;
import com.tropin.tablephone.interfaces.Controller;
import com.tropin.tablephone.interfaces.Router;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Optional;


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
        NotSoStupidRouter router = new NotSoStupidRouter();
        
        router.addRoute("/", Optional.of(new IndexController(new ContactMemoryStorage())));
        router.addRoute("/favicon.ico", Optional.of(new FaviconController()));
        
        httpServer.createContext("/", new Handler(
                //new StupidRouter(new IndexController(new ContactMemoryStorage()), new FaviconController()),
                router,
                new ErrorController(), 
                new NotFoundController()));
        
       /* final Router router = new StupidRouter(new IndexController(new ContactMemoryStorage()), new FaviconController());
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
        });*/
        httpServer.start();
        
        
    }
    
}
