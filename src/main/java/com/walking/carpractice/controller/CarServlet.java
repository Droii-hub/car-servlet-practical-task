package com.walking.carpractice.controller;

import com.walking.carpractice.exception.SerializationException;
import com.walking.carpractice.service.CarService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/car")
public class CarServlet extends HttpServlet {
    private final Logger log= LogManager.getLogger(CarServlet.class);
    private CarService carService;

    @Override
    public void init(){
        carService=(CarService) getServletContext().getAttribute("carService");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        if (req.getContentType().equals("application/json")) {
            try {
                resp.setStatus(200);
                var writer = resp.getWriter();
                writer.print(carService.addCar(req.getReader().lines().collect(Collectors.joining())));
            } catch (SerializationException e) {
                resp.setStatus(422);
                resp.getWriter().println("Wrong data in body");
            }
        } else {
            resp.setStatus(415);
            resp.getWriter().println("Expected application/json content type");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        if (req.getContentType().equals("application/json")) {
            try {
                resp.setStatus(200);
                var writer = resp.getWriter();
                writer.print(carService.getCar(req.getReader().lines().collect(Collectors.joining())));
            } catch (SerializationException e) {
                resp.setStatus(422);
                resp.getWriter().println("Wrong data in body");
            }
        } else {
            resp.setStatus(415);
            resp.getWriter().println("Expected application/json content type");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        if (req.getContentType().equals("application/json")) {
            try {
                resp.setStatus(200);
                var writer = resp.getWriter();
                writer.print(carService.updateCar(req.getReader().lines().collect(Collectors.joining())));
            } catch (SerializationException e) {
                resp.setStatus(422);
                resp.getWriter().println("Wrong data in body");
            }
        } else {
            resp.setStatus(415);
            resp.getWriter().println("Expected application/json content type");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        if (req.getContentType().equals("application/json")) {
            try {
                resp.setStatus(200);
                var writer = resp.getWriter();
                writer.print(carService.deleteCar(req.getReader().lines().collect(Collectors.joining())));
            } catch (SerializationException e) {
                resp.setStatus(422);
                resp.getWriter().println("Wrong data in body");
            }
        } else {
            resp.setStatus(415);
            resp.getWriter().println("Expected application/json content type");
        }
    }
}
