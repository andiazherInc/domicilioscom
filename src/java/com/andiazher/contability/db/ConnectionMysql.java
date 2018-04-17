/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andiazher.contability.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author andre
 */
public class ConnectionMysql {
    
    private Connection connection;
    private String host ="localhost"; //192.168.0.12
    private String port ="3306";
    private String db ="domicilios";
    private String user ="root"; //root or admin
    private String pass =""; //-- or root
    private String informationSchema="information_schema";

    public Connection getConnection() {
        
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void conectar(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        try {
            String qry= "jdbc:mysql://"+host+":"+port+"/"+db+"?zeroDateTimeBehavior=convertToNull"
                            + "&user="+user+"&password="+pass;
            this.connection = DriverManager.getConnection(qry);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }
    
    public boolean escribir(String sql) throws SQLException { 
        try { 
            Statement sentencia; 
            sentencia = getConnection().createStatement();
            sentencia.executeUpdate(sql);  
        } catch (SQLException e) { 
            System.out.print("ERROR SQL");
            e.printStackTrace();
            return false; 
        }        
        return true; 
    } 

public ResultSet consultar(String sql) throws SQLException { 
        ResultSet resultado = null; 
        PreparedStatement sentencia; 
        try { 
            sentencia = getConnection().prepareStatement(sql);
            resultado = sentencia.executeQuery(); 
        } catch (SQLException e) { 
            System.out.print("ERROR SQL: "+sql); 
            e.printStackTrace();
            return null; 
        }
       
        return resultado; 
    } 

    public void desconectar() throws SQLException {
        connection.close();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getInformationSchema() {
        return informationSchema;
    }

    public void setInformationSchema(String informationSchema) {
        this.informationSchema = informationSchema;
    }
    
    
}
