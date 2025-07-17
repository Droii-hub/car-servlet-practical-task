package com.walking.carpractice.service;

import com.walking.carpractice.exception.SerializationException;
import com.walking.carpractice.mapper.CarMapper;
import com.walking.carpractice.mapper.JsonMapper;
import com.walking.carpractice.model.Car;
import com.walking.carpractice.model.CarIdentifier;
import com.walking.carpractice.repository.CarRepository;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class CarService {
    private static CarService instance;
    private CarService(DataSource dataSource){
        this.cars=new ConcurrentHashMap<>();
        this.lock=new ReentrantLock();
        this.mapper=new JsonMapper();
        this.repository=new CarRepository(new CarMapper(), dataSource);
    }

    public static CarService getInstance(DataSource dataSource){
        if (instance==null){
            instance=new CarService(dataSource);
        }
        return instance;
    }
    private final ConcurrentHashMap<CarIdentifier, Car> cars;
    private final ReentrantLock lock;
    private final JsonMapper mapper;
    private final CarRepository repository;


    public String addCar(String body) throws SerializationException {
        lock.lock();
        var car=repository.create(mapper.deserializeCar(body));
        cars.put(car.getIdentifier(),car);
        lock.unlock();
        return mapper.serializeCar(cars.get(car.getIdentifier()));
    }

    public String getCar(String body) throws SerializationException{
        Car car=cars.get(mapper.deserializeIdentifier(body));
        if (car!=null) return mapper.serializeCar(car);

        lock.lock();
        var dbCar=repository.read(mapper.deserializeIdentifier(body));
        cars.put(dbCar.getIdentifier(), dbCar);
        lock.unlock();
        return mapper.serializeCar(cars.get(dbCar.getIdentifier()));
    }

    public String updateCar(String body) throws SerializationException{
        lock.lock();
        var car=repository.update(mapper.deserializeCar(body));
        cars.put(car.getIdentifier(),car);
        lock.unlock();
        return mapper.serializeCar(cars.get(car.getIdentifier()));
    }

    public String deleteCar(String body) throws  SerializationException{
        lock.lock();
        var car=repository.delete(mapper.deserializeCar(body));
        cars.remove(car.getIdentifier());
        lock.unlock();
        return mapper.serializeCar(car);
    }
}
