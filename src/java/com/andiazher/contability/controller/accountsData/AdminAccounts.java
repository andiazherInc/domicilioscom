/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andiazher.contability.controller.accountsData;

import com.andiazher.contability.app.App;
import com.andiazher.contability.controller.JSONA;
import com.andiazher.contability.entitie.Entitie;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author andre
 */
public class AdminAccounts extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            if(App.isSession(request,response)){
                String type= request.getParameter("type");
                if(type.equals("allUsers")){
                    try{
                        Entitie login= new Entitie(App.TABLE_LOGIN);
                        Entitie account = new Entitie(App.TABLE_USERS);
                        Entitie role = new Entitie(App.TABLE_ROLE);
                        ArrayList<Entitie> accounts = account.getEntities();
                        JSONA jaccounts = new JSONA();
                        for(Entitie e: accounts){
                            JSONA auxJson= e.getJson();
                            String loginId = e.getDataOfLabel("user").toString();
                            login.getEntitieId(loginId);
                            String roleId = login.getDataOfLabel("role").toString();
                            role.getEntitieId(roleId);
                            auxJson.add("usuario", login.getDataOfLabel("usuario").toString() );
                            auxJson.add("rol", role.getDataOfLabel("name").toString() );
                            jaccounts.add(e.getId(), auxJson);
                        }

                        try (PrintWriter out = response.getWriter()) {
                            out.print(jaccounts);
                        }                     

                    }catch(NullPointerException s){
                        s.printStackTrace();
                        try (PrintWriter out = response.getWriter()) {
                            JSONA j= new JSONA();
                            j.add("error", "Error: "+s);
                            out.print(j);
                        }                     
                    }
                }
                if(type.equals("customerUsers")){
                    try{
                        Entitie login= new Entitie(App.TABLE_LOGIN);
                        Entitie account = new Entitie(App.TABLE_USERS);
                        Entitie role = new Entitie(App.TABLE_ROLE);
                        ArrayList<Entitie> accounts = account.getEntities();
                        JSONA jaccounts = new JSONA();
                        for(Entitie e: accounts){
                            JSONA auxJson= e.getJson();
                            String loginId = e.getDataOfLabel("user").toString();
                            login.getEntitieId(loginId);
                            String roleId = login.getDataOfLabel("role").toString();
                            role.getEntitieId(roleId);
                            auxJson.add("usuario", login.getDataOfLabel("usuario").toString() );
                            auxJson.add("rol", role.getDataOfLabel("name").toString() );
                            jaccounts.add(e.getId(), auxJson);
                        }

                        try (PrintWriter out = response.getWriter()) {
                            out.print(jaccounts);
                        }                     

                    }catch(NullPointerException s){
                        s.printStackTrace();
                        try (PrintWriter out = response.getWriter()) {
                            JSONA j= new JSONA();
                            j.add("error", "Error: "+s);
                            out.print(j);
                        }                     
                    }
                }
                if(type.equals("userEspecific")){
                    String user= request.getParameter("id");
                    try{
                        Entitie login= new Entitie(App.TABLE_LOGIN);
                        Entitie account = new Entitie(App.TABLE_USERS);
                        Entitie role = new Entitie(App.TABLE_ROLE);
                        account.getEntitieId(user);
                        String loginId = account.getDataOfLabel("user").toString();
                        login.getEntitieId(loginId);
                        String roleId = login.getDataOfLabel("role").toString();
                        role.getEntitieId(roleId);
                        JSONA auxJson= account.getJson();
                        auxJson.add("usuario", login.getDataOfLabel("usuario").toString() );
                        auxJson.add("rol", role.getDataOfLabel("name").toString() );
                        try (PrintWriter out = response.getWriter()) {
                            out.print(auxJson);
                        }                     

                    }catch(NullPointerException s){
                        s.printStackTrace();
                        try (PrintWriter out = response.getWriter()) {
                            JSONA j= new JSONA();
                            j.add("error", "Error: "+s);
                            out.print(j);
                        }                     
                    }
                }
                if(type.equals("miperfil")){
                    
                    try{
                        String userid= request.getSession().getAttribute("userid").toString();
                        
                        Entitie login= new Entitie(App.TABLE_LOGIN);
                        Entitie account = new Entitie(App.TABLE_USERS);
                        Entitie role = new Entitie(App.TABLE_ROLE);
                        
                        ArrayList<Entitie> acounts= account.getEntitieParam("user", userid);
                        account= acounts.get(0);
                        login.getEntitieId(userid);
                        String roleId = login.getDataOfLabel("role").toString();
                        role.getEntitieId(roleId);
                        JSONA auxJson= account.getJson();
                        auxJson.add("usuario", login.getDataOfLabel("usuario").toString() );
                        auxJson.add("rol", role.getDataOfLabel("name").toString() );
                        try (PrintWriter out = response.getWriter()) {
                            out.print(auxJson);
                        }                     

                    }catch(NullPointerException s){
                        s.printStackTrace();
                        try (PrintWriter out = response.getWriter()) {
                            JSONA j= new JSONA();
                            j.add("error", "Error: "+s);
                            out.print(j);
                        }                     
                    }
                }
            }
        }catch(NullPointerException s){
            response.sendRedirect("login.jsp?error=Credenciales+invalidas");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AdminAccounts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
