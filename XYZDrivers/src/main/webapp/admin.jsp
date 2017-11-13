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
        <nav class="navbar navbar-default">
            <div class="container-fluid">
              <div class="navbar-header">
                <a class="navbar-brand" href="#">XYZ Drivers Association</a>
              </div>

              <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                  <li><a href="/XYZDrivers">Home</a></li>
                </ul>
  
                <ul class="nav navbar-nav navbar-right">
                  <li><a href="logout">Logout</a></li>
                </ul>
              </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
        <div class="page row">
            <div class="col-md-4">
                <h2>Claims</h2>
                <c:forEach items="${claims}" var="claim">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <c:out value="${claim.memberID}" /> - <c:out value="${claim.date}" />
                        </h3>
                    </div>
                    <div class="panel-body">
                        <p><strong>Reason: </strong> <c:out value="${claim.reason}" /></p>
                        <p><strong>Status </strong> <c:out value="${claim.status}" /></p>
                        <p><strong>Amount: </strong> <c:out value="${claim.amount}" /></p>
                    </div>
                </div>
                </c:forEach>
            </div>
            <div class="col-md-4">
                <h2>Members</h2>
                <c:forEach items="${members}" var="member">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Outstanding Balances</h3>
                    </div>
                    <div class="panel-body">
                        <a href="suspend?member=<c:out value="${member[0]}" />">Suspend</a> / <a href="resume?member=<c:out value="${member[0]}" />">Resume</a>
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
                </c:forEach>
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
