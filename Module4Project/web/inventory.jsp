<%@page import="Takahm.User"%>
<%@page import="edu.lcc.citp.inventory.Product"%>
<%@page import="Takahm.DataAccessObjectFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <fmt:setBundle basename="takahm.messages" var="message"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Store Inventory</title>
            <%
                User cu = (User) session.getAttribute("currentUser");
                if (cu == null || !cu.isInventoryManager()) {
                    response.sendRedirect("login.jsp");
                }
                if (application.getAttribute("productDao") == null) {
                    application.setAttribute("productDao", DataAccessObjectFactory.getProductDao());
                }
            %>
            <jsp:useBean id="productDao" scope="application" type="Takahm.DataAccessObject<Product>" />
        </head>
        <body>
            <h1>Store Inventory</h1>
            <div>
                <h2>Current Products</h2>
                <c:catch var ="catchException">
                    <div>
                        <c:forEach var="p" items="${productDao.readAll()}">
                            <div>
                                <form action="inventory" method="POST">
                                    <label>
                                        <fmt:message bundle="${messages}" key="UPC" /> 
                                        <input type="text" name="upc" value="${p.getUpc()}" readonly="readonly"/>
                                    </label>
                                    <label>
                                        <span>Short Details</span>
                                        <input type="text" name="shortDetails" value="${p.getShortDetails()}" />
                                    </label>
                                    <label>
                                        <span>Long Details</span>
                                        <input type="text" name="longDetails" value="${p.getLongDetails()}" />
                                    </label>
                                    <label>
                                        <span>Price</span>
                                        <input type="text" name="price" value="${p.getPrice()}" />
                                    </label>
                                    <label>
                                        <span>Stock</span>
                                        <input type="text" name="stock" value="${p.getStock()}" />
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
                <h2>Add a New Product</h2>
                <div>
                    <form action="inventory" method="post">
                        <div>
                            <label for="upc">UPC</label>
                            <input type="text" id="upc" name="upc" />
                        </div>
                        <div>
                            <label for="shortDetails">Short Details</label>
                            <input type="text" id="shortDetails" name="shortDetails" />
                        </div>
                        <div>
                            <label for="longDetails">Long Details</label>
                            <input type="text" id="longDetails" name="longDetails" />
                        </div>
                        <div>
                            <label for="price">Price</label>
                            <input type="text" id="price" name="price" />
                        </div>
                        <div>
                            <label for="stock">Stock</label>
                            <input type="text" id="stock" name="stock" />
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