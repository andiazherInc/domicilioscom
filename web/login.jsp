<%-- 
    Document   : login.jsp
    Created on : 22/04/2017, 06:14:53 PM
    Author     : andre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String name="";
    try{      
        if(session.getAttribute("isSession").equals("true")){
            //response.sendRedirect("app");
        }
    }
    catch(NullPointerException s){ 
    }
    try{
        if(!request.getParameter("error").equals("null")){
            name =request.getParameter("error");
        }
    }catch(NullPointerException s){ 
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" type="image/png" href="pages/images/favicon.ico" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
	<meta name="viewport" content="width=device-width" />
        <link rel="stylesheet" href="pages/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link rel="stylesheet" href="pages/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <script src="pages/js/jquery-3.2.1.js"></script>
        <script src="pages/js/jquery-ui.js"></script>
        <!--<script src="pages/js/jquery.mobile.custom.js"></script> -->
        <script src="pages/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        
        <title>Login | SCP | by andiazher Inc</title>
    </head>
    <body>
        <div class="">
            <nav class="navbar navbar-primary navbar-static-top" style="background: transparent; color: white; text-shadow: 2px 2px 1px black;">
                <h3 class="col-md-offset-1 col-sm-offset-2"> Sistema de Andres Diaz</h3>
            </nav>
        </div>
        <div class="col-lg-3 col-md-4 col-sm-6 col-lg-offset-8 col-md-offset-7 col-sm-offset-5 setTopToFloorScreenDiv">
            <div class="panel " style="background: transparent;">
                <div class="panel-headingt text-center" style="text-shadow: 2px 2px 2px black; color: white;">
                    <br>
                    <b>Login with your Account</b>
                </div>
                <div class="panel-body ">
                    <p class="text-center text-danger" id="error" style="text-shadow: 2px 2px 2px black; color: white;"><%=name%></p>
                    <form action="loginApp?action=execute&login=true#id=266HSH26A72B126ZHASV2GMLOIU312HG12" id="loginForm" method="post">
                        <input type="hidden" value="login" name="param">
                        <div class="form-group">
                            <!--<label for="exampleInputEmail1">Username</label>-->
                            <input type="text" class="form-control" id="exampleInputEmail1"  name="user" placeholder="Username" required>
                        </div>
                        <div class="form-group">
                            <!--<label for="exampleInputPassword1">Password</label>-->
                            <input type="password" class="form-control" id="exampleInputPassword1" name="pass" placeholder="Password" required>
                            <input type="hidden" name="key" value="<%=session.getId()%>">
                        </div>
                        <div class="text-center">
                            <button type="submit" class="btn  btn-block" onclick="validate()">Access</button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="text-center" style="text-shadow: 2px 2px 2px black; color: white;">
                <p class="" style="color: white;">by andiazher Inc</p>
            </div>
        </div>
        
    </body>
    <style>
        body {
            width:100%;
            height:100%;
            background: url("pages/images/font1.jpg") no-repeat center center fixed;
            background-color: #2F2D2D;
            background-repeat: no-repeat;
            background-size: cover;
           -moz-background-size: cover;
           -webkit-background-size: cover;
           -o-background-size: cover;
        }
        .setTopToFloorScreenDiv{
            height:100%;
            background-color: black;
            opacity: 0.8;
            top:0px;
        }
        .navbar{
            background: white;
        }

        
    </style>
</html>
