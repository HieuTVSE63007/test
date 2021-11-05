<%-- 
    Document   : ViewCart
    Created on : Apr 25, 2021, 10:48:51 PM
    Author     : Truong Van Hieu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Page</title>
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
        <h1>Your Cart</h1>
        <a href="back" style="text-decoration: none"> &#8592;Rent more car </a>
        <c:set var="result" value="${sessionScope.CART}"/>
        <c:if test="${not empty result}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Car Name</th>
                        <th>Car type</th>
                        <th>Amount</th>
                        <th>Price</th>
                        <th>Total</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${result}" varStatus="counter">
                    <form action="saveOrder" method="POST">
                        <tr>
                            <td>
                                ${counter.count}
                            </td>
                            <td>
                                ${dto.carName}
                                <input type="hidden" name="txtCarID" value="${dto.carID}" />
                                <input type="hidden" name="txtCarName" value="${dto.carName}" />
                            </td>
                            <td>
                                ${dto.carType}
                            </td>
                            <td>
                                ${dto.amount}
                                <input type="hidden" name="txtCarAmount" value="${dto.amount}" />
                                <input type="submit" value="+" name="btnAction" />
                                <input type="submit" value="-" name="btnAction" />
                            </td>
                            <td>
                                ${dto.price}
                            </td>
                            <td>
                                ${dto.total}
                            </td>
                            <td>
                                <input type="submit" value="Delete" name="btnAction" onclick="return confirm('Delete car ?')"/>
                            </td>
                        </tr>
                    </form>
                </c:forEach>
                <td colspan="7">Total Price: ${sessionScope.TOTAL}</td>
            </tbody>
        </table>
        <font color="blue">
        ${requestScope.ERROR}
        </font>
        <form action="checkOut" method="POST">
            Discount code: <input type="text" name="txtDiscount" value="" />
            <input type="submit" value="Confirm" name="btnAction" onclick="return confirm('Check Out ?')"/>
        </form>
    </c:if>
        <c:if test="${empty result}">
            <h2>No car your cart !!!</h2>
        </c:if>
</body>
</html>
