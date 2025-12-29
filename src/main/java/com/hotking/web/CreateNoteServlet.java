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
}
