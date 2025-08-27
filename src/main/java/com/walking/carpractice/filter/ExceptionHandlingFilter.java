package com.walking.carpractice.filter;

import com.walking.carpractice.ApplicationException;
import com.walking.carpractice.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ExceptionHandlingFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(req, resp);
        } catch (ApplicationException e) {
            handleException(req, resp, e);
        } catch (Exception e){
            handleUnknownException(req, resp, e);
        }
    }

    private void handleUnknownException(HttpServletRequest req, HttpServletResponse res, Exception e) throws IOException {
        res.setStatus(500);
        res.getOutputStream().print("Unknown error");
    }

    private void handleException(HttpServletRequest req, HttpServletResponse resp, ApplicationException e) throws ServletException, IOException {
        if (e.getErrorCode()== ErrorCode.NOT_FOUND){
            req.getRequestDispatcher("./notFound").forward(req,resp);
            return;
        }
        resp.setStatus(e.getErrorCode().getHttpCode());
        resp.getOutputStream().print(e.getMessage());
    }
}
