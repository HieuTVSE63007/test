<%-- 
    Document   : SignUp
    Created on : Apr 16, 2021, 9:59:44 AM
    Author     : Truong Van Hieu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign Up Page</title>
    </head>
    <body>
        <h1>Create New Account</h1>
        <c:set var="error" value="${requestScope.ERROR}"/>
        <form action="createAccount" method="POST">
            Email <input type="text" name="txtEmail" value="${param.txtEmail}" /><br/>
            <c:if test="${not empty error.errorEmail}">
                <font color="red">
                ${error.errorEmail}
                </font><br/>
            </c:if>
            Name <input type="text" name="txtName" value="${param.txtName}" /><br/>
            <c:if test="${not empty error.errorName}">
                <font color="red">
                ${error.errorName}
                </font><br/>
            </c:if>
            Phone <input type="text" name="txtPhone" value="" /><br/>
            <c:if test="${not empty error.errorPhone}">
                <font color="red">
                ${error.errorPhone}
                </font><br/>
            </c:if>
            Address <input type="text" name="txtAddress" value="" /><br/>
            <c:if test="${not empty error.errorAddress}">
                <font color="red">
                ${error.errorAddress}
                </font><br/>
            </c:if>
            Password <input type="password" name="txtPassword" value="" /><br/>
            <c:if test="${not empty error.errorPassword}">
                <font color="red">
                ${error.errorPassword}
                </font><br/>
            </c:if>
            Confirm Password <input type="password" name="txtConfirmPassword" value="" /><br/>
            <c:if test="${not empty error.errorConfirmPassword}">
                <font color="red">
                ${error.errorConfirmPassword}
                </font><br/>
            </c:if>
            <input type="submit" value="Sign Up" name="btnAction"/><br/>
            <c:if test="${not empty error.isExistAccount}">
                <font color="red">
                ${error.isExistAccount}
                </font><br/>
            </c:if>
        </form>
    </body>
</html>
