package com.hotking.web;

import com.hotking.entity.Note;
import com.hotking.entity.QuoteNote;
import com.hotking.entity.Tag;
import com.hotking.repository.TagRepository;
import com.hotking.service.NoteService;
import com.hotking.service.TagService;
import com.hotking.util.JspUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/createNote")
public class CreateNoteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Tag> tags = TagService.getAllTags();
        req.setAttribute("allTags", tags);
        List<String> sourceTypes = Arrays.stream(QuoteNote.SourceType.values())
                        .map(Enum::name)
                        .toList();

        req.setAttribute("sourceTypes", sourceTypes);
        req.getRequestDispatcher(JspUtil.getPath("createNote"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String url = req.getParameter("url");
        String author = req.getParameter("author");
        String sourceName = req.getParameter("sourceName");
        String sourceType = req.getParameter("sourceType");
        List<Integer> tagsId = new ArrayList<>();
        if(req.getParameter("tags") != null) {
            tagsId = Arrays.stream(req.getParameterValues("tags"))
                    .map(Integer::parseInt)
                    .toList();
        }
        System.out.println("======================================");
        System.out.println(title);
        System.out.println(content);
        System.out.println( sourceType);
        System.out.println("======================================");
        NoteService.saveNote(title, content, url, author, sourceName, sourceType, tagsId);
        resp.sendRedirect("/notes");
    }
}
