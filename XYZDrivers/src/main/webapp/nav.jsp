<%-- 
    Document   : nav
    Created on : 25-Nov-2017, 03:14:25
    Author     : Toneo
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<nav class="navbar navbar-default">
    <div class="container-fluid">
      <div class="navbar-header">
        <a class="navbar-brand" href="home">XYZ Drivers Association</a>
      </div>

      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
            <li><a href="home">Home</a></li>

        <c:if test="${not empty sessionScope.user and sessionScope.user.isAdministrator()}">
            <li><a href="admin">Admin</a></li>
        </c:if>
        <c:if test="${not empty sessionScope.user}">
            <li><a href="member">Members</a></li>
        </c:if>

            <li><a href="logout">Logout</a></li>
        </ul>
      </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>