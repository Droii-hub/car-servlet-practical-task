package com.walking.carpractice.repository;

import com.walking.carpractice.mapper.CarMapper;
import com.walking.carpractice.model.Car;
import com.walking.carpractice.model.CarIdentifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRepository {

    private final CarMapper mapper;
    private final DataSource dataSource;
    private final Logger log= LogManager.getLogger(CarRepository.class);

    public CarRepository(CarMapper mapper, DataSource dataSource){
        this.dataSource=dataSource;
        this.mapper=mapper;
    }

    public Car create(Car car){
        String sql= "insert into car values(?, ?, ?, ?, ?, ?)";
        try(Connection connection=dataSource.getConnection();
            PreparedStatement statement=connection.prepareStatement(sql)){
            statement.setInt(1, car.getIdentifier().getYear());
            statement.setString(2, car.getIdentifier().getNumber());
            statement.setString(3, car.getColor());
            statement.setString(4, car.getBrand());
            statement.setString(5, car.getModel());
            statement.setBoolean(6, car.isActualTechnicalInspection());
            statement.executeUpdate();
            return car;
        } catch (SQLException e){
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Car read(CarIdentifier id){
        String sql="select * from car where creation_year=? and number=?";
        try(Connection connection=dataSource.getConnection();
            PreparedStatement statement=connection.prepareStatement(sql)){
            statement.setInt(1, id.getYear());
            statement.setString(2, id.getNumber());
            ResultSet rs=statement.executeQuery();
            return mapper.map(rs);
        } catch (SQLException e){
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Car update(Car car){
        String sql= """
                update car set color=?,
                ActualTechnicalInspection=?
                where creation_year=? and number=?""";
        try(Connection connection=dataSource.getConnection();
            PreparedStatement statement=connection.prepareStatement(sql)){
                statement.setString(1, car.getColor());
                statement.setBoolean(2, car.isActualTechnicalInspection());
                statement.setInt(3, car.getIdentifier().getYear());
                statement.setString(4, car.getIdentifier().getNumber());
                statement.executeUpdate();
                return car;
        } catch (SQLException e){
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Car delete(Car car){
        String sql="delete from car where creation_year=? and number=?";
        try(Connection connection=dataSource.getConnection();
            PreparedStatement statement=connection.prepareStatement(sql)){
                statement.setInt(1, car.getIdentifier().getYear());
                statement.setString(2, car.getIdentifier().getNumber());
                statement.executeUpdate();
                return car;
        } catch (SQLException e){
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
