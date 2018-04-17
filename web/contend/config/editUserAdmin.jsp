<%@page import="com.andiazher.contability.app.App"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String sessionId="";
    String key="";
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
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit User Admin</title>
    </head>
    <body>
        <form class="form-group" method="post" action="saveUser?sessionId=<%=sessionId%>&key=<%=key%>&action=old&id=<%=id%>" id="fo3">
            <h4 id="nameaccount"><b>Edicion de usuario:</b>
                <div class="pull-right btn-group">
                    <a href="#deleteAccount(<%=id%>)" onclick="deleteUser(<%=id%>);" class="btn bg-danger">Borrar</a>
                    <input type="submit" class=" btn btn-success" value="Guardar">
                </div>
            </h4>
            <br><p class="text-info" id="result"></p>
            <table class="table">
                <tr>
                    <td class="text-right">Usuario:</td>
                    <td><input type="text" name="usuario" id="usuario" class="form-control" required="" readonly=""></td>
                </tr>
                <tr>
                    <td class="text-right">Rol:</td>
                    <td><input type="text" name="rol" id="rol" class="form-control" required="" readonly=""></td>
                </tr>
                <tr>
                    <td class="text-right">Nombre*:</td>
                    <td><input type="text" name="nombre" id="nombre" class="form-control" required=""></td>
                </tr>
                <tr>
                    <td class="text-right">Apellido:*</td>
                    <td><input type="text" name="apellido" id="apellido" class="form-control" required=""></td>
                </tr>
                <tr>
                    <td class="text-right">Email:</td>
                    <td><input type="email" name="email" id="email" class="form-control"></td>
                </tr>
                <tr>
                    <td class="text-right">Telefono:</td>
                    <td><input type="text" name="telefono" id="telefono" class="form-control"></td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                </tr>
            </table>
        </form>
    </body>
    <script>
    function loadAccounts(){
            var s1= "<%=sessionId%>";
            var s2= "<%=key%>";
            var s3= "userEspecific";
            var s4= "<%=id%>"
            $.post("adminAccounts", {sessionId: s1, key:s2, type:s3, id:s4}, function(data){
                var v = JSON.parse(data);
                $("#usuario").val("");
                $("#rol").val("");
                $("#nombre").val("");
                $("#apellido").val("");
                $("#email").val("");
                $("#telefono").val("");
                var isfirst=true;
                if(v.error!=""){
                    $("#usuario").val(v.usuario);
                    $("#rol").val(v.rol);
                    $("#nombre").val(v.name);
                    $("#apellido").val(v.lastname);
                    $("#email").val(v.mail);
                    $("#telefono").val(v.phone);
                }else{
                  $("#result").append("Error. No loads data accounts, please comunicate with administrator");
                }
            });
        }
    loadAccounts();
    
    $('#fo3').submit(function() {
  // Enviamos el formulario usando AJAX
        $.ajax({
            type: 'POST',
            url: $(this).attr('action'),
            data: $(this).serialize(),
            // Mostramos un mensaje con la respuesta de PHP
            success: function(data) {
                var v = JSON.parse(data);
                if(v.error!=""){
                    loadContend("Edicion del usuario <b>["+v.usuario+"] "+v.name+" "+v.lastname+"</b> ","contend/config/editUserAdmin.jsp?id="+v.id+"");
                }
                else{
                    $('#result').html("Mensaje: "+data);
                }
            }
        })        
        return false;
    });
    </script>
</html>
