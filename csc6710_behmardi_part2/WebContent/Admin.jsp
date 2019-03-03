<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Users Management</title>
</head>
<body>
    <table style="padding-bottom:50">
    <col width="600">
	<col width="600">
	<col width="600">
	<col width="600">
	<col width="600">
    	<tr>
    		<th align="left"><img src="images/admin.png" height="100px" width="100px"></th>
    		<th></th>
        	<th></th>
        	<th></th>
       		<th align="right"><a href="logoutUser"><img src="images/logout.png" title="log out" height="70px" width="70px"></a></th>
   		</tr>
   		<tr>
    		<th></th>
    		<th align="center"><a href="initTables?rootUser=TRUE"><img src="images/reset.png" title="initialize database" height="100px" width="100px"></a></th>
        	<th align="center"> <a href="newUser"><img src="images/register.png" title="register user" height="100px" width="100px"></a></th>
        	<th align="center"> <a href="listUsers"><img src="images/list.png" title="list all users" height="100px" width="100px"></a></th>
       		<th></th>
   		</tr>
    </table>
    <div align="center">
    	<c:if test="${userList != null}">
	        <table border="1">
	            <tr>
	                <th width="20">ID</th>
	                <th width="100">User Name</th>
	                <th width="100">Password</th>
	                <th width="100">First Name</th>
	                <th width="100">Last Name</th>
	                <th width="200">Email</th>
	                <th width="50">Gender</th>
	                <th width="50">Age</th>
	                <th width="200" colspan="4">Actions</th>
	            </tr>
	            <c:forEach var="user" items="${userList}">
	                <tr>
	                    <td align="center"><c:out value="${user.userId}" /></td>
	                    <td align="center"><c:out value="${user.userName}" /></td>
	                    <td align="center"><c:out value="${user.password}" /></td>
	                    <td align="center"><c:out value="${user.firstName}" /></td>
	                    <td align="center"><c:out value="${user.lastName}" /></td>
	                    <td align="center"><c:out value="${user.email}" /></td>
	                    <td align="center"><c:out value="${user.gender}" /></td>
	                    <td align="center"><c:out value="${user.age}" /></td>
	                    <td align="center">
	                        <a href="modifyUser?userId=<c:out value='${user.userId}' />"><img src="images/editUser.png" title="edit user" height="30%" width="30%"></a>
	                    </td>
	                    <td align="center">
	                        <a href="deleteUser?userId=<c:out value='${user.userId}' />"><img src="images/removeUser.png" title="remove user" height="30%" width="30%"></a>
	                    </td>
	                    <td align="center">
	                        <a href="banUser?userId=<c:out value='${user.userId}' />"><img src="images/banUser.png" title="ban user" height="30%" width="30%"></a>
	                    </td>
	                    <td align="center">
	                        <a href="unbanUser?userId=<c:out value='${user.userId}' />"><img src="images/unbanUser.png" title="unban user" height="30%" width="30%"></a>
	                    </td>
	                </tr>
	            </c:forEach>
	        </table>
        </c:if>
    </div>
    <div>
	<h2 style="text-align:center">
	    <c:if test="${message != null && color != null}">
	        <font color=<c:out value="${color}"></c:out>><c:out value="${message}"></c:out></font>
	    </c:if>
	</h2>
    </div>
</body>
</html>