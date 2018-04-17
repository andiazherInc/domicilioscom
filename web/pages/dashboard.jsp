<%-- 
    Document   : dashboard
    Created on : 23/04/2017, 01:01:12 AM
    Author     : andre
--%>

<%@page import="com.andiazher.contability.app.App"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String q="";
    String q2="";
    String user="";
    String sessionId="";
    String key="";
    try{      
        if(!App.isSession(request,response)){
            response.sendRedirect("../login.jsp");
        }
        else{
            user = session.getAttribute("user").toString();
        }
    }catch(NullPointerException s){
        response.sendRedirect("../login.jsp");
    }catch(IllegalStateException s){ System.out.println("Error");}
    try{
        if(!request.getParameter("q").equals("null")){
            q = request.getParameter("q");
            q2 = "?q="+request.getParameter("q");
        }
    }catch(NullPointerException s){
    }
    try{
        sessionId = request.getParameter("sessionId");
        key= request.getParameter("key");
    }catch(NullPointerException s){}
    catch(IllegalStateException s){System.out.println("Error2");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard</title>
    </head>
    <body >
        <div class="">
        <div id="navbar">
            
        </div>
        <div class="col-sm-2 no-print" id="menu"> <!--hidden-xs-->
            
        </div>
        <div class="col-sm-10" id="contenido">
            
        </div>
        <div class="col-sm-12 footerDashboard" id="footer">
            
        </div>
        </div>
    </body>
    <script>
        
        function load(param){
            var idSession= param;
            if(idSession=="" || idSession =="null" || idSession==undefined){
                $.post("login.jsp", {}, function(data){
                    $("#contend").html(data);
                });
            }
            else{
                var q= "<%=q2%>";
                var s1= "<%=sessionId%>";
                var s2= "<%=key%>";
                $.post("pages/dashboard/navbar.jsp"+q, {sessionId: s1, key:s2}, function(data){
                    $("#navbar").html(data);
                });
                $( function() {
                    $.post("pages/dashboard/menu.jsp"+q, {sessionId: s1, key:s2}, function(data){
                        $("#menu").html(data);
                    });
                } );
                $( function() {
                    $.post("pages/dashboard/contend.jsp"+q, {sessionId: s1, key:s2}, function(data){
                        $("#contenido").html(data);
                    });
                } );
                $.post("pages/dashboard/footer.jsp"+q, {sessionId: s1, key:s2}, function(data){
                    $("#footer").html(data);
                });
            }
        }
        function loadTwo(){
            var isSession="isSession";
            $.post("loginApp",{param:isSession}, function(data){
                var v = JSON.parse(data);
                var idSession = v.isSession;
                if(idSession=="" || idSession =="null" || idSession==undefined){
                    $.post("login.jsp", {}, function(data){
                        $("#contend").html(data);
                    });
                }
                else{
                    loadMenus();
                }
            });
            
        }
        function search(data){
            try{
                var v = JSON.parse(data);
                setTitleContend("Resultados de busqueda para \"<b>"+v.q+"</b>\"");
                setContendToMenu("<a href=\"?q="+v.q+"&search=1\" >Busqueda avanzada</a>");
                if(v.number=="0"){
                    setContendToContend("<h4>No se han encontrado resultado para <b>\""+v.q+"\"</b>.<h4>")
                }else{

                }    
            }catch(e){
                load("");
            }
            
        }
        load(<%=session.getAttribute("isSession")%>);
        
    </script>
    <style>
        .footerDashboard {
            position: fixed;
            bottom: 0;
            right: 0;
            background: white;
          }
    </style>
    <style>
        @media print
        {
            .no-print
            {
                display: none !important;
                height: 0;
            }


            .no-print, .no-print *{
                display: none !important;
                height: 0;
            }
        }
       
    </style>
</html>
