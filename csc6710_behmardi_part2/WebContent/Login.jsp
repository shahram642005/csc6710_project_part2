<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Login</title>
</head>
<body>
    <h1 style="text-align:center">Please login or register!</h1>
    <div align="center">
	    <table style="padding-bottom:50">
	    <col width="600">
		<col width="600">
		<col width="600">
		<col width="600">
		<col width="600">
	   		<tr>
	    		<th align="center"><a href="initTables?rootUser=FALSE"><img src="images/reset.png" title="initialize database" height="100px" width="100px"></a></th>
	        	<th align="center"> <a href="newUser"><img src="images/register.png" title="register user" height="100px" width="100px"></a></th>
	   		</tr>
	    </table>
    </div>
    <div align="center">
        <form action="loginUser" method="post">
        	<table>
        		<tr><td>User Name:</td></tr>
        		<tr>
        			<td><input type="text" placeholder="user name" name="userName" size="50" autofocus value="<c:out value='${user.userName}'/>"/></td>
        		</tr>
        		<tr><td>Password:</td></tr>
        		<tr>
        			<td><input type="text" placeholder="password" name="password" size="50" value="<c:out value='${user.password}'/>"/></td>
        		</tr>
        		<tr>
        			<td><input type="submit" value="login" /></td>
        		</tr>
        	</table>
        </form>
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