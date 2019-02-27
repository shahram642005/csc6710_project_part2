<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<c:if test="${joke != null}">
    <title>create a joke!</title>
</c:if>
<c:if test="${joke == null}">
    <title>Modify your joke!</title>
</c:if>
</head>
<body>
	<h1  style="text-align:center">Post your Joke!</h1>
    <div align="center">
        <c:if test="${joke != null}">
            <form action="updateJoke" method="post">
        </c:if>
        <c:if test="${joke == null}">
            <form action="postJoke" method="post">
        </c:if>
        <table border="1" cellpadding="5">
            <caption>
                <h3>
                    <c:if test="${joke != null}">
                        Please modify your joke:
                    </c:if>
                    <c:if test="${joke == null}">
                        Please insert your joke:
                    </c:if>
                </h3>
            </caption>
                <c:if test="${joke != null}">
                    <input type="hidden" name="jokeId" value="<c:out value='${joke.jokeId}' />" />
                    <input type="hidden" name="userId" value="<c:out value='${joke.postUserId}' />" />
                </c:if> 
            <tr>
                <th>Title</th>
                <td>
                    <input type="text" name="title" size="45"
                            value="<c:out value='${joke.jokeTitle}' />"
                        />
                </td>
            </tr>
            <tr>
                <th>Description</th>
                <td>
                    <input type="text" name="description" size="45"
                            value="<c:out value='${joke.jokeText}' />"
                    />
                </td>
            </tr>
            <tr>
            <c:if test="${joke != null}">
                <td colspan="2" align="center">
                    <input type="submit" value="save" />
                </td>
            </c:if>
            <c:if test="${joke == null}">
                <td colspan="2" align="center">
                    <input type="submit" value="post" />
                </td>
            </c:if>
            </tr>
        </table>
        </form>
    </div>
</body>
</html>