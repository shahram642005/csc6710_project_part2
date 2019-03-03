<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Welcome</title>
</head>
<body>
	<table style="padding-bottom:50">
	<col width="600">
	<col width="600">
	<col width="600">
		<tr>
			<c:choose>
				<c:when test="${gender == null}">
					<th align="left"><img src="http://localhost:8080/csc6710_behmardi_part2/images/nogenderProfile.png"></th>
				</c:when>
				<c:when test="${gender eq 'Male'}">
					<th align="left"><img src="http://localhost:8080/csc6710_behmardi_part2/images/maleProfile.png"></th>
				</c:when>
				<c:otherwise>
					<th align="left"><img src="http://localhost:8080/csc6710_behmardi_part2/images/femaleProfile.png"></th>
				</c:otherwise>
			</c:choose>
			<th align="center"><h1>Welcome<c:out value=" ${user.userName}"/>!<img src="http://localhost:8080/csc6710_behmardi_part2/images/welcome.png"></h1></th>
			<th align="right"><a href="logoutUser"><img src="http://localhost:8080/csc6710_behmardi_part2/images/logout.png" title="sign out"></a></th>
		</tr>
	</table>	
	<h2 style="text-align:center"><a href="newJoke"><img src="http://localhost:8080/csc6710_behmardi_part2/images/newJoke.png" title="post a joke"></a></h2>
	<div align="center">
        <table border="1">
        	<c:if test="${jokeList != null}">
	            <tr>
	                <th width="20">ID</th>
	                <th width="100">Title</th>
	                <th width="500">Text</th>
	                <th width="100">Date</th>
	                <th width="100">Owner</th>
	                <th width="200" colspan="2">Actions</th>
	            </tr>
            </c:if>
            <c:forEach var="joke" items="${jokeList}">
                <tr>
                    <td align="center"><c:out value="${joke.jokeId}" /></td>
                    <td align="center"><c:out value="${joke.jokeTitle}" /></td>
                    <td align="center"><c:out value="${joke.jokeText}" /></td>
                    <td align="center"><c:out value="${joke.jokePostDate}" /></td>
                    <td align="center"><c:out value="${joke.postUserId}" /></td>
                    <td align="center">
                        <a href="modifyJoke?jokeId=<c:out value='${joke.jokeId}' />&postUserId=<c:out value='${joke.postUserId}' />"><img src="http://localhost:8080/csc6710_behmardi_part2/images/edit.png" height="30%" width="30%"></a>
                    </td>
                    <td align="center">
                        <a href="deleteJoke?jokeId=<c:out value='${joke.jokeId}' />&postUserId=<c:out value='${joke.postUserId}' />"><img src="http://localhost:8080/csc6710_behmardi_part2/images/trash.png" height="30%" width="30%"></a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>