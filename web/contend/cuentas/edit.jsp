<%-- 
    Document   : edit
    Created on : Jul 30, 2017, 7:48:50 PM
    Author     : diaan07
--%>

<%@page import="com.andiazher.contability.app.App"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String sessionId = "";
    String key = "";
    String id ="0";
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
        id = request.getParameter("id");
    }
    catch(NullPointerException s){}
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MOVIMIENTOS</title>
    </head>
    <body>
        <div>
            <h4 id="nameaccount"><b>CUENTA:</b> NaN</h4>
            <table class="table table-bordered table-striped table-hover table-condensed" id="tableAccounts">
                <thead>
                    <tr>
                        <th  class="text-center">ID</th>
                        <th  class="text-center">FECHA</th>
                        <th  class="text-center">T</th>
                        <th  class="text-center">VALOR</th>
                        <th  class="text-center">AP</th>
                        <th  class="text-center">DESCRIP</th>
                    </tr>
                </thead>
                <tbody id="tableBody">
                    <!--HERE DATA ACCOUNTS -->
                </tbody>
            </table>
            <p class="text-right " ><b>Número de mov.: </b> <m id="nmovimientos"> 0 </m>  </p>
            <p><b>RESUMEN DE CUENTA: </b></p>
            <table class="table table-condensed">
                <tr>
                    <td>INGRESOS: </td>
                    <td id="ingresosvalor" class="text-right">--</td>
                </tr>
                <tr>
                    <td>EGRESOS: </td>
                    <td id="egresosvalor" class="text-right" style="color:red;">--</td>
                </tr>
                <tr>
                    <td><b>TOTAL CUENTA: </b></td>
                    <td id="totalvalor" class="text-right">--</td>
                </tr>
            </table>
        </div>
    </body>
    <script>
        function loadAccounts(){
            var s1= "<%=sessionId%>";
            var s2= "<%=key%>";
            var s3= "<%=id%>"
            $.post("dataMovents", {sessionId: s1, key:s2, id: s3}, function(data){
                var v = JSON.parse(data);
                $("#tableBody").html("");
                $("#nameaccount").html("");
                if(v.error!=""){
                    $("#nameaccount").html("<b>CUENTA:</b> "+v.name+" ");
                    var arrayMovements = v.movements;
                    var sumacuenta=0;
                    var nmov=0;
                    var sumingresos=0;
                    var sumegresos=0;
                    for(i in arrayMovements){
                        nmov++;
                        row= arrayMovements[i];
                        if(row.type=="+"){
                            sumacuenta+= parseInt(row.value);
                            sumingresos+= parseInt(row.value);
                        }else{
                            sumacuenta-= parseInt(row.value);
                            sumegresos+= parseInt(row.value);
                        }
                        $("#tableBody").append(" <tr>\n\
                            <td class=\"text-center\">"+row.id+"</td>\n\
                            <td class=\"text-center\">"+row.date+"</td>\n\
                            <td class=\"text-center\">"+row.type+"</td>\n\
                            <td class=\"text-right\">$ "+Intl.NumberFormat().format(row.value)+"</td>\n\
                            <td class=\"text-center\">"+row.aprobe+"</td>\n\
                            <td class=\"\">"+row.description+"</td>\n\
                            </tr>");
                    }
                    if(sumacuenta < 0){
                        $("#totalvalor").html("<b><m style=\"color:red;\"> $ "+Intl.NumberFormat().format(sumacuenta)+"</m></b>");
                    }else{
                        $("#totalvalor").html("<b> $ "+Intl.NumberFormat().format(sumacuenta)+"</b>");
                    }
                    $("#nmovimientos").html(Intl.NumberFormat().format(nmov));
                    $("#ingresosvalor").html(" $ "+Intl.NumberFormat().format(sumingresos));
                    $("#egresosvalor").html(" $ "+Intl.NumberFormat().format(sumegresos));
                }else{
                  $("#tableBody").append("Error. No loads data accounts, please comunicate with administrator");
                }
            });
        }
        loadAccounts();
    </script>
</html>
