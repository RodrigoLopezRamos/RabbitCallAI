package com.analia.web.util;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;

public class AnaliaHttpServletResponseWrapper extends HttpServletResponseWrapper {
    private int httpCodeStatus;

    public AnaliaHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }


    public void sendError(int sc) throws IOException {
        httpCodeStatus = sc;
        super.sendError(sc);
    }


    public void sendError(int sc, String msg) throws IOException {
        httpCodeStatus = sc;
        super.sendError(sc, msg);
    }

    public int getStatus() {
        return httpCodeStatus;
    }


    public void setStatus(int sc) {
        httpCodeStatus = sc;
        super.setStatus(sc);
    }
}
