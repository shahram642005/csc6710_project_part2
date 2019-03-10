<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Review</title>
</head>
<body>
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
	<table style="padding-bottom:50">
	<col width="600">
	<col width="600">
	<col width="600">
		<tr>
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
			<th>
				<div align="center">
					<table>
						<tr>
							<th><h2><c:out value='${formText}' /></h2></th>
							<th><img src="images/reviewer.png" height="70px" width="70px"></th>
						</tr>
					</table>
				</div>
			</th>
			<th align="right"><a href="logoutUser"><img src="images/logout.png" title="log out" height="70px" width="70px"></a></th>
		</tr>
	</table>
    <div align="center">
    	<form action=<c:out value="${formAction}" /> method="post">
	        <table>
	        	<tr>
	        		<td>
		                <c:if test="${joke != null}">
		                    <input type="hidden" name="jokeId" value="<c:out value='${joke.jokeId}' />" />
		                    <input type="hidden" name="scoreStr" value="<c:out value='${scoreStr}' />" />
		                </c:if> 
	                </td>
				</tr>
	            <tr><td>Score:</td></tr>
	            <tr>
	                <td>
	                	<a href="newReview?score=poor&jokeId=<c:out value='${joke.jokeId}' />"><img src="images/star.png" title="poor" height="30px" width="30px"></a>
               			<c:choose>
							<c:when test="${score > 1}">
	                			<a href="newReview?score=fair&jokeId=<c:out value='${joke.jokeId}' />"><img src="images/star.png" title="fair" height="30px" width="30px"></a>
                			</c:when>
                			<c:otherwise>
                				<a href="newReview?score=fair&jokeId=<c:out value='${joke.jokeId}' />"><img src="images/emptyStar.png" title="fair" height="30px" width="30px"></a>
                			</c:otherwise>
               			</c:choose>
               			<c:choose>
							<c:when test="${score > 2}">
	                			<a href="newReview?score=good&jokeId=<c:out value='${joke.jokeId}' />"><img src="images/star.png" title="good" height="30px" width="30px"></a>
                			</c:when>
                			<c:otherwise>
                				<a href="newReview?score=good&jokeId=<c:out value='${joke.jokeId}' />"><img src="images/emptyStar.png" title="good" height="30px" width="30px"></a>
                			</c:otherwise>
               			</c:choose>
               			<c:choose>
							<c:when test="${score > 3}">
	                			<a href="newReview?score=excellent&jokeId=<c:out value='${joke.jokeId}' />"><img src="images/star.png" title="excellent" height="30px" width="30px"></a>
                			</c:when>
                			<c:otherwise>
                				<a href="newReview?score=excellent&jokeId=<c:out value='${joke.jokeId}' />"><img src="images/emptyStar.png" title="excellent" height="30px" width="30px"></a>
                			</c:otherwise>
               			</c:choose>
	                </td>
	            </tr>
	            <tr><td>Remarks:</td></tr>
	            <tr>
	                <td><input type="text" name="remarks" size="50" autofocus placeholder="remarks ..." value="<c:out value='${remarks}' />"/></td>
	            </tr>
	            <tr>
		            <td ><input type="submit" value="<c:out value='${buttonText}' />" /></td>
	            </tr>
	        </table>
        </form>
    </div>
</body>
</html>