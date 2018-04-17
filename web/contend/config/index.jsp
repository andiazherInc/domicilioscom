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
    try{
        sessionId = request.getParameter("sessionId");
        key= request.getParameter("key");
    }
    catch(NullPointerException s){}
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CONFIGURACIONES</title>
    </head>
    <body>
        <h4 class="text-center">Usuarios Registrados en el sistema <a class="pull-right btn btn-success" onclick="loadContend('Crear usuario','contend/config/newUserAdmin.jsp');">Crear usuario</a></h4>
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
            var s3= "allUsers";
            $.post("adminAccounts", {sessionId: s1, key:s2, type:s3}, function(data){
                var v = JSON.parse(data);
                $("#tableBody").html("");
                var isfirst=true;
                if(v.error!=""){
                    for(i in v){
                        row= v[i];
                        $("#tableBody").append(" <tr>\n\
                            <td class=\"text-center\"><a href=\"#openAccount("+row.id+")\" onclick=\"loadContend('Edicion del usuario <b>["+row.usuario+"] "+row.name+" "+row.lastname+"</b> ','contend/config/editUserAdmin.jsp?id="+row.id+"');\">"+row.id+"</a></td>\n\
                            <td style=\"table-layout:fixed;\">"+row.rol+"</td>\n\
                            <td style=\"table-layout:fixed;\">"+row.usuario+"</td>\n\
                            <td style=\"table-layout:fixed;\">"+row.name+" "+row.lastname+"</td>\n\
                            <td style=\"table-layout:fixed;\">"+row.mail+"</td>\n\
                            <td style=\"table-layout:fixed;\">\n\
                                <a href=\"#openAccount("+row.id+")\" onclick=\"loadContend('Edicion del usuario <b>["+row.usuario+"] "+row.name+" "+row.lastname+"</b> ','contend/config/editUserAdmin.jsp?id="+row.id+"');\"><span class=\"glyphicon glyphicon-edit\" aria-hidden=\"true\"></span></a>\n\
                                <a href=\"#deleteAccount("+row.id+")\" onclick=\"deleteUser("+row.id+");\"><span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span></a>\n\
                            </td>\n\ "
                            );
                    }
                }else{
                  $("#tableBody").append("Error. No loads data accounts, please comunicate with administrator");
                }
            });
        }
        loadAccounts();
        function deleteUser(id){
            if(confirm('¿Está seguro de eliminar el usuario?')){
                var s1= "<%=sessionId%>";
                var s2= "<%=key%>";
                var s3= "delete";
                var s4= id;
                $.post("saveUser", {sessionId: s1, key:s2, action:s3, id:s4}, function(data){
                    var v = JSON.parse(data);
                    var isfirst=true;
                    if(v.error!=""){
                        loadContend("Información de todos los usuarios del sistema","contend/config/index.jsp");
                    }else{
                      $('#result').html("Mensaje: "+data);
                    }
                });
            }
            
        }
    </script>
</html>
