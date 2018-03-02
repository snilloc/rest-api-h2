package com.snilloc;

import com.snilloc.config.AppConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class RestSpringDemo {
    public static void main(String[] args){
        SpringApplication bootApp = new SpringApplication(RestSpringDemo.class);
//        bootApp.setBanner(Banner.Mode.OFF);
        bootApp.setLogStartupInfo(true);
//        ConfigurableApplicationContext context = bootApp.run(args);
//        Connection client = context.getBean(Connection.class);
//        System.out.println("Client: " + client.g.get.getHostname() + ":" + client.getPort());
        SpringApplication.run(RestSpringDemo.class, args);
    }
}
