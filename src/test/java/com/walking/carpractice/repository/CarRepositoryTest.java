package com.walking.carpractice.repository;

import com.walking.carpractice.InitParamsListener;
import com.walking.carpractice.exception.SerializationException;
import com.walking.carpractice.mapper.JsonMapper;
import com.walking.carpractice.model.Car;
import jakarta.servlet.ServletContextEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CarRepositoryTest {

    @Mock
    private ServletContextEvent event;

    @InjectMocks
    private InitParamsListener listener;

    @Test
    public void serialize_success(){
        Car actual = null;
        String actualString=null;
        Car car=new Car("uq293i", 2022, "Lada", "Kalina", "White", true);
        JsonMapper mapper=new JsonMapper();
        try {
            actualString=mapper.serializeCar(car);
            String json = """
                    {
                      "brand" : "Lada",
                      "model" : "Kalina",
                      "color" : "White",
                      "actualTechnicalInspection" : true,
                      "identifier" : {
                        "number" : "uq293i",
                        "year" : 2022
                      }
                    }
                    """;
            System.out.println(actualString);
            actual =mapper.deserializeCar(json);
            
        } catch (SerializationException e) {
            System.out.println(e.getMessage());
        }
        assert actual != null;
        Assertions.assertEquals("White", actual.getColor());

    }
}
