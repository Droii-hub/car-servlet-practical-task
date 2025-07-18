package com.walking.carpractice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walking.carpractice.service.CarService;
import com.walking.carpractice.service.UserService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class InitParamsListener implements ServletContextListener {
    private final Logger log= LogManager.getLogger(InitParamsListener.class);
    private final String HIKARI_PATH="/WEB-INF/hikari.properties";
    @Override
    public void contextInitialized(ServletContextEvent event){
        try {
            ServletContext context = event.getServletContext();
            HikariConfig config = new HikariConfig(getHikariProperties(context));

            HikariDataSource dataSource = new HikariDataSource(config);

            FluentConfiguration flywayConfiguration = Flyway.configure()
                    .dataSource(dataSource).baselineOnMigrate(true);
            Flyway flyway = flywayConfiguration.load();
            flyway.migrate();
            context.setAttribute("dbConnection", dataSource);
            context.setAttribute("carService", CarService.getInstance(dataSource));
            context.setAttribute("userService", UserService.getInstance(dataSource));
            context.setAttribute("objectMapper", new ObjectMapper());
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private Properties getHikariProperties(ServletContext context){
        try(InputStream inputStream=context.getResourceAsStream(HIKARI_PATH)) {
            if (inputStream==null){
                throw new RuntimeException("Can't find config file: "+HIKARI_PATH);
            }
            Properties properties=new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event){
        var context=event.getServletContext();
        HikariDataSource connection=(HikariDataSource)context.getAttribute("dbConnection");
        connection.close();
    }
}
