<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Panel</title>
        <link rel="stylesheet" type="text/css" href="node_modules/bootstrap/dist/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="styles/main.css" />
    </head>
    <body>
        <jsp:include page="nav.jsp"></jsp:include>
        
        <span>Hello, ${requestScope.user.id}!</span>
        
        <div class="page row">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">All Claims</h3>
                    </div>
                    <div class="panel-body">
                        <ul>
                        <c:forEach items="${claims}" var="claim">
                            <li>
                                <c:out value="${claim.date}" />
                                - Member: <c:out value="${claim.memberID}" />
                                - Reason: <c:out value="${claim.reason}" />
                                - Status <c:out value="${claim.status}" />
                                - Amount <c:out value="${claim.amount}" />
                            </li>
                        </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Outstanding Balances</h3>
                    </div>
                    <div class="panel-body">
                        <ul>
                        <c:forEach items="${outstandingBalances}" var="outstandingBalance">
                            <li>
                            - name: <c:out value="${outstandingBalance.name}"/>
                            - balance: <c:out value="${outstandingBalance.balance}"/>
                            </li>
                        </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="page row">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">All Members</h3>
                </div>
                <div class="panel-body">
                    <ul>
                    <c:forEach items="${members}" var="member">
                        <li>
                            <c:out value="${member.id}"/>
                        - name: <c:out value="${member.name}"/>
                        - address: <c:out value="${member.address}"/>
                        - dob: <c:out value="${member.dob}"/>
                        - dor: <c:out value="${member.dor}"/>
                        - status: <c:out value="${member.status}"/>
                        - balance: <c:out value="${member.balance}"/>
                        </li>
                    </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </body>
</html>
