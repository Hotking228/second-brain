<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>

    <body>
        <h1>Notes:</h1>
        <c:forEach var="note" items="${requestScope.notes}">
            <a href="/note?noteId=${note.id}">
                ${note.title}<br>
                ${note.createdAt}<br>
            </a><br>
        </c:forEach>
    </body>
</html>
