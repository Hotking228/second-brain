<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Notes:</title>
        <style>
            .tag-cloud{
                margin-top: 40px;
                padding: 20px;
                border-top: 2px solid #eee;
            }

            .tag-cloud h3{
                margin-bottom: 15px;
                color: #333;
            }

            .tag-container{
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
                max-width: 100%;
            }

            .tag-item{
                display: inline-flex;
                align-items: center;
                padding: 6px 12px;
                background-color: #f0f0f0;
                border-radius: 20px;
                font-size: 14px;
                color: #333;
                text-decoration: none;
                transition: all 0.2s;
                border: 1px solid #ddd;
            }

            .tag-item:hover{
                background-color: #e0e0e0;
                transform: translateY(-2px);
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }

            .tag-color-dot{
                width: 12px;
                height: 12px;
                border-radius: 50%;
                margin-right: 6px;
                display: inline-block;
            }

            .tag-count{
                background-color: #ddd;
                border-radius: 10px;
                padding: 2px 6px;
                font-size: 12px;
            }

            .tag-name{
                margin-right: 5px;
            }

            .notes-container{
                margin-bottom: 30px;
            }

            .note-card{
                border:1px solid #e0e0e0;
                border-radius: 8px;
                padding: 15px;
                margin-bottom: 15px;
                background-color: #fff;
            }

            .note-title{
                font-size: 18px;
                font-weight: bold;
                margin-bottom: 10px;
                color: #2c2e50;
            }

            .note-meta{
                font-size: 12px;
                color:#7f8c8d;
                margin-bottom: 10px;
            }

            .note-tags{
                display: flex;
                flex-wrap: wrap;
                gap: 5px;
                margin-top: 10px;
            }

            .note-tag{
                padding: 3px 8px;
                background-color: #ecf0f1;
                border-radius: 12px;
                font-size: 12px;
            }
        </style>
    </head>

    <body>
        <div class="notes-container">
            <h1>Notes</h1>
            <c:forEach var="note" items="${requestScope.notes}">
                <div class="note-card">
                    <div class="note-title">
                        <a href="/note?noteId=${note.id}">${note.title}</a>
                    </div>

                    <div class="note-meta">
                        Created at: ${note.createdAt}
                        <c:if test="${not empty note.updatedAt}">
                            Updated at: ${note.updatedAt}
                        </c:if>
<%--                        Type:--%>
<%--                        <c:if test="${not empty requestScope.personalNote}">--%>
<%--                            Personal note--%>
<%--                        </c:if>--%>
<%--                        <c:if test="${not empty requestScope.articleNote}">--%>
<%--                            Article note--%>
<%--                        </c:if>--%>
<%--                        <c:if test="${not empty requestScope.quoteNote}">--%>
<%--                            Quote note--%>
<%--                        </c:if>--%>
                    </div>
                    <div class="note-content">
                        <c:choose>
                            <c:when test="${fn:length(note.content) < 200}">
                                ${fn:substring(note.content, 0, 200)}...
                            </c:when>
                            <c:otherwise>
                                ${note.content}
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <c:if test="${not empty note.tags}">
                        <div class="note-tags">
                            <c:forEach var="tag" items="${note.tags}">
                                <span class="note-tag" style="border-left: 3px solid ${tag.color.hex}">
                                        ${tag.name}
                                </span>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>
            </c:forEach>
        </div>
    <div class="tag-cloud">
        <h3>Tags: (${fn:length(allTags)})</h3>
        <div class="tag-container">
            <c:forEach var="tag" items="${allTags}">
                <a href="/notes?tagId=${tag.id}" class="tag-item"
                   title="${tag.description}">
                    <span class="tag-color-dot" style="background-color: ${tag.color.hex}"></span>
                    <span class="tag-name">${tag.name}</span>
<%--                    <span class="tag-count">${tag.notesCount}</span>--%>
                </a>
            </c:forEach>
        </div>
    </div>

    </body>
</html>
