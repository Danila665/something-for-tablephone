/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone.interfaces;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

/**
 *
 * @author Danila
 */
public interface Controller {
    
    public void process(HttpExchange he) throws IOException;
}
