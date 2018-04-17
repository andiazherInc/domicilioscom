<%-- 
    Document   : footer
    Created on : 4/06/2017, 07:19:04 PM
    Author     : andre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    try{      
        if(!session.getAttribute("isSession").equals("true")){
            response.sendRedirect("../../login.jsp");
        }
    }
    catch(NullPointerException s){
        response.sendRedirect("../../login.jsp");
    }  
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FOOTER</title>
    </head>
    <body>
        <div style="text-shadow: 0px 0px 0px black;" class="text-right small "  id="copyright" >
            by <a href="http://andiazher.com/about?ref=Software+Accounting&V=1.0">andiazher Inc</a> &copy;   
        </div>
        
        
    </body>
    <script>
        function loadCopyri(){
            var date = new Date().getFullYear();
            $("#copyright").html("by <a href=\"http://andiazher.com/about?ref=Software+Accounting&V=1.0\" class=\"\">andiazher Inc</a> &copy; "+date+"    ");
        }
        loadCopyri();
    </script>
</html>
