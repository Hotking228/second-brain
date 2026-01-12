<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <style>
            body{
                background-color: #f8f9fa;
                font-family: sans-serif;
                line-height: 1.6;
                color: #333;
                padding: 20px;
                max-width: 800px;
                margin: 0 auto;
            }

            .note-card{
                border: 1px solid #e0e0e0;
                border-radius: 8px;
                padding: 20px;
                margin-bottom: 20px;
                background-color: #fff;
            }

            .title-input{
                width: 100%;
                font-size: 24px;
                font-weight: bold;
                color: #2c2e50;
                border: none;
                outline: none;
                background: transparent;
                padding: 5px 0;
            }

            .title-input:focus{
                border-bottom: 2px solid #3498db;
            }

            .content-text{
                width: 100%;
                min-height: 200px;
                border:none;
                outline: none;
                background: transparent;

                font-size: 16px;
                color: #333;

                padding: 10px;
                resize: vertical;

                overflow-y: auto;
            }

            .content-text:focus{
                border-left: 3px solid #3498db;
                padding-left: 5px;
            }

            .content-text::placeholder{
                color: #aaa;
                font-style: italic;
            }

            .default-text{
                width: 100%;
                border:none;
                outline: none;
                background: transparent;

                font-size: 16px;
                color: #333;

                padding: 10px;
            }

            .default-text:focus{
                border-left: 3px solid #3498db;
                padding-left: 5px;
            }

            .default-text::placeholder{
                color: #aaa;
                font-style: italic;
            }

            .create-note-button{
                margin-top: 20px;
                outline: none;
                border: 1px solid #e0e0e0;
                border-radius: 8px;
                padding: 5px;
                margin-bottom: 15px;
            }

            .create-note-button:focus{
                background-color: #3498db;
            }

            .tag-section{

            }

            .section-label{

            }

            .tag-checkbox-container{
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
                margin-top: 10px;
            }

            .tag-option{
                display: flex;
                align-items: center;
                padding: 6px 12px;
                background-color: #f0f0f0;
                border-radius: 20px;
                border: 1px solid #ddd;
                cursor: pointer;
                transition: all 0.2s;
            }

            .tag-option:hover{
                background-color: #e0e0e0;
            }

            .tag-color-dot{
                width: 12px;
                height: 12px;
                border-radius: 50%;
                margin-right: 6px;
                display: inline-block;
            }

            .tag-name{
                margin-right: 5px;
            }

            .tag-checkbox {
                display: none; /* Скрываем стандартный чекбокс */
            }


            .tag-checkbox:checked + .tag-color-dot {
                outline: 2px solid #3498db;
                outline-offset: 2px;
            }

            .tag-checkbox:checked + .tag-color-dot + .tag-name {
                font-weight: bold;
                color: #3498db;
            }

            .back-button{
                color: #3498db;
                padding-top: 20px;
            }

            .tag-option input:checked ~ .tag-color-dot {
                outline: 2px solid #3498db;
                outline-offset: 2px;
            }

            .select-source-type{
                border-radius: 20px;
            }

        </style>
    </head>

    <body>
        <form action="/createNote" method="post">
            <div class="note-card">
                <label for="titleId">
                    <input type="text" name="title" id="titleId" class="title-input" placeholder="Title...">
                </label>

                <label for="contentId">
                    <textarea name="content" id="contentId" placeholder="Note text..." class="content-text"></textarea>
                </label>

                <label for="urlId">
                    <input type="text" name="url" id="urlId" class="default-text" placeholder="URL...">
                </label>

                <label for="authorId">
                    <input type="text" name="author" id="authorId" class="default-text" placeholder="Author...">
                </label>

                <label for="sourceNameId">
                    <input type="text" name="sourceName" id="sourceNameId" class="default-text" placeholder="Source name...">
                </label>

                <label for="sourceTypeId">
                    <select name="sourceType" id="sourceTypeId" class="select-source-type">
                        <c:forEach items="${requestScope.sourceTypes}" var="sourceType">
                            <option value="${sourceType}">${sourceType}</option>
                        </c:forEach>
                    </select>
                </label>

                <div class="tag-section">
                    <label class="section-label">Select tags:</label>
                    <div class="tag-checkbox-container">
                        <c:forEach var="tag" items="${requestScope.allTags}">
                            <label for="tag${tag.id}" class="tag-option">
                                <input type="checkbox" name="tags" value="${tag.id}" class="tag-checkbox" id="tag${tag.id}">
                                <span class="tag-color-dot" style="background-color: ${tag.color.hex}"></span>
                                <span class="tag-name">${tag.name}</span>
                            </label>
                        </c:forEach>
                    </div>
                </div>

                <button type="submit" class="create-note-button">Create</button>
            </div>
        </form>

            <a href="javascript:history.back()" class="back-button">
                ← Back to Notes
            </a>
    </body>
</html>
