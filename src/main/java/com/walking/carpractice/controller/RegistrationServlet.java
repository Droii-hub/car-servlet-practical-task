package com.walking.carpractice.controller;

import com.walking.carpractice.model.User;
import com.walking.carpractice.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(){
        userService=(UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user=(User) req.getAttribute("pojoRequestBody");
        if (user==null){
            resp.sendError(400);
            return;
        }
        try {
            userService.createUser(user);
            resp.setStatus(201);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("ERROR: duplicate"))
                resp.sendError(400,"This email already in use");
            if (e.getMessage().contains("violates check constraint"))
                resp.sendError(400,"Invalid email format");
            throw e;
        }
    }
}
