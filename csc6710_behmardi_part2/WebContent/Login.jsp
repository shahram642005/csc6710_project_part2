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
        <table border="1" cellpadding="5">
            <caption>
                <h2>Please login or register!</h2>
            </caption>            
            <tr>
                <th>UserName </th>
                <td>
                    <input type="text" name="userName" size="45"
                            value="<c:out value='${user.userName}' />"
                        />
                </td>
            </tr>
            <tr>
                <th>Password </th>
                <td>
                    <input type="text" name="password" size="45"
                            value="<c:out value='${user.password}' />"
                    />
                </td>
            </tr>
        </table>
        <table cellpadding="5">
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
	    <c:if test="${showErrMsg != null}">
	        <font color="red">The user name or the password is not correct, please try again!</font>
	    </c:if>
	</h2>
	<h2 style="text-align:center">
	    <c:if test="${showInitMsg != null}">
	        <font color="green">All tables are successfully initialized!</font>
	    </c:if>
	</h2>
    </div>
</body>
</html>