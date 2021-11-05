<%-- 
    Document   : Login
    Created on : Apr 14, 2021, 8:47:54 PM
    Author     : Truong Van Hieu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    </head>
    <body>
        <h1>Login</h1>
        <c:set var="error" value="${requestScope.ERROR}"/>
        <form action="login" method="POST">
            Username <input type="text" name="txtUserID" value=""/><br/>
            Password <input type="password" name="txtPassword" value=""/><br/>
            <div class="g-recaptcha" data-sitekey="6LeRrLUaAAAAAMCohafYecokDC92A03yua9pnPcz"></div>
            <input type="submit" value="Login" name="btnAction"/>
            <input type="reset" value="Reset"/><br/>
            <c:if test="${not empty error}">
                <font color="red">
                ${error}
                </font>
            </c:if>
        </form>
        <a href="SignUp">Sign Up</a>
    </body>
</html>
