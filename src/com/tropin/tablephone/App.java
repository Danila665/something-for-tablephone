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
import java.sql.Connection;
import java.sql.DriverManager;
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
        
        //router.addRoute("/", (new IndexController(new ContactMemoryStorage())));
        router.addRoute("/", (new IndexController(new DbContactStorage())));
        router.addRoute("/favicon.ico", new StaticController("/resources/img/favicon.ico"));
        router.addRoute("/style.css", new StaticController("/resources/css/style.css"));
        
        httpServer.createContext("/", new Handler(
                router,
                new ErrorController(), 
                new NotFoundController()));

        httpServer.start(); 
    }
    
}
