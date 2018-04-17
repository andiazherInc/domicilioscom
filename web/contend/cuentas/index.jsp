<%-- 
    Document   : index
    Created on : Jul 21, 2017, 12:56:06 AM
    Author     : diaan07
--%>

<%@page import="com.andiazher.contability.app.App"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    catch(IllegalStateException s){}
    try{
        sessionId = request.getParameter("sessionId");
        key= request.getParameter("key");
    }
    catch(NullPointerException s){}
    catch(IllegalStateException s){}
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DASHBOARD: ESTADO DE CUENTAS</title>    
    </head>
    
    <body>        
        <h3 class="text-center">ESTADO DE CUENTAS</h3>
        <div class="col-md-6">
            <div class=" panel panel-success">
                <div class="panel-body">
                    <div class="ct-chart ct-perfect-fourth"></div>
                </div>
                <div class="panel-footer"><small>Comportamientos de saldos en el año</small></div>
            </div>
        </div>
        <div class="col-md-6">
            <h3 class="text-center">SALDOS DE CUENTAS</h3>
            <table class="table table-bordered table-striped table-hover table-condensed" id="tableAccounts">
                <thead>
                    <tr>
                        <th  class="text-center">ID</th>
                        <th  class="text-center">ACCOUNT</th>
                        <th  class="text-center">TOTAL</th>
                    </tr>
                </thead>
                <tbody id="tableBody">
                    <!--HERE DATA ACCOUNTS -->
                </tbody>
            </table>
        </div>
        <div class="col-md-6">
            <div class=" panel panel-success">
                <div class="panel-body">
                    <div class="ct-chart2 ct-perfect-fourth"></div>
                </div>
                <div class="panel-footer"><small>Saldo de cuentas</small></div>
            </div>
        </div>
    </body>    
    <script>
        var data = {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
              series: [
              [5, 4, 3, 7, 5, 10, 3, 4, 8, 10, 6, 8],
              [3, 2, 9, 5, 4, 6, 4, 6, 7, 8, 7, 4]
            ]
          };

          // Create a new line chart object where as first parameter we pass in a selector
          // that is resolving to our chart container element. The Second parameter
          // is the actual data object.
          new Chartist.Line('.ct-chart', data);
    </script>
    <script>
        var data = {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
              series: [
              [5, 4, 3, 7, 5, 10, 3, 4, 8, 10, 6, 8],
              [3, 2, 9, 5, 4, 6, 4, 6, 7, 8, 7, 4]
            ]
          };

          // Create a new line chart object where as first parameter we pass in a selector
          // that is resolving to our chart container element. The Second parameter
          // is the actual data object.
          new Chartist.Line('.ct-chart2', data);
    </script>
    <script>
        function loadAccounts(){
            var s1= "<%=sessionId%>";
            var s2= "<%=key%>";
            $.post("dataAccounts", {sessionId: s1, key:s2}, function(data){
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
                            <td class=\"text-center\"><a href=\"#openAccount("+row.number+")\" onclick=\"loadContend('Movimientos de la cuenta <b>"+row.number+" - "+row.name+"</b>','contend/cuentas/edit.jsp?id="+row.id+"');\">"+row.number+"</a></td>\n\
                            <td style=\"table-layout:fixed;\">"+row.name+"</td> "+valorsaldohtml+" \n\
                            </tr>");
                    }
                }else{
                  $("#tableBody").append("Error. No loads data accounts, please comunicate with administrator");
                }
            });
        }
        loadAccounts();
    </script>
</html>
