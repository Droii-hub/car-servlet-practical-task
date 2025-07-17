package com.walking.carpractice.mapper;

import com.walking.carpractice.model.Car;
import com.walking.carpractice.model.CarIdentifier;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarMapper {
    public Car map(ResultSet rs) throws SQLException {
        rs.next();
        return new Car(new CarIdentifier(rs.getString("number"), rs.getInt("creation_year")),
                rs.getString("brand"), rs.getString("model"), rs.getString("color"), rs.getBoolean("actualTechnicalInspection"));
    }
}
