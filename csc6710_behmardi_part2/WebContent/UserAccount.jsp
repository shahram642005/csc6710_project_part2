<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Welcome</title>
</head>
<body>
	<h1 style="text-align:center">Welcome<c:out value=" ${user.userName}" />!</h1>
	<h2 style="text-align:center"><a href="newJoke">Post a Joke!</a></h2>
	<div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of Your Jokes</h2></caption>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Text</th>
                <th>Date</th>
                <th>Owner</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="joke" items="${jokeList}">
                <tr>
                    <td><c:out value="${joke.jokeId}" /></td>
                    <td><c:out value="${joke.jokeTitle}" /></td>
                    <td><c:out value="${joke.jokeText}" /></td>
                    <td><c:out value="${joke.jokePostDate}" /></td>
                    <td><c:out value="${joke.postUserId}" /></td>
                    <td>
                        <a href="modifyJoke?jokeId=<c:out value='${joke.jokeId}' />&postUserId=<c:out value='${joke.postUserId}' />">Modify</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="deleteJoke?jokeId=<c:out value='${joke.jokeId}' />&postUserId=<c:out value='${joke.postUserId}' />">Remove</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>