<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Login</title>
</head>
<body>
    <h1 style="text-align:center">Welcome to Jokes website!</h1>
    <div align="center">
        <form action="loginUser" method="post">
        <table border="1">
            <caption>
                <h2>Please login or register!</h2>
            </caption>            
            <tr>
                <th>UserName </th>
                <td>
                    <input type="text" placeholder="user name" name="userName" size="45"
                            value="<c:out value='${user.userName}' />"
                    />
                </td>
            </tr>
            <tr>
                <th>Password </th>
                <td>
                    <input type="text" placeholder="password" name="password" size="45"
                            value="<c:out value='${user.password}' />"
                    />
                </td>
            </tr>
        </table>
        <table>
            <tr>
            	<td align="center">
                    <input type="submit" value="login" />
                </td>
                <td align="center">
                    <a href="newUser">register now!</a>
                </td>
                <td align="center">
                    <a href="initTables?rootUser=FALSE">Initialize Database!</a>
                </td>
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