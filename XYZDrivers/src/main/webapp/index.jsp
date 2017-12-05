<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Home Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="node_modules/bootstrap/dist/css/bootstrap.min.css" />
    </head>
    <body>
        <jsp:include page="nav.jsp"></jsp:include>
        
        <div class="row text-center">
            <h1>Welcome to the driver's Association</h1>
            <h3>Please select an action</h3>
            
            <form method="GET" action="auth">
            
                <div class="form-group">
                    <label for="userType">Type of User:</label>
                    <select name="userType">
                        <option value="admin">Admin</option>
                        <option value="user">User</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="actionType">Type of Action</label>
                    <select name="actionType">
                        <option value="login">Login</option>
                        <option value="register">Register</option>
                    </select>
                </div>
                
                <input type="submit" value="Go" />
            
            </form>
            
        </div>
    </body>
</html>
