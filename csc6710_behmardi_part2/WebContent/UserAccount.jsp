<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Welcome</title>
</head>
<body>
<!-- Check if user is logged in -->
<%
	if(session.getAttribute("userId") == null)
	{
		response.sendRedirect("Login.jsp");
	}
	else
	{
		int userId = Integer.parseInt(session.getAttribute("userId").toString());
	}
%>

	<!-- header icons: user picture, welcome note, logout; search jokes, add joke, list users (only root) -->
	<table style="padding-bottom:50">
	<col width="600">
	<col width="600">
	<col width="600">
	<col width="600">
	<col width="600">
		<tr >
			<c:choose>
				<c:when test="${userId == 1}">
					<th align="left"><a href="modifyUser?userId=1"><img src="images/admin.png" height="100px" width="100px"></a></th>
				</c:when>
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
			<th></th>
			<th>
				<div align="center">
					<table>
						<tr>
							<td><h2>Welcome<c:out value=" ${user.userName}"/>!</h2></td>
							<td><img src="images/welcome.png" height="70px" width="70px"></td>
						</tr>
					</table>
				</div>
			</th>
			<th></th>
			<th align="right"><a href="logoutUser"><img src="images/logout.png" title="log out" height="70px" width="70px"></a></th>
		</tr>
		<tr>
			<th></th>
			<th align="left">
				<table>
				<tr>
					<td><input type="radio" name="joketable" value="yourJokes" checked>your jokes
					<input type="radio" name="joketable" value="allJokes">all jokes
					<input type="radio" name="joketable" value="allJokes">search jokes</td>
			    </tr>
			    <tr>
			    	<td><input type="search" size="40" id="mySearch" placeholder="find a joke ..."></td>
					<td><a href="searchJoke"><img src="images/searchJoke.png" title="search joke tags" height="50px" width="50px"></a></td>
				</tr>
				</table>
			</th>
			<th align="center">
				<a href="newJoke"><img src="images/newJoke.png" title="post a new joke" height="50px" width="50px"></a>
			</th>
			<c:if test="${userId == 1}">
				<th align="right"><a href="listUsers"><img src="images/listUsers.png" title="list all users" height="50px" width="50px"></a></th>
			</c:if>
			<th></th>
		</tr>
	</table>
	
	<!-- msg on top of the tables -->
	<div>
		<h2 style="text-align:center">
		    <c:if test="${message != null && color != null}">
		        <font color=<c:out value="${color}"></c:out>><c:out value="${message}"></c:out></font>
		    </c:if>
		</h2>
    </div>
    
    <!-- listUser table (only root) -->
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
	                <th width="200" colspan="3">Actions</th>
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
	                    	<c:set var="isBanned" value="false" />
							<c:forEach var="bannedUser" items="${bannedUsers}">
							  <c:if test="${user.userId == bannedUser.userId}">
							    <c:set var="isBanned" value="true" />
							  </c:if>
							</c:forEach>
		                    <c:choose>
								<c:when test="${isBanned == 'true'}">
									<a href="unbanUser?userId=<c:out value='${user.userId}' />"><img src="images/banUser.png" title="unban user" height="30%" width="30%"></a>
								</c:when>
								<c:otherwise>
									<a href="banUser?userId=<c:out value='${user.userId}' />"><img src="images/unbanUser.png" title="ban user" height="30%" width="30%"></a>
								</c:otherwise>
							</c:choose>
	                    </td>
	                </tr>
	            </c:forEach>
	        </table>
        </c:if>
    </div>
    
    <!-- List all jokes -->
	<div align="center">
		<c:if test="${jokeList != null}">
	        <table border="1">
	            <tr>
	                <th width="20">ID</th>
	                <th width="100">Title</th>
	                <th width="500">Text</th>
	                <th width="100">Date</th>
	                <th width="100">Owner</th>
	                <th width="200" colspan="5">Actions</th>
	            </tr>
	            <c:forEach var="joke" items="${jokeList}">
	                <tr>
	                    <td align="center"><c:out value="${joke.jokeId}" /></td>
	                    <td align="center"><c:out value="${joke.jokeTitle}" /></td>
	                    <td align="center"><c:out value="${joke.jokeText}" /></td>
	                    <td align="center"><c:out value="${joke.jokePostDate}" /></td>
	                    <td align="center"><c:out value="${userDAO.getUser(joke.postUserId).userName}" /></td>
	                    <td align="center">
	                        <a href="modifyJoke?jokeId=<c:out value='${joke.jokeId}' />&postUserId=<c:out value='${joke.postUserId}' />"><img src="images/edit.png"  title="edit joke" height="30%" width="30%"></a>
	                    </td>
	                    <td align="center">
	                        <a href="deleteJoke?jokeId=<c:out value='${joke.jokeId}' />&postUserId=<c:out value='${joke.postUserId}' />"><img src="images/trash.png"  title="remove joke" height="30%" width="30%"></a>
	                    </td>
	                    <td align="center">
	                    	<c:set var="isStarUser" value="false" />
							<c:forEach var="friend" items="${friends}">
							  <c:if test="${friend.friendUserId == joke.postUserId}">
							    <c:set var="isStarUser" value="true" />
							  </c:if>
							</c:forEach>
	                    	<c:choose>
								<c:when test="${isStarUser == 'true'}">
									<a href="unstarUser?postUserId=<c:out value='${joke.postUserId}' />"><img src="images/starUser.png" title="remove friend" height="30%" width="30%"></a>
								</c:when>
								<c:otherwise>
									<a href="starUser?postUserId=<c:out value='${joke.postUserId}' />"><img src="images/unstarUser.png" title="add friend" height="30%" width="30%"></a>
								</c:otherwise>
							</c:choose>
	                    </td>
	                    <td align="center">
	                    	<c:set var="isStarJoke" value="false" />
							<c:forEach var="favoriteJoke" items="${favoriteJokes}">
							  <c:if test="${favoriteJoke.jokeId == joke.jokeId}">
							    <c:set var="isStarJoke" value="true" />
							  </c:if>
							</c:forEach>
		                    <c:choose>
								<c:when test="${isStarJoke == 'true'}">
									<a href="unstarJoke?jokeId=<c:out value='${joke.jokeId}' />"><img src="images/starJoke.png" title="remove favorite" height="30%" width="30%"></a>
								</c:when>
								<c:otherwise>
									<a href="starJoke?jokeId=<c:out value='${joke.jokeId}' />"><img src="images/unstarJoke.png" title="add favorite" height="30%" width="30%"></a>
								</c:otherwise>
							</c:choose>
	                    </td>
	                    <td align="center">
	                        <a href="newReview?jokeId=<c:out value='${joke.jokeId}' />"><img src="images/addReview.png"  title="add review" height="30%" width="30%"></a>
	                    </td>
	                </tr>
	            </c:forEach>
	        </table>
         </c:if>
    </div>
</body>
</html>