package com.hotking.web;

import com.hotking.entity.Tag;
import com.hotking.service.TagService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/tags")
public class AllTagsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Tag> tags = TagService.getAllTags();

        req.setAttribute("tags", tags);
    }
}
