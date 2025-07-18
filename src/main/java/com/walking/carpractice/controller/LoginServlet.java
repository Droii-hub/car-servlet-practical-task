package com.walking.carpractice.controller;

import com.walking.carpractice.PasswordProvider;
import com.walking.carpractice.model.User;
import com.walking.carpractice.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/auth")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(){
        userService=(UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user;
        if ("application/json".equals(req.getContentType())) {
            user = (User) req.getAttribute("pojoRequestBody");
        } else if ("application/x-www-form-urlencoded".equals(req.getContentType())) {
            String email=req.getParameter("email");
            String password=req.getParameter("password");
            if (email!=null&password!=null){
                user=new User();
                user.setEmail(email);
                user.setPassword(password);
            } else
                user=null;
        } else {
            handleError(req, resp);
            return;
        }
        if (user==null){
            handleError(req, resp);
            return;
        }
        String password = userService.readPasswordByEmail(user.getEmail());
        if (password==null){
            handleError(req, resp);
            return;
        }
        if (!PasswordProvider.checkPassword(user.getPassword(), password)){
            handleError(req, resp);
            return;
        }
        HttpSession session=req.getSession();
        session.setAttribute("email",user.getEmail());
        resp.setStatus(200);
    }
    
    private void handleError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(401);
        req.getRequestDispatcher("./login").forward(req,resp);
    }
}
