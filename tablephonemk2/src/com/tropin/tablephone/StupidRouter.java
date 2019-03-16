/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

import java.util.Optional;

import com.tropin.tablephone.interfaces.Controller;
import com.tropin.tablephone.interfaces.Router;

/**
 *
 * @author Danila
 */
public class StupidRouter implements Router {
    
    final private Controller indexController;
    final private Controller faviconController;
    
    public StupidRouter() {
        this.indexController = new IndexController();
        this.faviconController = new FaviconController();
    }

    @Override
    public Optional<Controller> resolve(String uri) {
               
        if (uri.equals("/")) {
            return Optional.of(indexController);
        }
        if (uri.equals("/favicon.ico")){
            return Optional.of(faviconController);
        }
        
        return Optional.empty();
    }
    
    
}
