/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

/**
 *
 * @author Danila
 */
public class Contact {
    private int id;
    private String name;
    private String number;
    
    public int getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public String getNumber(){
        return number;
    }
    
    public void setId(int id){
        this.id = id;  
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setNumber(String number){
        this.number = number;
    }
    
}
