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

            .card{
                border: 1px solid #e0e0e0;
                border-radius: 8px;
                padding: 20px;
                margin-bottom: 20px;
                background-color: #fff;
            }

            .name-input{
                width: 100%;
                font-size: 24px;
                font-weight: bold;
                color: #2c2e50;
                border: none;
                outline: none;
                background: transparent;
                padding: 5px 0;
            }

            .name-input:focus{
                border-bottom: 2px solid #3498db;
            }

            .description-input{
                width: 100%;
                border:none;
                outline: none;
                background: transparent;

                font-size: 16px;
                color: #333;

                padding: 10px;
            }

            .description-input:focus{
                border-left: 1px solid #3498db;
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

            .back-button{
                color: #3498db;
                padding-top: 20px;
            }

            .select-parent{
                border-radius: 20px;
            }

            .tag-color-dot{
                width: 12px;
                height: 12px;
                border-radius: 50%;
                margin-right: 6px;
                display: inline-block;
            }

            .color-name{
                margin-right: 5px;
            }

        </style>
    </head>

    <body>
        <div class="card">
            <form action="/createTag" method="post">
                <label for="nameId">
                    <input type="text" name="name" placeholder="Tag name..." class="name-input">
                </label>

                <label for="descriptionId">
                    <input type="text" name="description" placeholder="Description..." class="description-input">
                </label>
                <br>

                <label for="parentTagId">Select parent:
                    <select name="parentTag" id="parentTagId" class="select-parent">
                        <c:forEach var="parentTag" items="${requestScope.tags}">
                            <option value="${parentTag}">${parentTag}</option>
                        </c:forEach>
                    </select>
                </label><br>

                <label for="colorId">Select color:<br>
                    <c:forEach var="color" items="${requestScope.colors}">
                        <label for="tag${color.hex}">
                            <input type="radio" name="color" value="${color.name()}" class="tag-checkbox" id="tag${color.hex}">
                            <span class="tag-color-dot" style="background-color: ${color.hex}"></span>
                            <span class="color-name">${color.name()}</span>
                        </label><br>
                    </c:forEach>
                </label>

                <button type="submit" class="create-note-button">Create</button>
            </form>

            <a href="javascript:history.back()" class="back-button">← Back to Notes</a>
        </div>
    </body>
</html>
