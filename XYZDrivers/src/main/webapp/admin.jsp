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
                <h2>Outstanding Balances</h2>
                <c:forEach items="${outstandingBalances}" var="outstandingBalance">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title"><c:out value="${outstandingBalance.name}" /></h3>
                    </div>
                    <div class="panel-body">
                        Balance : <c:out value="${outstandingBalance.balance}"/>
                        </ul>
                    </div>
                </div>
                </c:forEach>
            </div>
            <div class="col-md-4">
                <h2>Members</h2>
                <c:forEach items="${members}" var="member">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title"><c:out value="${member.name}"/></h3>
                    </div>
                    <div class="panel-body">
                        <p>ID : <c:out value="${member.id}"/></p>
                        <p>Name : <c:out value="${member.name}"/></p>
                        <p>Address : <c:out value="${member.address}"/></p>
                        <p>Date of Birth : <c:out value="${member.dob}"/></p>
                        <p>Date of Record : <c:out value="${member.dor}"/></p>
                        <p>Status : <c:out value="${member.status}"/></p>
                        <p>Balance : <c:out value="${member.balance}"/></p>
                        <p>
                            <a href="/XYZDrivers/membership-status?status=SUSPENDED&amp;member_id=<c:out value="${member.id}" />">
                                Suspend
                            </a> / 
                            <a href="/XYZDrivers/membership-status?status=APPROVED&amp;member_id=<c:out value="${member.id}" />">
                                Resume
                            </a>
                        </p>
                    </div>
                </div>
                </c:forEach>
            </div>
        </div>
        
    </body>
</html>
