<%-- 
    Document   : nabvar
    Created on : 4/06/2017, 07:18:28 PM
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
            response.sendRedirect("../../login.jsp");
        }
        else{
            user = session.getAttribute("user").toString();
        }
    }
    catch(NullPointerException s){
        response.sendRedirect("../../login.jsp");
    }
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
    }
    catch(NullPointerException s){}
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>NavBar</title>

    </head>
    <body>
        <nav class="navbar navbar-default" > <!--style="background: transparent;-->
                <div class="container-fluid">
                  <!-- Brand and toggle get grouped for better mobile display -->
                  <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                      <span class="sr-only">Toggle navigation</span>
                      <span class="icon-bar"></span>
                      <span class="icon-bar"></span>
                      <span class="icon-bar"></span>
                    </button>
                      <a class="navbar-brand" href="#reload" onclick="reloadAll()"><img src="pages/images/market.png" alt="Smiley face" height="32" width="130"></a>
                  </div>

                  <!-- Collect the nav links, forms, and other content for toggling -->
                  <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav" id="menusnavbar">
                        <!-- HERE CODE MENUS -->
                        <img src="pages/images/loading_spinner.gif" height="15" width="15">
                    </ul>
                      <form class="navbar-form navbar-left" action="search#search" method="get" id="search">
                      <div class="form-group">
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="Search for..." name="q" value="<%=q%>" required>
                            <input type="hidden" name="sessionId" value="<%=sessionId%>">
                            <input type="hidden" name="key" value="<%=key%>">
                            <span class="input-group-btn">
                              <button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
                            </span>
                          </div><!-- /input-group -->
                      </div>
                    </form>
                    <ul class="nav navbar-nav navbar-right">
                      <!--<li><a href="#"></a></li> -->
                      <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" id="username"><%=user%><span class="caret"></span></a>
                        <ul class="dropdown-menu">
                          <li><a href="#profile">Profile</a></li>
                          <li><a href="#editProfile">Edit Profile</a></li>
                          <li role="separator" class="divider"></li>
                          <li><a href="#logoutUser" onclick="logout()">Logout</a></li>
                        </ul>
                      </li>
                    </ul>
                  </div>
                </div>
              </nav>
    </body>
    <script>
        function logout() {
            var para="logout";
            $.post("loginApp", {param: para}, function(data){
                $("#contend").html(data);
            });
        }
        function reloadAll(){
            loadTwo();
        }
        function laodName(){
            var username= "<%=user%>";
            var s1= "<%=sessionId%>";
            var s2= "<%=key%>";
            $.post("loginParamters", {user: username, sessionId: s1, key:s2 }, function(data){
                if(data!=0){
                    $("#username").html(data +"<span class=\"caret\">");
                }
            });
        }
        laodName();
        function changeActiveNavbar(rolnavid, id){
            $(".active").removeClass("active");
            var s1= "<%=sessionId%>";
            var s2= "<%=key%>";
            $.post("menuActive", {navbar: rolnavid, sessionId: s1, key:s2}, function(data){
                $("#navid"+id).addClass("active");
            });
        }
        function loadNavbar(){
            var s1= "<%=sessionId%>";
            var s2= "<%=key%>";
            $.post("navBarContent", {sessionId: s1, key:s2}, function(data){
                var v = JSON.parse(data);
                $("#menusnavbar").html("");
                if(v.error!="0"){
                    for(i in v){
                        menu= v[i];
                        
                        if(menu.isdropdown=="1"){
                          var ls="";
                          ls+= "<li class=\"dropdown "+menu.active+" \" id=\"navid"+menu.id+"\" ><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">"+menu.name+"<span class=\"caret\"></span></a>";  
                          ls+="<ul class=\"dropdown-menu\">";
                          for(n in menu.steps){
                            step = menu.steps[n];
                            if(step.id!="0"){
                              if(step.isurlormethod=="1"){
                                ls+="<li><a href=\""+step.urlormethod+"\" target=\"_blank\">"+step.name+"</a></li>";
                              }else{
                                ls+="<li><a href=\"#"+step.name+"\" onclick=\""+step.urlormethod+"\">"+step.name+"</a></li>";
                                //ls+="<script>"+step.urlormethod+"<\/script>";
                              }
                            }else{
                              ls+="<li role=\"separator\" class=\"divider\"></li>";
                            }
                          }
                          ls+="</ul>";
                          ls+="</li>";
                          $("#menusnavbar").append(ls);
                        }
                        else{
                          if (menu.isurlormethod=="1") {
                            $("#menusnavbar").append("<li class=\""+menu.active+"\" id=\"navid"+menu.id+"\"><a href=\""+menu.urlormethod+"\" target=\"_blank\">"+menu.name+"</a></li>");
                          }else{
                            $("#menusnavbar").append("<li class=\""+menu.active+"\" id=\"navid"+menu.id+"\"><a href=\"#"+menu.name+"\" onclick=\"changeActiveNavbar("+menu.rolnavbid+", "+menu.id+"); "+menu.urlormethod+"\">"+menu.name+"</a></li>");
                            $("#menusnavbar").append("<script>"+menu.urlormethod+"<\/script>");
                          }
                        }
                    }
                }
                else{
                  $("#contentMenu").append("Error. No loads navbars ");
                  loadContend("Error", "pages/dashboard/error.jsp");
                }
            });
        }
        loadNavbar(); 
    $(document).ready(function(){
      $("#search").submit(function(){
          $.ajax({
                    type: 'POST',
                    url: $(this).attr('action'),
                    data: $(this).serialize(),
                    success: function(data){
                      search(data);
                  }
                })
            return false;
        });
    });
    </script>
    
</html>

