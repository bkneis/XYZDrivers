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
            <div class="col-md-12">
                <h2>Error</h2>
                <p><c:out value="${message}" /></p>
                <a href="<c:out value="${link}" />">Back</a>
            </div>
        </div>
    </body>
</html>
