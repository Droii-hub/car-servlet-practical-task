package com.walking.carpractice.controller;

import com.walking.carpractice.model.Car;
import com.walking.carpractice.model.CarIdentifier;
import com.walking.carpractice.service.CarService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

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
        Car car=(Car)req.getAttribute("pojoRequestBody");
        if (car==null){
            resp.setStatus(400);
            return;
        }
        try {
            var answer = carService.addCar(car);
            req.setAttribute("pojoResponseBody", answer);
            resp.setStatus(201);
        } catch (Exception e){
            resp.setStatus(500);
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        CarIdentifier carIdentifier=(CarIdentifier)req.getAttribute("pojoRequestBody");
        if (carIdentifier==null){
            resp.setStatus(400);
            return;
        }
        try {
            var answer = carService.getCar(carIdentifier);
            req.setAttribute("pojoResponseBody", answer);
            resp.setStatus(200);
        } catch (Exception e){
            resp.setStatus(500);
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Car car=(Car)req.getAttribute("pojoRequestBody");
        if (car==null){
            resp.setStatus(400);
            return;
        }
        try {
            var answer = carService.updateCar(car);
            req.setAttribute("pojoResponseBody", answer);
            resp.setStatus(200);
        } catch (Exception e){
            resp.setStatus(500);
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        CarIdentifier carIdentifier=(CarIdentifier)req.getAttribute("pojoRequestBody");
        if (carIdentifier==null){
            resp.setStatus(400);
            return;
        }
        try {
            carService.deleteCar(carIdentifier);
            resp.setStatus(200);
        } catch (Exception e){
            resp.setStatus(500);
            log.error(e.getMessage());
        }
    }
}
