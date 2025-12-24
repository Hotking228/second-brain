package com.hotking.util;

public class JspUtil {

    private static final String JSP_PATH = "/WEB-INF/%s.jsp";

    public static String getPath(String filename){
        return JSP_PATH.formatted(filename);
    }
}
