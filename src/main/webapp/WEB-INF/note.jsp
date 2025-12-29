<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${requestScope.note.title}</title>

    <!-- Простые стили в том же стиле -->
    <style>
        /* Основные стили страницы */
        body {
            font-family: sans-serif;
            line-height: 1.6;
            color: #333;
            background-color: #f8f9fa;
            padding: 20px;
            max-width: 800px;
            margin: 0 auto;
        }

        /* Карточка заметки - как в списке, но побольше */
        .note-card {
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            background-color: #fff;
        }

        /* Заголовок заметки */
        .note-title {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 15px;
            color: #2c2e50;
        }

        /* Мета-информация */
        .note-meta {
            font-size: 14px;
            color: #7f8c8d;
            margin-bottom: 15px;
            padding-bottom: 15px;
            border-bottom: 1px solid #eee;
        }

        /* Содержимое заметки */
        .note-content {
            font-size: 16px;
            margin-bottom: 20px;
            line-height: 1.6;
            white-space: pre-wrap;
            word-wrap: break-word;
        }

        /* Специальная информация для Article/Quote */
        .special-info {
            background-color: #f8f9fa;
            border-radius: 6px;
            padding: 15px;
            margin-top: 20px;
            border-left: 4px solid #3498db;
        }

        /* Элементы специальной информации */
        .info-item {
            margin-bottom: 8px;
            font-size: 14px;
        }

        /* Ссылки */
        .info-item a {
            color: #3498db;
            text-decoration: none;
        }

        .info-item a:hover {
            text-decoration: underline;
        }

        /* Кнопка назад */
        .back-link {
            display: inline-block;
            margin-top: 15px;
            color: #3498db;
            text-decoration: none;
            font-weight: 500;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body>
<!-- Основная карточка заметки -->
<div class="note-card">

    <!-- Заголовок -->
    <h1 class="note-title">
        ${requestScope.note.title}
    </h1>

    <!-- Мета-информация -->
    <div class="note-meta">
        Created at: ${requestScope.note.createdAt}<br>
        Updated at: ${requestScope.note.updatedAt}
    </div>

    <!-- Содержимое -->
    <div class="note-content">
        ${requestScope.note.content}
    </div>

    <!-- Article информация -->
    <c:if test="${not empty requestScope.articleNote}">
        <div class="special-info">
            <div class="info-item">
                <strong>Article URL:</strong>
                <a href="${requestScope.articleNote.url}" target="_blank">
                        ${requestScope.articleNote.url}
                </a>
            </div>
        </div>
    </c:if>

    <!-- Quote информация -->
    <c:if test="${not empty requestScope.quoteNote}">
        <div class="special-info">
            <div class="info-item">
                <strong>Author:</strong> ${requestScope.quoteNote.authorName}
            </div>
            <div class="info-item">
                <strong>Source:</strong> ${requestScope.quoteNote.sourceName}
            </div>
            <c:if test="${not empty requestScope.quoteNote.url}">
                <div class="info-item">
                    <strong>URL:</strong>
                    <a href="${requestScope.quoteNote.url}" target="_blank">
                            ${requestScope.quoteNote.url}
                    </a>
                </div>
            </c:if>
        </div>
    </c:if>

</div>

<!-- Простая ссылка назад -->
<a href="javascript:history.back()" class="back-link">
    ← Back to Notes
</a>

</body>
</html>