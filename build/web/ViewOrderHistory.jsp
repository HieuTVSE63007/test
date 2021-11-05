<%-- 
    Document   : ViewOrderHistory
    Created on : Apr 26, 2021, 8:30:23 PM
    Author     : Truong Van Hieu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Page</title>
    </head>
    <body>
        <form action="search" method="GET">
            <c:if test="${not empty sessionScope.USER.fullName}">
                <font color="red">
                welcome, ${sessionScope.USER.fullName}
                <c:if test="${sessionScope.USER.status eq 'New'}">
                    <input type="submit" value="Verify" name="btnAction"/><br/>
                </c:if>
                </font>
                <a href="logout">Log out</a><br/>
            </c:if>
        </form>
        <h1>Your order history</h1>
        <a href="back" style="text-decoration: none"> &#8592;back </a>
        <form action="history" method="GET">
            Name <input type="text" name="txtName" value="${param.txtName}" /><br/>
            Order Date <input type="text" name="txtOrderDate" value="${param.txtOrderDate}" />
            <input type="submit" value="Search" name="btnAction" />
        </form>
        <c:set var="result" value="${requestScope.HISTORY}"/>
        <c:if test="${not empty result}">
            <table border="1">
                <thead>
                    <tr>
                        <th>NO.</th>
                        <th>User</th>
                        <th>Total Price</th>
                        <th>Status</th>
                        <th>Order Date</th>
                        <th>Action</th>
                        <th>Detail</th>
                        <th>Feedback</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${result}" varStatus="counter">
                    <form action="history">
                        <tr>
                            <td>
                                ${counter.count}
                            </td>
                            <td>
                                ${dto.userID}
                            </td>
                            <td>
                                ${dto.totalPrice}
                            </td>
                            <td>
                                ${dto.status}
                            </td>
                            <td>
                                ${dto.orderDate}
                            </td>
                            <td>
                                <input type="submit" value="Delete" name="btnAction" onclick="return confirm('Delete order ?')"/>
                                <input type="hidden" name="txtHistoryID" value="${dto.historyID}" />
                            </td>
                            <td>
                                <input type="submit" value="Detail" name="btnAction" />
                                <input type="hidden" name="txtHistoryID" value="${dto.historyID}" />
                            </td>
                            <td>
                                <input type="submit" value="Feedback" name="btnAction" />
                                <input type="hidden" name="txtHistoryID" value="${dto.historyID}" />
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
        <c:if test="${empty result}">
            <h2>No record is matched !!!</h2>
        </c:if>
</body>
</html>
