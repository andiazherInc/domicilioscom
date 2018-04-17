/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andiazher.contability.controller.parameters;

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
 * @author diaan07
 */
public class MenusContent extends HttpServlet {

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
                    
                    JSONA menus= new JSONA();
                    Entitie menu = new Entitie(App.TABLE_MENUS);
                    String rolnavid= request.getSession().getAttribute("rolnavid").toString();
                    ArrayList<Entitie> menuss= menu.getEntitieParam("navbarrole", rolnavid);
                    int contador=0;
                    for(Entitie m: menuss){
                        contador++;
                        menus.add(m.getId(), m.getJson());
                    }
                    if(contador==0){
                        menus.add("error", "empty");
                    }
                    try (PrintWriter out = response.getWriter()) {
                        out.println(menus);
                    }
                    
                }catch(NullPointerException s){
                    try (PrintWriter out = response.getWriter()) {
                        JSONA j= new JSONA();
                        j.add("error", "0");
                        out.print(j);
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
            Logger.getLogger(MenusContent.class.getName()).log(Level.SEVERE, null, ex);
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
