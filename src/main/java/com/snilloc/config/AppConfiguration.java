package com.snilloc.config;

import com.snilloc.dao.AdvertiserDao;
import com.snilloc.dao.impl.H2AdvertiserDaoImpl;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Application Configuration
 */
@PropertySource("classpath:application.yml")
@Configuration
@ConfigurationProperties
public class AppConfiguration {

    // From the YAML file
    @NotEmpty
    @Value("${driverClass}")
    private String driverClass;
    @NotEmpty
    @Value("${user}")
    private String user;
    @NotEmpty
    @Value("${password}")
    private String password;
    @NotEmpty
    @Value("${connection}")
    private String connection;

   // @JsonProperty
    @Bean
    public Connection getConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(connection, user, password);
        } catch (SQLException ex) {
            System.out.println("ERROR connection to DB");
        }
        return dbConnection;
    }

    public AdvertiserDao getAdvertiserDao() {
        try {
            return new H2AdvertiserDaoImpl(getConnection());
        } catch (Exception ex) {
            return null;
        }
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }
}
