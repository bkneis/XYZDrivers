<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

        <span>Hello, ${sessionScope.username}!</span>
        
        <div class="page row">
            <div class="col-md-4">
                <h2>Total Turnover</h2>
                <h3><c:out value="${totalTurnover}" />
            </div>
            <div class="col-md-4">
                <h2>Claims</h2>
                <c:forEach items="${claims}" var="claim">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <c:out value="${claim.memberID}" /> - <fmt:formatDate value="${claim.date.getTime()}" pattern="yyyy-MM-dd"/>
                        </h3>
                    </div>
                    <div class="panel-body">
                        <p><strong>Reason: </strong> <c:out value="${claim.reason}" /></p>
                        <p><strong>Status </strong> <c:out value="${claim.status}" /></p>
                        <p><strong>Amount: </strong> <c:out value="${claim.amount}" /></p>
                        <c:if test="${claim.status == 'PENDING'}">
                            <a href="/XYZDrivers/claim-status?status=APPROVED&amp;claim_id=<c:out value="${claim.id}" />">
                                Approve
                            </a> /
                            <a href="/XYZDrivers/claim-status?status=REJECTED&amp;claim_id=<c:out value="${claim.id}" />">
                                Reject
                            </a>
                        </c:if>
                    </div>
                </div>
                </c:forEach>
            </div>
            <div class="col-md-4">
                <h2>Provisional Members</h2>
                <c:forEach items="${provisionalMembers}" var="provisionalMember">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title"><c:out value="${provisionalMember.name}" /></h3>
                    </div>

                        <p>ID : <c:out value="${provisionalMember.id}"/></p>
                        <p>Name : <c:out value="${provisionalMember.name}"/></p>
                        <p>Address : <c:out value="${provisionalMember.address}"/></p>
                        <p>Date of Birth : <fmt:formatDate value="${provisionalMember.dob.getTime()}" pattern="yyyy-MM-dd"/></p>
                        <p>Date of Record : <fmt:formatDate value="${provisionalMember.dor.getTime()}" pattern="yyyy-MM-dd"/></p>
                        <p>Status : <c:out value="${provisionalMember.status}"/></p>
                        <p>
                            <a href="/XYZDrivers/membership-status?status=APPROVED&amp;member_id=<c:out value="${provisionalMember.id}" />">
                                Approve
                            </a> /
                            <a href="/XYZDrivers/membership-status?status=REJECTED&amp;member_id=<c:out value="${provisionalMember.id}" />">
                                Reject
                            </a>
                        </p>
                    </div>
                </div>
                </c:forEach>
            </div>
        </div>
        <div class="page row">
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
                        <p>Date of Birth : <fmt:formatDate value="${member.dob.getTime()}" pattern="yyyy-MM-dd"/></p>
                        <p>Date of Record : <fmt:formatDate value="${member.dor.getTime()}" pattern="yyyy-MM-dd"/></p>
                        <p>Status : <c:out value="${member.status}"/></p>
                        <p>Balance : <c:out value="${member.balance}"/></p>
                        <p>
                            <c:if test="${(member.status == 'APPROVED') or (member.status == 'REJECTED')}">
                            <a href="/XYZDrivers/membership-status?status=SUSPENDED&amp;member_id=<c:out value="${member.id}" />">
                                Suspend
                            </a> /
                            </c:if>
                            <c:if test="${member.status == 'SUSPENDED'}">
                            <a href="/XYZDrivers/membership-status?status=APPROVED&amp;member_id=<c:out value="${member.id}" />">
                                Resume
                            </a>
                            </c:if>
                        </p>
                    </div>
                </div>
                </c:forEach>
            </div>
            <div class="col-md-4">
                <h2>User Claims</h2>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Eligibility Status</h3>
                    </div>
                    <div class="panel-body">
                        <ul>
                            <c:forEach items="${eligibleClaims}" var="current">
                                <li>
                                    <c:out value="${current}"/><br>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
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
        </div>
    </body>
</html>
