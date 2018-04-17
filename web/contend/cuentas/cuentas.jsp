<%-- 
    Document   : edit
    Created on : Jul 22, 2017, 3:10:51 PM
    Author     : diaan07
--%>

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
        <title>EDIT ACCOUNTS</title>
    </head>
    <body>
        <div class="">
            <h3 class="text-center">CUENTAS</h3>
            <table class="table table-bordered table-striped table-hover table-condensed" id="tableAccounts">
                <thead>
                    <tr>
                        <th  class="text-center">ID</th>
                        <th  class="text-center">ACCOUNT</th>
                        <th  class="text-center">OWNER</th>
                        <th  class="text-center">TOTAL</th>
                    </tr>
                </thead>
                <tbody id="tableBody">
                    <!--HERE DATA ACCOUNTS -->
                </tbody>
            </table>
        </div>
    </body>
    <script>
        function loadAccounts(){
            var s1= "<%=sessionId%>";
            var s2= "<%=key%>";
            var s3= "allUsers";
            $.post("dataAccounts", {sessionId: s1, key:s2, type: s3}, function(data){
                var v = JSON.parse(data);
                $("#tableBody").html("");
                var isfirst=true;
                if(v.error!=""){
                    for(i in v){
                        row= v[i];
                        var valorsaldohtml="<td class=\"text-right\"> $ "+Intl.NumberFormat().format(row.saldo)+"</td>";
                        if(row.saldo<0){
                            valorsaldohtml="<td class=\"text-right\" style=\"color:red;\"> $ "+Intl.NumberFormat().format(row.saldo)+"</td>";
                        }
                        $("#tableBody").append(" <tr>\n\
                            <td class=\"text-center\"><a href=\"#openAccount("+row.number+")\" onclick=\"loadContend('Movimientos de la cuenta <b>"+row.number+" - "+row.name+"</b> ','contend/cuentas/edit.jsp?id="+row.id+"');\">"+row.number+"</a></td>\n\
                            <td style=\"table-layout:fixed;\">"+row.name+"</td>\n\
                            <td style=\"table-layout:fixed;\">"+row.owner+"</td>\n\
                            "+valorsaldohtml);
                    }
                }else{
                  $("#tableBody").append("Error. No loads data accounts, please comunicate with administrator");
                }
            });
        }
        loadAccounts();
    </script>
</html>
