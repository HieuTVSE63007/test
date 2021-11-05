<%-- 
    Document   : Verify
    Created on : Apr 26, 2021, 9:42:13 PM
    Author     : Truong Van Hieu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verify Page</title>
    </head>
    <body>
        <font color="red">
        welcome, ${sessionScope.USER.fullName}
        </font>
        <a href="logout">Log out</a><br/>
        <h1>Welcome to Car rental online service</h1>
        <form action="verify">
            <h3>You are logged in <font color="blue">${sessionScope.USER.userID}</font> account</h3>
            <input type="submit" value="Click here to verify " name="btnAtion"/>
        </form>
    </body>
</html>
