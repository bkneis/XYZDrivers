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
                        <h3 class="panel-title">Your outstanding balances</h3>
                        <a href="submitpayment.jsp">Submit Payment</a>
                    </div>
                    <div class="panel-body">
                        <c:if test="${member.status == \"OUTSTANDING\"}"> 
                            <ul>
                                <li>
                                    <c:out value="${member.id}"/>
                                </li>
                                <li>
                                    - name: <c:out value="${member.name}"/>
                                </li>
                                <li>
                                    - status: <c:out value="${member.status}"/>
                                </li>
                                <li>
                                    - balance: <c:out value="${member.balance}"/>
                                </li>
                            </ul>
                        </c:if>
                    </div>
                </div>
            </div>  <!-- col-md-6 -->
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Your claims</h3>
                        <a href="submit-claim.jsp">Submit Claim</a>
                    </div>
                    <div class="panel-body">
                        <ul>
                            <c:forEach items="${claims}" var="claim">
                                <li>
                                    <fmt:formatDate value="${claim.date.getTime()}" pattern="yyyy-MM-dd"/>
                                    - Member: <c:out value="${claim.memberID}" />
                                    - Reason: <c:out value="${claim.reason}" />
                                    - Status <c:out value="${claim.status}" />
                                    - Amount <c:out value="${claim.amount}" />
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div> <!-- col-md-6 -->
        </div>
    </body>
</html>
