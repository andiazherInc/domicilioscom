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
public class SaveUsers extends HttpServlet {

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
                String action= request.getParameter("action");
                if(action.equals("old")){
                    String user= request.getParameter("id");
                    try{
                        Entitie login= new Entitie(App.TABLE_LOGIN);
                        Entitie account = new Entitie(App.TABLE_USERS);
                        Entitie role = new Entitie(App.TABLE_ROLE);
                        
                        account.getEntitieId(user);
                        account.getDataMap().put("name", request.getParameter("nombre"));
                        account.getDataMap().put("lastname", request.getParameter("apellido"));
                        account.getDataMap().put("mail", request.getParameter("email"));
                        account.getDataMap().put("phone", request.getParameter("telefono"));
                        account.update();
                        
                        String loginId = account.getDataOfLabel("user").toString();
                        login.getEntitieId(loginId);
                        String roleId = login.getDataOfLabel("role").toString();
                        role.getEntitieId(roleId);
                        JSONA auxJson= account.getJson();
                        auxJson.add("usuario", login.getDataOfLabel("usuario").toString() );
                        auxJson.add("rol", role.getDataOfLabel("name").toString() );
                        auxJson.add("id",account.getId());
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
                
                if(action.equals("miperfil")){
                    
                    try{
                        String userid= request.getSession().getAttribute("userid").toString();
                        
                        Entitie login= new Entitie(App.TABLE_LOGIN);
                        Entitie account = new Entitie(App.TABLE_USERS);
                        Entitie role = new Entitie(App.TABLE_ROLE);
                        
                        ArrayList<Entitie> acounts= account.getEntitieParam("user", userid);
                        account= acounts.get(0);
                        
                        account.getDataMap().put("name", request.getParameter("nombre"));
                        account.getDataMap().put("lastname", request.getParameter("apellido"));
                        account.getDataMap().put("mail", request.getParameter("email"));
                        account.getDataMap().put("phone", request.getParameter("telefono"));
                        account.update();
                        
                        login.getEntitieId(userid);
                        String roleId = login.getDataOfLabel("role").toString();
                        role.getEntitieId(roleId);
                        JSONA auxJson= account.getJson();
                        auxJson.add("usuario", login.getDataOfLabel("usuario").toString() );
                        auxJson.add("rol", role.getDataOfLabel("name").toString() );
                        auxJson.add("id",account.getId());
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
                
                if(action.equals("new")){
                    try{
                        Entitie login= new Entitie(App.TABLE_LOGIN);
                        Entitie account = new Entitie(App.TABLE_USERS);
                        Entitie role = new Entitie(App.TABLE_ROLE);
                        
                        login.getDataMap().put("usuario", request.getParameter("usuario"));
                        login.getDataMap().put("pass", request.getParameter("usuario"));
                        login.getDataMap().put("role", request.getParameter("rol"));
                        login.getDataMap().put("backgroundcolor", "white");
                        login.create();
                        
                        ArrayList<Entitie> logins = login.getEntitieParam("usuario",request.getParameter("usuario"));
                        login = logins.get(0);
                        
                        account.getDataMap().put("name", request.getParameter("nombre"));
                        account.getDataMap().put("lastname", request.getParameter("apellido"));
                        account.getDataMap().put("mail", request.getParameter("email"));
                        account.getDataMap().put("phone", request.getParameter("telefono"));
                        account.getDataMap().put("user", login.getId());
                        
                        account.create();
                        
                        ArrayList<Entitie> accounts = account.getEntitieParam("user", login.getId());
                        account= accounts.get(0);
                        
                        String loginId = account.getDataOfLabel("user").toString();
                        login.getEntitieId(loginId);
                        String roleId = login.getDataOfLabel("role").toString();
                        role.getEntitieId(roleId);
                        JSONA auxJson= account.getJson();
                        auxJson.add("usuario", login.getDataOfLabel("usuario").toString() );
                        auxJson.add("rol", role.getDataOfLabel("name").toString() );
                        auxJson.add("id",account.getId());
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
                
                if(action.equals("delete")){
                    try{
                        String user= request.getParameter("id");
                        Entitie login= new Entitie(App.TABLE_LOGIN);
                        Entitie account = new Entitie(App.TABLE_USERS);
                        
                        account.getEntitieId(user);
                        String loginId = account.getDataOfLabel("user").toString();
                        login.getEntitieId(loginId);
                        account.delete();
                        login.delete();
                        try (PrintWriter out = response.getWriter()) {
                            JSONA j= new JSONA();
                            j.add("Eliminado", "Yes");
                            out.print(j);
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
            Logger.getLogger(SaveUsers.class.getName()).log(Level.SEVERE, null, ex);
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
