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
public class DataMovents extends HttpServlet {

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
               try{
                   String param1 = request.getParameter("id");
                   Entitie movement= new Entitie(App.TABLE_MOVIMIENTS);
                   Entitie account = new Entitie(App.TABLE_ACCOUNTS);
                   account.getEntitieId(param1);
                   JSONA jmovements = new JSONA();
                   JSONA jmovements2 = new JSONA();
                    ArrayList<Entitie> movements = movement.getEntitieParam("account", param1);
                    for(Entitie a: movements){
                        jmovements.add(a.getId(), a.getJson());
                    }
                    jmovements2.add("movements", jmovements);
                    jmovements2.add("name", " ["+account.getDataOfLabel("number")+"] "+account.getDataOfLabel("name") );
                    try (PrintWriter out = response.getWriter()) {
                        out.print(jmovements2);
                    }
                    
               }catch(NullPointerException s){
                   try (PrintWriter out = response.getWriter()) {
                        JSONA j= new JSONA();
                        j.add("error", "Error: "+s);
                        out.print(j);
                    }  
               }
           } 
        } catch(NullPointerException s){
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
            Logger.getLogger(DataMovents.class.getName()).log(Level.SEVERE, null, ex);
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
