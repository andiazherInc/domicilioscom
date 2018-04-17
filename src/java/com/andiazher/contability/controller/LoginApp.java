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
import java.util.Calendar;
import java.util.GregorianCalendar;
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
public class LoginApp extends HttpServlet {

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
            String param1 = request.getParameter("param");
            if(param1.equals("login")){
                String user = request.getParameter("user");
                String pass = request.getParameter("pass");
                if(user.isEmpty() || pass.isEmpty()){
                    response.sendRedirect("login.jsp?error=Usuario o contraseña no pueden estas vacios");
                }
                else{
                    //CONSULT USER AND PASS IN BD
                    try{
                        Entitie login =new Entitie(App.TABLE_LOGIN);
                        login = login.getEntitieParam("usuario", user).get(0);
                        if(user.equals(login.getDataOfLabel("usuario")) && pass.equals(login.getDataOfLabel("pass"))){
                            Calendar fecha = new GregorianCalendar();
                            String f= fecha.get(Calendar.YEAR) +"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH)
                                    +" "+fecha.get(Calendar.HOUR_OF_DAY)+":"+fecha.get(Calendar.MINUTE)+":"+fecha.get(Calendar.SECOND);
                            System.out.println("The user "+user+" has login at "+ f);
                            request.getSession().setAttribute("isSession", "true");
                            request.getSession().setAttribute("user", user);
                            request.getSession().setAttribute("userid", login.getId());
                            request.getSession().setAttribute("role", login.getDataOfLabel("role"));
                            request.getSession().setAttribute("color", login.getDataOfLabel("backgroundcolor"));
                            request.getSession().setAttribute("image", login.getDataOfLabel("backgroundcolorimage"));
                            String oldSession = request.getSession().getId();
                            //request.changeSessionId();
                            request.getSession().setAttribute("key", oldSession);
                            response.sendRedirect("app?sessionId="+request.getSession().getId()+"&key="+oldSession);
                        }
                        else{
                            response.sendRedirect("login.jsp?error=Credenciales+invalidas");
                            request.getSession().invalidate();
                        }
                    }catch(IndexOutOfBoundsException s){
                        response.sendRedirect("login.jsp?error=Credenciales+invalidas");
                    }catch(NullPointerException s){
                        response.sendRedirect("login.jsp?error=Error: "+s.toString() +" "+s.getMessage());
                    }
                }
            }
            if(param1.equals("logout")){
                request.getSession().removeAttribute("isSession");
                response.sendRedirect("login.jsp?logout=true&newKey="+request.getSession().getId());
                request.getSession().invalidate();
                
            }
            if(param1.equals("isSession")){
                try (PrintWriter out = response.getWriter()) {
                    JSONA j= new JSONA();
                    j.add("isSession",(String) request.getSession().getAttribute("isSession")+"");
                    out.print(j);
                }                     
            }
        }
        catch(NullPointerException s){
            s.printStackTrace();
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
            Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
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
