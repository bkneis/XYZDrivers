<%-- 
    Document   : member
    Created on : 13-Nov-2017, 13:39:14
    Author     : Alex
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <nav class="navbar navbar-default">
            <div class="container-fluid">
              <div class="navbar-header">
                <a class="navbar-brand" href="#">XYZ Drivers Association</a>
              </div>

              <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                  <li><a href="#">Home</a></li>
                </ul>
  
                <ul class="nav navbar-nav navbar-right">
                  <li><a href="#">Logout</a></li>
                </ul>
              </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
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
                        <a href="submit-claim.html">Submit Claim</a>
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
            </div> <!-- col-md-6 -->
        </div>
    </body>
</html>
