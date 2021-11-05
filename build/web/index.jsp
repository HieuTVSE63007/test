<%-- 
    Document   : index
    Created on : Apr 23, 2021, 9:52:31 PM
    Author     : Truong Van Hieu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
        <script src="js/paginga.jquery.js"></script>
    </head>
    <body>
        <div style="margin-left: auto; margin-right: auto;  width: 80%; text-align: center">
            <form action="search" method="GET">
                <input type="submit" value="Login" name="btnAction" />
            </form>
            <h1>Welcome to Car rental online service</h1>
            <form action="search" method="GET">
                Car name <input type="text" name="txtSearchCarName" value="" /><br/>
                Category
                <select name="cbCategory">
                    <option value="" selected="selected">All Category</option>
                    <c:forEach var="category" items="${requestScope.DATA}">
                        <option value="${category.categoryID}">${category.categoryName}</option>
                    </c:forEach>
                </select><br/>
                Rental date <input type="text" name="txtRentalData" value="" /><br/>
                Return date <input type="text" name="txtReturnDate" value="" /><br/>
                Amount <input type="text" name="txtAmount" value="" /><br/>
                <input type="submit" value="Search" name="btnAction"/>
            </form><br/>
            <c:set var="result" value="${requestScope.LISTCAR}"/>
            <c:if test="${not empty result}">
                <div class="paginate">
                    <div class="items">
                        <form action="renCar" method="GET">
                            <c:forEach var="dto" items="${result}" varStatus="counter">
                                <div style="margin: auto; margin-bottom: 20px;  width: 50%; ;border: 1px solid; text-align: left">
                                    <div style="display: inline-block; width: 30%; padding-top: 5px; padding-left: 30px">
                                        Car Name: ${dto.carName}<br/>
                                        Color: ${dto.color}
                                    </div>
                                    <div style="display: inline-block; width: 30%; padding-top: 5px; padding-left: 20px">
                                        Year: ${dto.year}<br/>
                                        Category: ${dto.category}
                                    </div>
                                    <div style="display: inline-block; width: 20%; padding-top: 5px; padding-left: 10px; ">
                                        Price: ${dto.price}<br/>
                                        Quantity: ${dto.quantity}
                                    </div>
                                    <input style="margin: 20px;" type="submit" value="Ren Car" name="btnAction" />
                                </div>
                            </c:forEach>
                        </form>
                    </div>
                    <div class="pager">
                        <div class="firstPage" style="display: inline-block">&laquo;</div>
                        <div class="previousPage" style="display: inline-block">&lsaquo;</div>
                        <div class="pageNumbers" style="display: inline-block"></div>
                        <div class="nextPage" style="display: inline-block">&rsaquo;</div>
                        <div class="lastPage" style="display: inline-block">&raquo;</div>
                    </div>
                </div>
            </c:if>
        </div>
        <script>
            $(function () {
                $(".paginate").paginga({
                    itemsPerPage: 20,
                    itemsContainer: ".items",
                    item: "> div",
                    page: 1,
                    nextPage: ".nextPage",
                    previousPage: ".previousPage",
                    firstPage: ".firstPage",
                    lastPage: ".lastPage",
                    pageNumbers: ".pageNumbers",
                    maxPageNumbers: false,
                    currentPageClass: "active",
                    pager: ".pager",
                    autoHidePager: true,
                    scrollToTop: {
                        offset: 15,
                        speed: 100,
                    },
                    history: false,
                    historyHashPrefix: "page-"
                });
            });
        </script>
    </body>
</html>
