<%-- 
    Document   : app.jsp
    Created on : 22/04/2017, 05:20:40 PM
    Author     : andre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String q="";
    String color="";
    String image="";
    String sessionId="";
    String key="";
    try{
        if(!request.getParameter("q").equals("null")){
            q = "?q="+request.getParameter("q");
        }
        
    }catch(NullPointerException s){}
    try{
        color= session.getAttribute("color").toString();
        image= session.getAttribute("image").toString();
    }
    catch(NullPointerException s){}
    catch(IllegalStateException s){}
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
        
        <link rel="icon" type="image/png" href="pages/images/favicon.ico" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
	<meta name="viewport" content="width=device-width" />
        
        
        <link rel="stylesheet" href="pages/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link rel="stylesheet" href="pages/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <script src="pages/js/jquery-3.2.1.js"></script>
        <script src="pages/js/jquery-ui.js"></script>
        <script src="pages/js/chartist.js"></script>
        <script src="pages/js/chartist.min.js"></script>
        
        <!--<script src="pages/js/jquery.mobile.custom.js"></script> -->
        <script src="pages/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="pages/css/font-awesome.min.css">
        <link rel="stylesheet" href="pages/css/chartist.css">
        <link rel="stylesheet" href="pages/css/chartist.min.css">
        <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons" />
        <!--
        <script type="text/javascript" id="ca_eum_ba" agent=browser src="https://cloud.ca.com/mdo/v1/sdks/browser/BA.js" data-profileUrl="https://collector-axa.cloud.ca.com/api/1/urn:ca:tenantId:6D9A82C5-B1BA-4E10-94B1-61357FE88E67/urn:ca:appId:Demo%20App/profile?agent=browser" data-tenantID="6D9A82C5-B1BA-4E10-94B1-61357FE88E67" data-appID="Demo App" data-appKey="776928e0-4f83-11e7-8056-b9dcd6578ee6" ></script>
        -->
        <title>SCP | andiazher Inc</title>
        
    </head>
    <body>
        <div id="contend">
            <div class="text-center">
                <h2>Loading....</h2>
                <img src="pages/images/loading_spinner.gif" height="35" width="35">
            </div>
        </div>
    <script>
        function load(param){
            var idSession= param;
            if(idSession=="" || idSession =="null" || idSession==undefined){
                $.post("login.jsp", {}, function(data){
                    $("#contend").html(data);
                });
            }
            else{
                var q= "<%=q%>";
                var s1= "<%=sessionId%>";
                var s2= "<%=key%>";
                $.post("pages/dashboard.jsp"+q, {sessionId: s1, key:s2}, function(data){
                    $("#contend").html(data);
                });
            }
        }
        load(<%=session.getAttribute("isSession")%>);
    </script>
    </body>
    <style>
        body {
            width:100%;
            height:100%;
            background: url("pages/images/<%=image%>") no-repeat center center fixed;
            background-color: <%=color%>;
            background-repeat: no-repeat;
            background-size: cover;
           -moz-background-size: cover;
           -webkit-background-size: cover;
           -o-background-size: cover;
        }
        @media print
        {
            .no-print
            {
                display: none !important;
                height: 0;
            }


            .no-print, .no-print *{
                display: none !important;
                height: 0;
            }
        }
        a[href]:after {
            content: none !important;
        }
        .short-text{
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }
    </style>
</html>
