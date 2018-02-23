package com.snilloc.config;

import com.fasterxml.jackson.annotation.JsonProperty;
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

@PropertySource("classpath:application-test.yml")
@Configuration
@ConfigurationProperties
public class TestConfiguration {

    /*
        @NotEmpty
        private String template;

        @NotEmpty
        private String defaultName;

        // From the YAML file
//        @NotEmpty
        //@Value("${driverClass}")
        @JsonProperty
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

        //    @JsonProperty
        public String getTemplate() {
            return template;
        }

        //   @JsonProperty
        public String getDefaultName() {
            return defaultName;
        }

        //  @JsonProperty
        public void setDefaultName(String name) {
            this.defaultName = name;
        }
*/
        @Bean
        public Connection getConnection() {
            Connection dbConnection = null;
            try {
                Class.forName("org.h2.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            }

            try {
                dbConnection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "user", "password");
            } catch (SQLException ex) {
                System.out.println("TestConfiguration: ERROR connection to DB");
                ex.printStackTrace();
            }
            return dbConnection;
        }

        @Bean
        public AdvertiserDao getAdvertiserDao() {
            try {
                return new H2AdvertiserDaoImpl(getConnection());
            } catch (Exception ex) {
                return null;
            }
        }

/*
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

        public String getUser() {
            return this.user;
        } */

}
