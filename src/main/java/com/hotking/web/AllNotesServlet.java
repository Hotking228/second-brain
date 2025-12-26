package com.hotking.web;

import com.hotking.dto.ShowAllNotesDto;
import com.hotking.entity.Note;
import com.hotking.entity.Tag;
import com.hotking.service.NoteService;
import com.hotking.service.TagService;
import com.hotking.util.JspUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/notes")
public class AllNotesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Note> notes = NoteService.getAllNotes();
        List<Tag> tags = TagService.getAllTags();
        req.setAttribute("allTags", tags);
        req.setAttribute("notes", notes);
        req.getRequestDispatcher(JspUtil.getPath("notes"))
                .forward(req, resp);
    }
}
