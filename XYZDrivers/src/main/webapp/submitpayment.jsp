<!DOCTYPE html>
<!--
 - @file submitpayment.jsp
 - @author Joe Dicker
-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Make a Payment to your Membership</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="node_modules/bootstrap/dist/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="styles/main.css" />
    </head>
    <body>
        <jsp:include page="nav.jsp"></jsp:include>
        
        <form action="payment" method="post">
            Amount: <input type="number" name="amount">
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
