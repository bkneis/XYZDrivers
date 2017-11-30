<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Panel</title>
        <link rel="stylesheet" type="text/css" href="node_modules/bootstrap/dist/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="styles/main.css" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
    <!-- navbar -->
        <jsp:include page="nav.jsp"></jsp:include>
    <!-- row 1 -->
        <div class="page row">
            <h3>Total cost in Claims: £<c:out value="${totalClaims}"/></h3>
            <h2>Total Turnover: £<c:out value="${totalTurnover}"/>
        </div>
        <hr/>
    <!-- row 3 -->
        <div class="page row">
        <!-- provisional members -->
            <div class="col-md-4">
                <h2>Provisional Members</h2>
                <div class="panel-group">
                    <c:forEach items="${provisionalMembers}" var="provisionalMember">
                        <div class="panel panel-default">
                        <!-- heading -->
                            <div class="panel-heading">
                                <span style="float: right;">
                                    <a data-toggle="collapse" href="#${provisionalMember.id}">
                                        expand
                                    </a>
                                </span>
                                <h3 class="panel-title">
                                    <c:out value="${provisionalMember.name}" />
                                </h3>

                                <a href="/XYZDrivers/membership-status?status=APPROVED&amp;member_id=<c:out value="${provisionalMember.id}" />">
                                    Approve
                                </a>
                                /
                                <a href="/XYZDrivers/membership-status?status=REJECTED&amp;member_id=<c:out value="${provisionalMember.id}" />">
                                    Reject
                                </a>
                            </div>
                        <!-- body -->
                            <div id="${provisionalMember.id}" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <p><b>ID: </b><c:out value="${provisionalMember.id}"/></p>
                                    <p><b>Name: </b><c:out value="${provisionalMember.name}"/></p>
                                    <p><b>Address: </b><c:out value="${provisionalMember.address}"/></p>
                                    <p><b>Date of Birth: </b><fmt:formatDate value="${provisionalMember.dob.getTime()}" pattern="yyyy-MM-dd"/></p>
                                    <p><b>Date of Record: </b><fmt:formatDate value="${provisionalMember.dor.getTime()}" pattern="yyyy-MM-dd"/></p>
                                    <p><b>Balance: </b><c:out value="${provisionalMember.balance}"/></p>
                                    <p><b>Status: </b><c:out value="${provisionalMember.status}"/></p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        <!-- Existing User Claims -->
            <div class="col-md-4">
                <h2>Existing Claims</h2>
                <div class="panel-group">
                    <c:forEach items="${claims}" var="claim">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <span style="float: right;">
                                <a data-toggle="collapse" href="#${claim.id}">
                                    expand
                                </a>
                            </span>
                            <h3 class="panel-title">
                                <c:out value="${claim.memberID}" /> - <fmt:formatDate value="${claim.date.getTime()}" pattern="yyyy-MM-dd"/>
                            </h3>
                            <c:if test="${claim.status == 'SUBMITTED'}">
                                <a href="/XYZDrivers/claim-status?status=APPROVED&amp;claim_id=<c:out value="${claim.id}" />">
                                    Approve
                                </a> /
                                <a href="/XYZDrivers/claim-status?status=REJECTED&amp;claim_id=<c:out value="${claim.id}" />">
                                    Reject
                                </a>
                            </c:if>
                        </div> 
                        <div id="${claim.id}" class="panel-collapse collapse">
                            <div class="panel-body">
                                <p><strong>Reason: </strong> <c:out value="${claim.reason}" /></p>
                                <p><strong>Status </strong> <c:out value="${claim.status}" /></p>
                                <p><strong>Amount: </strong> <c:out value="${claim.amount}" /></p>
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                </div>
            </div>
        <!-- outstanding balances -->
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
        </div>
        <hr/>
    <!-- row 2 -->
        <div class="page row">
        <!-- approved members -->
            <div class="col-md-12">
                <span style="float: right;">
                    <a href="/XYZDrivers/charge-members?amount=<c:out value="${totalClaims}"/>&amp;num_members=${fn:length(members)}">
                        Charge ALL annual amount
                    </a>
                </span>
                <h2>Approved Members & Claim Eligibility</h2>
                <div class="panel-group">
                    <c:forEach items="${members}" var="member" varStatus="varStatus">
                    <div class="panel panel-default">
                    <!-- heading -->
                        <div class="panel-heading">
                            <span style="float: right;">
                                <a data-toggle="collapse" href="#${member.id}">
                                    expand
                                </a>
                            </span>
                            <h3 class="panel-title"><c:out value="${member.name}"/></h3>
                        </div>
                    <!-- body -->
                        <div id="${member.id}" class="panel-collapse collapse">
                            <div class="panel-body">
                                <p>ID : <c:out value="${member.id}"/></p>
                                <p>Name : <c:out value="${member.name}"/></p>
                                <p>Address : <c:out value="${member.address}"/></p>
                                <p>Date of Birth : <fmt:formatDate value="${member.dob.getTime()}" pattern="yyyy-MM-dd"/></p>
                                <p>Date of Record : <fmt:formatDate value="${member.dor.getTime()}" pattern="yyyy-MM-dd"/></p>
                                <p>Status : <c:out value="${member.status}"/></p>
                                <p>Balance : <c:out value="${member.balance}"/></p>
                                <br/>
                                <p>${eligibleClaims[varStatus.index]}</p>
                                <br/>
                                <h4>
                                    <c:if test="${(member.status == 'APPROVED') or (member.status == 'REJECTED')}">
                                    <a href="/XYZDrivers/membership-status?status=SUSPENDED&amp;member_id=<c:out value="${member.id}" />">
                                        Suspend
                                    </a>
                                    </c:if>
                                    <c:if test="${member.status == 'SUSPENDED'}">
                                    <a href="/XYZDrivers/membership-status?status=APPROVED&amp;member_id=<c:out value="${member.id}" />">
                                        Resume
                                    </a>
                                    </c:if>
                                </h4>
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </body>
</html>
