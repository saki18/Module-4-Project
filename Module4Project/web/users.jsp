<%@page import="Takahm.User"%>
<%@page import="Takahm.User"%>
<%@page import="Takahm.DataAccessObjectFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Store Users</title>
        <%
            User cu = (User) session.getAttribute("currentUser");
            if (cu == null || !cu.isAdministrator()) {
                response.sendRedirect("login.jsp");
            }
            if (application.getAttribute("userDao") == null) {
                application.setAttribute("userDao", DataAccessObjectFactory.getUserDao());
            }
        %>
        <jsp:useBean id="userDao" scope="application" type="Takahm.DataAccessObject<User>" />
    </head>
    <body>
        <h1>Store Users</h1>
        <div>
            <h2>Current Users</h2>
            <c:catch var ="catchException">
            <div>
                <c:forEach var="p" items="${userDao.readAll()}">
                    <div>
                        <form action="users" method="POST">
                            <label>
                                <span>Username</span>
                                <input type="text" name="username" value="${p.getUsername()}" readonly="readonly"/>
                            </label>
                            <label>
                                <span>Password</span>
                                <input type="password" name="password" value="" placeholder="********" />
                            </label>
                            <label>
                                <span>Administrator</span>
                                <input type="checkbox" name="roles" value="ADMIN" ${p.isAdministrator()?"checked='checked'":""} />
                            </label>
                            <label>
                                <span>Inventory Manager</span>
                                <input type="checkbox" name="roles" value="INV_MAN" ${p.isInventoryManager()?"checked='checked'":""} />
                            </label>
                            <input type="submit" name="button" value="Edit" />
                            <input type="submit" name="button" value="Delete" />
                        </form>
                    </div>
                </c:forEach>
            </div>
            </c:catch>
             <c:if test = "${catchException != null}">
                    <p>The exception is : ${catchException} <br />
                        There is an exception: ${catchException.message}</p>
                    </c:if>
            <h2>Add a New User</h2>
            <div>
                <form action="users" method="post">
                    <div>
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username" />
                    </div>
                    <div>
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" />
                    </div>
                    <div>
                        <label for="adminRole">Administrator</label>
                        <input type="checkbox" id="adminRole" name="roles" value="ADMIN" />
                    </div>
                    <div>
                        <label for="invManRole">Inventory Manager</label>
                        <input type="checkbox" id="invManRole" name="roles" value="INV_MAN" />
                    </div>
                    <div>
                        <input type="submit" name="button" value="Create" />
                    </div>
                </form>
            </div>
        </div>
        <br />
        <form action="login" method="post">
            <input type="hidden" name="logout" value="true" />
            <input type="submit" value="Log Out" />
        </form>
    </body>
</html>
