/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

import java.util.Optional;

/**
 *
 * @author Danila
 */
public interface Router {
    public Optional<Controller> resolve(String uri);
}
