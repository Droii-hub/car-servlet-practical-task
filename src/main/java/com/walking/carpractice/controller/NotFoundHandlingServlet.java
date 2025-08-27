package com.walking.carpractice.controller;

import com.walking.carpractice.ApplicationException;
import com.walking.carpractice.ErrorCode;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NotFoundHandlingServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp){
        throw new ApplicationException(ErrorCode.NOT_FOUND);
    }
}
