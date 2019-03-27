/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

import com.tropin.tablephone.interfaces.Controller;
import com.tropin.tablephone.interfaces.Router;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Danila
 */
public class NotSoStupidRouter implements Router{
    
    private final Map<String, Optional<Controller>> routes = new HashMap<>(); 
    
    public void addRoute(String string, Optional<Controller> controller){
        routes.put(string, controller);
    }
    
    @Override
    public Optional<Controller> resolve(String uri) {
        return Optional.ofNullable(routes.get(uri)).orElse(Optional.empty());
        //return Optional.of(routes.get(uri)).orElse(Optional.empty()); - выяснить, почему не работает
    }
    
}
