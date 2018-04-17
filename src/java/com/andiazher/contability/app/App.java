/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andiazher.contability.app;

import com.andiazher.contability.db.ConnectionMysql;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author andre
 */
public class App {
    
    public final static String versionCompilation="V1.03";
    private static ConnectionMysql connectionMysql = new ConnectionMysql();
    
    //TABLES
    public static String TABLE_LOGIN = "andiazher_login";
    public static String TABLE_USERS = "config_usuarios";
    public static String TABLE_NAVBAR = "config_navbar";
    public static String TABLE_NAVBARSTEP = "config_navbarsteps";
    public static String TABLE_ROLE = "config_role";
    public static String TABLE_ROLENAV = "config_rolenavbars";
    public static String TABLE_MENUS = "config_menu";
    
    //DATA TABLE APP
    public static String TABLE_ACCOUNTS = "data_accounts";
    public static String TABLE_MOVIMIENTS = "data_movimientos";
    
    public static ResultSet consult(String sql) throws SQLException{
        if(connectionMysql.getConnection()==null){
            connectionMysql.conectar();
        }
        sql+=" LIMIT 100";
        ResultSet resultSet = connectionMysql.consultar(sql);
        //System.out.println("SQL: "+sql);
        //connectionMysql.desconectar();
        return resultSet;
    }
    public static boolean update(String sql) throws SQLException{
        if(connectionMysql.getConnection()==null){
            connectionMysql.conectar();
        }
        System.out.println("SQL: "+sql);
        return connectionMysql.escribir(sql);
    }

    public static ConnectionMysql getConnectionMysql() {
        return connectionMysql;
    }

    public static void setConnectionMysql(ConnectionMysql connectionMysql) {
        App.connectionMysql = connectionMysql;
    }
    
    public static String getVersionApp(){
        return versionCompilation;
    }
    
    public static boolean isSession(HttpServletRequest request, HttpServletResponse response) throws IOException{
        try{
            if(request.getSession().getAttribute("isSession").equals("true")){
                String idSession=request.getParameter("sessionId");
                String key=request.getParameter("key");
                if(idSession.equals(request.getSession().getId()) && key.equals(request.getSession().getAttribute("key"))){
                    return  true;
                }else{
                    request.getSession().removeAttribute("isSession");
                    request.getSession().invalidate();
                    response.sendRedirect("login.jsp?error=THE SESSION IS NO VALIDE. ID="+request.getSession().getId());
                    return  false;
                }
            }
            else{
                request.getSession().invalidate();
                response.sendRedirect("login.jsp?error=THE SESSION IS NO VALIDE. ID="+request.getSession().getId());
                return  false;
            }
        }catch(NullPointerException s){
            response.sendRedirect("login.jsp?error=THE SESSION IS NO VALIDE. ID="+request.getSession().getId());
        }catch(IllegalStateException s){
            System.out.println("Error here "+s);
        }
        return false;
    }
    
}
