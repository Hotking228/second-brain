package com.hotking.web;

import com.hotking.entity.Tag;
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

@WebServlet("/createTag")
public class CreateTagServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> tags = TagService.getAllTags().stream()
                        .map(Tag::getName)
                        .toList();
        req.setAttribute("tags", tags);
        List<Tag.Color> colors = Arrays.stream(Tag.Color.values()).toList();
        req.setAttribute("colors", colors);
        req.getRequestDispatcher(JspUtil.getPath("createTag"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        Tag parent = TagService.getByName(req.getParameter("parentTag"));
        String colorName = req.getParameter("color");
        Tag.Color tagColor = Tag.Color.PRIMARY;
        for (Tag.Color color : Tag.Color.values()) {
            if(colorName.equals(color.name())){
                tagColor = color;
            }
        }
        TagService.save(Tag.builder()
                .color(tagColor)
                .name(name)
                .description(description)
                .parent(parent)
                .build());
        resp.sendRedirect("/notes");
    }
}
