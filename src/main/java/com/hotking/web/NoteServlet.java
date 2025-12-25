package com.hotking.web;

import com.hotking.entity.ArticleNote;
import com.hotking.entity.Note;
import com.hotking.entity.PersonalNote;
import com.hotking.entity.QuoteNote;
import com.hotking.service.NoteService;
import com.hotking.util.JspUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/note")
public class NoteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer noteId = Integer.parseInt(req.getParameter("noteId"));
        Note note = NoteService.getNoteById(noteId);
        req.setAttribute("note", note);
        System.out.println(note instanceof PersonalNote);
        System.out.println(note instanceof QuoteNote);
        System.out.println(note instanceof ArticleNote);
        if(note instanceof PersonalNote personalNote){
            req.setAttribute("personalNote", personalNote);
        } else if(note instanceof QuoteNote quoteNote){
            var a = quoteNote.getAuthorName();
            req.setAttribute("quoteNote", quoteNote);
        } else if(note instanceof ArticleNote articleNote){
            var u = articleNote.getUrl();
            req.setAttribute("articleNote", articleNote);
        }

        req.getRequestDispatcher(JspUtil.getPath("note"))
                .forward(req, resp);
    }
}
