package com.walking.carpractice.repository;

import com.walking.carpractice.PasswordProvider;
import com.walking.carpractice.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepository {
    private final DataSource dataSource;
    private final Logger log = LogManager.getLogger(UserRepository.class);

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void create(User user) {
        String sql = "insert into car_user values(?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, PasswordProvider.hashPassword(user.getPassword()));
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void update(User user) {
        String sql = "update car_user set password=? where email=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(2, user.getEmail());
            statement.setString(1, PasswordProvider.hashPassword(user.getPassword()));
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String passwordByEmail(String email){
        String sql= "select password from car_user where email=?";
        try(Connection connection=dataSource.getConnection();
            PreparedStatement statement=connection.prepareStatement(sql)){
            statement.setString(1, email);
            var rs=statement.executeQuery();
            if(!rs.next())
                return  null;
            return rs.getString("password");
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void delete (String email){
        String sql = "delete from car_user where email=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
