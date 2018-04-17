/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andiazher.contability.controller;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author andre
 */
public class JSONA {
    
    private Map<String, Object> object;

    public JSONA() {
        object= new HashMap<>();
    }
    
    public void add(String key, String value){
        object.put(key, value);
    }
    
    public void add(String key, JSONA value){
        object.put(key, value);
    }

    public Map<String, Object> getObject() {
        return object;
    }
    public Object getValue(String key){
        return object.get(key);
    }
    public void setObject(Map<String, Object> object) {
        this.object = object;
    }
    
    
    
    @Override
    public String toString() {
        String s = "{";
        try{
            String k = "";
            for(Map.Entry<String, Object> j: object.entrySet()){
                s+=k;
                if(j.getValue().getClass().equals(JSONA.class)){
                    s+=" \""+j.getKey()+"\""+" :\n"+"  "+j.getValue()+"   ";
                }
                else{
                    s+=" \""+j.getKey()+"\""+":"+"\""+j.getValue()+"\"  ";
                }
                k=", \n";
            }
        }catch(NullPointerException k){
            System.err.println("Algunos de los objetos son nulos.");
            k.printStackTrace();
        }
        s+="}";
        return s;
    }

}
