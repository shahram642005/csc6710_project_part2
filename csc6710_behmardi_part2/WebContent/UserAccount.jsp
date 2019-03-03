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
					<th align="left"><a href="modifyUser?userId=0"><img src="images/nogenderProfile.png" height="100px" width="100px"></a></th>
				</c:when>
				<c:when test="${gender eq 'Male'}">
					<th align="left"><a href="modifyUser?userId=0"><img src="images/maleProfile.png" height="100px" width="100px"></a></th>
				</c:when>
				<c:otherwise>
					<th align="left"><a href="modifyUser?userId=0"><img src="images/femaleProfile.png" height="100px" width="100px"></a></th>
				</c:otherwise>
			</c:choose>
			<th align="center"><h1>Welcome<c:out value=" ${user.userName}"/>!<img src="images/welcome.png" height="70px" width="70px"></h1></th>
			<th align="right"><a href="logoutUser"><img src="images/logout.png" title="log out" height="70px" width="70px"></a></th>
		</tr>
	</table>
	<div align="center">
		<table style="padding-bottom:50">
			<tr>
				<th width="300">
					<img src="images/search.png" height="50px" width="50px">
					<input type="search" size="30" id="mySearch" placeholder="find a joke ...">
				</th>
				<th width="300">
					<a href="newJoke"><img src="images/newJoke.png" title="post a new joke" height="50px" width="50px"></a>
				</th>
			</tr>
		</table>
	</div>
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
                        <a href="modifyJoke?jokeId=<c:out value='${joke.jokeId}' />&postUserId=<c:out value='${joke.postUserId}' />"><img src="images/edit.png" height="30%" width="30%"></a>
                    </td>
                    <td align="center">
                        <a href="deleteJoke?jokeId=<c:out value='${joke.jokeId}' />&postUserId=<c:out value='${joke.postUserId}' />"><img src="images/trash.png" height="30%" width="30%"></a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>