package com.walking.carpractice.service;

import com.walking.carpractice.mapper.CarMapper;
import com.walking.carpractice.model.Car;
import com.walking.carpractice.model.CarIdentifier;
import com.walking.carpractice.repository.CarRepository;

import javax.sql.DataSource;

public class CarService {
    private static CarService instance;
    private CarService(DataSource dataSource){
        this.repository=new CarRepository(new CarMapper(), dataSource);
    }

    public static CarService getInstance(DataSource dataSource){
        if (instance==null){
            instance=new CarService(dataSource);
        }
        return instance;
    }
    private final CarRepository repository;


    public Car addCar(Car car){
        return  repository.create(car);
    }

    public Car getCar(CarIdentifier carIdentifier){
        return repository.read(carIdentifier);
    }

    public Car updateCar(Car car){
        return repository.update(car);
    }

    public void deleteCar(CarIdentifier carIdentifier){
        repository.delete(carIdentifier);
    }
}
