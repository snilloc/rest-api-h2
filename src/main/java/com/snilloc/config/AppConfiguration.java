package com.snilloc.config;

import com.snilloc.dao.AdvertiserDao;
import com.snilloc.dao.impl.H2AdvertiserDaoImpl;
import com.snilloc.entity.Advertiser;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Application Configuration
 */
@PropertySource("classpath:application.yml")
@Configuration
@ConfigurationProperties
@ComponentScan("com.snilloc")
@Import( { SwaggerConfig.class})
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

    private static AdvertiserDao advertiserDao;

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

    @Bean
    public AdvertiserDao getAdvertiserDao() {
        if (advertiserDao == null) {
            try {
                advertiserDao = new H2AdvertiserDaoImpl(this.getConnection());
                advertiserDao.init();
                return advertiserDao;
            } catch (Exception ex) {
                //ex.printStackTrace();
                //throw ex;
                return advertiserDao;
            }
        } else {
            return advertiserDao;
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

    public void setAdvertiserDao(AdvertiserDao dao) {
        this.advertiserDao = dao;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    /*
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }*/
}
