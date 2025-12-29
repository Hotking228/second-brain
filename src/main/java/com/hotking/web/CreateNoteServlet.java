package com.hotking.web;

import com.hotking.entity.Tag;
import com.hotking.repository.TagRepository;
import com.hotking.service.TagService;
import com.hotking.util.JspUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/createNote")
public class CreateNoteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Tag> tags = TagService.getAllTags();
        req.setAttribute("allTags", tags);
        req.getRequestDispatcher(JspUtil.getPath("createNote"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        List<Integer> tagsId = Arrays.stream(req.getParameterValues("tags"))
                        .map(Integer::parseInt)
                        .toList();

        resp.sendRedirect("/notes");
    }
}
