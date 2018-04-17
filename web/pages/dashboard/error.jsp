<%-- 
    Document   : error
    Created on : Jul 23, 2017, 11:58:58 PM
    Author     : diaan07
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <div class="alert alert-danger" role="alert">
            <h3><span class="glyphicon glyphicon-alert" aria-hidden="true"></span> Error: </h3>
            <p>Ha ocurrido un error mientras se cargaba el contenido, si el error persiste, por favor cominicarse con el administrador.</p>
        </div>
        <p><a onclick="reloadAll()" href="#realod">Reload here</a></p>
        <p><a onclick="logout()" href="#logout">Cambiar de usuario</a></p>
    </body>
</html>
