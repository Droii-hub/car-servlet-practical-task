package com.walking.carpractice.controller;

import com.walking.carpractice.PasswordProvider;
import com.walking.carpractice.model.User;
import com.walking.carpractice.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(){
        userService=(UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user=(User) req.getAttribute("pojoRequestBody");
        String password = userService.readPasswordByEmail(user.getEmail());
        if (password==null){
            resp.sendError(401);
            return;
        }
        if (!PasswordProvider.checkPassword(user.getPassword(), password)){
            resp.sendError(401);
            return;
        }
        HttpSession session=req.getSession();
        session.setAttribute("email",user.getEmail());
        resp.setStatus(200);
    }
}
