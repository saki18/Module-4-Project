<%-- 
    Document   : login
    Created on : Apr 10, 2016, 9:17:36 PM
    Author     : Takahm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log In</title>
    </head>
    <body>
        <h1>Log In</h1>
        <form action="login" method="post">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" />
            <label for="password">Password</label>
            <input type="password" id="password" name="password" />
            <input type="submit" value="Log In" />
        </form>
        <c:if test="${param.failed eq 'true'}">
            <div>Invalid username or password.</div>
        </c:if>
    </body>
</html>
