/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andiazher.contability.controller;

import com.andiazher.contability.app.App;
import com.andiazher.contability.entitie.Entitie;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class LoginParamters extends HttpServlet {

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
            if(App.isSession(request, response)){
                String username="";
                try{
                    username= request.getParameter("user");
                    Entitie login = new Entitie(App.TABLE_LOGIN);
                    login= login.getEntitieParam("usuario", username).get(0);
                    Entitie user = new Entitie(App.TABLE_USERS);
                    user = user.getEntitieParam("user", login.getId()).get(0);
                    try (PrintWriter out = response.getWriter()) {
                        if(user.getDataOfLabel("name").equals("") && user.getDataOfLabel("lastname").equals("") && 
                                user.getDataOfLabel("name").equals(" ") && user.getDataOfLabel("lastname").equals(" ")){
                            out.println("0");
                        }
                        else{
                            out.println(user.getDataOfLabel("name")+" "+user.getDataOfLabel("lastname"));
                        }
                    } 
                }catch(NullPointerException | IndexOutOfBoundsException e){
                    try (PrintWriter out = response.getWriter()) {
                        out.println("0");
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
            Logger.getLogger(LoginParamters.class.getName()).log(Level.SEVERE, null, ex);
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
