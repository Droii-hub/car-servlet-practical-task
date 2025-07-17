package com.walking.carpractice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Car {
    //    Уникальным идентификатором будет объект immutable класса CarIdentifier
    private final CarIdentifier identifier;

    private String color;
    private final String brand;
    private final String model;
    private boolean actualTechnicalInspection;

    @JsonCreator
    public Car(
            @JsonProperty("number") String number,
            @JsonProperty("year") int year,
            @JsonProperty("brand") String brand,
            @JsonProperty("model") String model,
            @JsonProperty("color") String color,
            @JsonProperty("actualTechnicalInspection") boolean actualTechnicalInspection) {
        this(new CarIdentifier(number, year), brand, model, color, actualTechnicalInspection);
    }

    public Car(CarIdentifier identifier, String brand, String model, String color, boolean actualTechnicalInspection) {
        this.identifier = identifier;
        this.brand=brand;
        this.model=model;
        this.color = color;
        this.actualTechnicalInspection = actualTechnicalInspection;
    }

    public CarIdentifier getIdentifier() {
        return identifier;
    }

    public String getBrand() {return brand;}

    public String getModel() {return model;}

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isActualTechnicalInspection() {
        return actualTechnicalInspection;
    }

    public void setActualTechnicalInspection(boolean actualTechnicalInspection) {
        this.actualTechnicalInspection = actualTechnicalInspection;
    }

    @Override
    public String toString() {
//        Метод formatted() у String позволяет создать строку с использованием спецификаторов
//        (как в System.out.printf()). До Java 15 вместо него использовался статический метод String.format().
//        Здесь он использован для большей наглядности кода, менее красиво, но с тем же успехом,
//        можно было обойтись обычной конкатенацией строк
        return """
                number: %s
                year: %d
                brand: %s
                model: %s
                color: %s
                actualTechnicalInspection: %s
                """.formatted(
                identifier.getNumber(), identifier.getYear(), brand, model, color,
                actualTechnicalInspection ? "actual" : "not actual");
    }
}