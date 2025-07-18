package com.walking.carpractice.controller;

import com.walking.carpractice.model.User;
import com.walking.carpractice.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(){
        userService=(UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if (method.equals("PATCH")) {
            this.doPatch(req, resp);
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user=(User) req.getAttribute("pojoRequestBody");
        if (user==null){
            resp.sendError(400);
            return;
        }
        HttpSession session=req.getSession(false);
        String email=(String) session.getAttribute("email");
        if(!email.equals(user.getEmail())){
            resp.sendError(401);
            return;
        }
        userService.updatePassword(user);
        resp.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp){
        HttpSession session=req.getSession(false);
        userService.deleteUser((String) session.getAttribute("email"));
        resp.setStatus(200);
    }

}
