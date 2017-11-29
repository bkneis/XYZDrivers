<%-- 
    Document   : member
    Created on : 13-Nov-2017, 13:39:14
    Author     : Alex
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Member Home</title>
        <link rel="stylesheet" type="text/css" href="node_modules/bootstrap/dist/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="styles/main.css" />
    </head>
    <body>
        <jsp:include page="nav.jsp"></jsp:include>

        <div class="page row">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Your outstanding balance</h3>
                    </div>
                    <div class="panel-body">
                        <c:if test="${member.balance > 0}"> 
                            <table style="width:100%">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>NAME</th>
                                    <th>STATUS</th>
                                    <th>BALANCE</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td><c:out value="${member.id}"/></br></td>
                                    <td><c:out value="${member.name}"/></td>
                                    <td><c:out value="${member.status}"/></td>
                                    <td><c:out value="${member.balance}"/></td>
                                </tr>
                            </tbody>
                            </table>
                        </c:if>
                    </div>
                    <div class="panel-heading" style="text-align: right;">
                        <a href="submitpayment.jsp">Submit Payment</a>
                    </div>
                </div>
            </div>  <!-- col-md-6 -->
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Your existing claims</h3>
                    </div>
                    <div class="panel-body">
                        <table style="width:100%">
                            <thead>
                                <tr>
                                    <th>DATE</th>
                                    <th>MEMBER</th>
                                    <th>REASON</th>
                                    <th>STATUS</th>
                                    <th>AMOUNT</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${claims}" var="claim">
                                    <tr>
                                        <td><fmt:formatDate value="${claim.date.getTime()}" pattern="yyyy-MM-dd"/></br></td>
                                        <td><c:out value="${claim.memberID}" /></td>
                                        <td><c:out value="${claim.reason}" /></td>
                                        <td><c:out value="${claim.status}" /></td>
                                        <td><c:out value="${claim.amount}" /></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="panel-heading" style="text-align: right;">
                        <a href="submit-claim.jsp">Submit New Claim</a>
                    </div>
                </div>
            </div> <!-- col-md-6 -->
        </div>
    </body>
</html>
