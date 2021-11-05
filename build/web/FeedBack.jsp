<%-- 
    Document   : FeedBack
    Created on : Apr 26, 2021, 11:23:55 PM
    Author     : Truong Van Hieu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feedback Page</title>
    </head>
    <body>
        <h1>Feedback</h1>
        <form action="feedbackHistory" method="GET">
            History ID : ${param.txtHistoryID}<br/>
            <input type="hidden" name="txtHistoryID" value="${param.txtHistoryID}" />
            Rating 
            <input type="radio" name="rate" value="1" />1
            <input type="radio" name="rate" value="2" />2
            <input type="radio" name="rate" value="3" />3
            <input type="radio" name="rate" value="4" />4
            <input type="radio" name="rate" value="5" />5
            <input type="radio" name="rate" value="6" />6
            <input type="radio" name="rate" value="7" />7
            <input type="radio" name="rate" value="8" />8
            <input type="radio" name="rate" value="9" />9
            <input type="radio" name="rate" value="10" />10<br/>
            <textarea name="txtFeedBack"></textarea><br/>
            <input type="submit" value="Feedback" name="btnAction" />
        </form>
    </body>
</html>
