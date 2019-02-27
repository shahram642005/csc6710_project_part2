<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Users Management</title>
</head>
<body>
	<h1 style="text-align:center">Users Management</h1>
    <h2 style="text-align:center">
    	<a href="initTables?rootUser=TRUE">Initialize Database</a>
        &nbsp;&nbsp;&nbsp;
        <a href="newUser">Register New User</a>
        &nbsp;&nbsp;&nbsp;
        <a href="listUsers">List All Users</a>
    </h2>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of Users</h2></caption>
            <tr>
                <th>ID</th>
                <th>User Name</th>
                <th>Password</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Gender</th>
                <th>Age</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="user" items="${userList}">
                <tr>
                    <td><c:out value="${user.userId}" /></td>
                    <td><c:out value="${user.userName}" /></td>
                    <td><c:out value="${user.password}" /></td>
                    <td><c:out value="${user.firstName}" /></td>
                    <td><c:out value="${user.lastName}" /></td>
                    <td><c:out value="${user.email}" /></td>
                    <td><c:out value="${user.gender}" /></td>
                    <td><c:out value="${user.age}" /></td>
                    <td>
                        <a href="modifyUser?userId=<c:out value='${user.userId}' />">Modify</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="deleteUser?userId=<c:out value='${user.userId}' />">Remove</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div>
	<h2 style="text-align:center">
	    <c:if test="${showInitMsg != null}">
	        <font color="green">All tables are initialized!</font>
	    </c:if>
	</h2>
    </div>
</body>
</html>