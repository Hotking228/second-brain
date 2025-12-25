<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>Title</title>

    </head>

    <body>
        <h2>
            ${requestScope.note.title}
        </h2><br>
        ${requestScope.note.content}<br>
        Created at: ${requestScope.note.createdAt}<br>
        Updated at: ${requestScope.note.updatedAt}<br>

        <c:if test="${not empty requestScope.articleNote}">
            ${requestScope.articleNote.url}
        </c:if>

        <c:if test="${not empty requestScope.quoteNote}">
            Author: ${requestScope.quoteNote.authorName}<br>
            Source: ${requestScope.quoteNote.sourceName}<br>
            <c:if test="${not empty requestScope.quoteNote.url}">
                Url: ${requestScope.quoteNote.url}<br>
            </c:if>
        </c:if>
    </body>
</html>
