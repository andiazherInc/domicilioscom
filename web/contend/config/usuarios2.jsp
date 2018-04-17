<%@page import="com.andiazher.contability.app.App"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String sessionId="";
    String key="";
    try{      
        if(!App.isSession(request, response)){
            response.sendRedirect("../../login.jsp");
        }
    }
    catch(NullPointerException s){
        response.sendRedirect("../../login.jsp");
    }  
    try{
        sessionId = request.getParameter("sessionId");
        key= request.getParameter("key");
    }
    catch(NullPointerException s){}
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Usuarios</title>
    </head>
    <body>
        <h4 class="text-center">Usuarios Registrados en el sistema</h4>
        <br>
            <table class="table table-bordered table-striped table-hover table-condensed" id="tableAccounts">
                <thead>
                    <tr>
                        <th  class="text-center">ID</th>
                        <th  class="text-center">ROL</th>
                        <th  class="text-center">USUARIO</th>
                        <th  class="text-center">NOMBRE</th>
                        <th  class="text-center">EMAIL</th>
                        <th  class="text-center"></th>
                    </tr>
                </thead>
                <tbody id="tableBody">
                    <!--HERE DATA ACCOUNTS -->
                </tbody>
            </table>
    </body>
    <script>
        function loadAccounts(){
            var s1= "<%=sessionId%>";
            var s2= "<%=key%>";
            var s3= "customerUsers";
            $.post("adminAccounts", {sessionId: s1, key:s2, type:s3}, function(data){
                var v = JSON.parse(data);
                $("#tableBody").html("");
                var isfirst=true;
                if(v.error!=""){
                    for(i in v){
                        row= v[i];
                        $("#tableBody").append(" <tr>\n\
                            <td class=\"text-center\">"+row.id+"</td>\n\
                            <td class=\"text-center\">"+row.rol+"</td>\n\
                            <td style=\"table-layout:fixed;\">"+row.usuario+"</td>\n\
                            <td style=\"table-layout:fixed;\">"+row.name+" "+row.lastname+"</td>\n\
                            <td style=\"table-layout:fixed;\">"+row.mail+"</td>\n\
                            <td></td>\n\
                            "
                            );
                    }
                }else{
                  $("#tableBody").append("Error. No loads data accounts, please comunicate with administrator");
                }
            });
        }
        loadAccounts();
    </script>
</html>
