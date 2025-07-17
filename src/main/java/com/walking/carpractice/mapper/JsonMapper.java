package com.walking.carpractice.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.walking.carpractice.exception.SerializationException;
import com.walking.carpractice.model.Car;
import com.walking.carpractice.model.CarIdentifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class JsonMapper {
    private final ObjectMapper mapper;
    private final Logger log= LogManager.getLogger(JsonMapper.class);

    public JsonMapper(){
        this.mapper=new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public String serializeCar(Car car) throws SerializationException {
        try {
            return mapper.writeValueAsString(car);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new SerializationException(e.getMessage());
        }

    }

    public Car deserializeCar(String json) throws SerializationException{
       try{
           return mapper.readValue(json, Car.class);
       } catch (JsonProcessingException e) {
           log.error(e.getMessage());
           throw new SerializationException(e.getMessage());
       }
    }

    public CarIdentifier deserializeIdentifier(String json) throws SerializationException{
        try{
            return mapper.readValue(json, CarIdentifier.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new SerializationException(e.getMessage());
        }
    }
}
