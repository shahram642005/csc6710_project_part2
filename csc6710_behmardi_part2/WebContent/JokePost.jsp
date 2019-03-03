<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Post Joke</title>
</head>
<body>
    <div align="center">
    	<h2><c:out value='${formText}' /></h2>
    	<form action=<c:out value="${formAction}" /> method="post">
	        <table>
	        	<tr>
	        		<td>
		                <c:if test="${joke != null}">
		                    <input type="hidden" name="jokeId" value="<c:out value='${joke.jokeId}' />" />
		                    <input type="hidden" name="userId" value="<c:out value='${joke.postUserId}' />" />
		                </c:if> 
	                </td>
				</tr>
				<tr><td>Title:</td></tr>
	            <tr>
	                <td><input type="text" name="title" size="50" autofocus placeholder="title of your joke" value="<c:out value='${joke.jokeTitle}' />"/></td>
	            </tr>
	            <tr><td>Description:</td></tr>
	            <tr>
	                <td><input type="text" name="description" size="50" placeholder="description of your joke" value="<c:out value='${joke.jokeText}' />" /></td>
	            </tr>
	            <tr>
		            <td rowspan="5" align="center"><input type="submit" value="<c:out value='${buttonText}' />" /></td>
	            </tr>
	        </table>
        </form>
    </div>
</body>
</html>