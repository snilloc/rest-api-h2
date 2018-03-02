package com.snilloc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collection;

import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration
//@Import({ springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration.class})
public class SwaggerConfig extends WebMvcConfigurationSupport {

    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.snilloc"))
                .paths(regex("/api.*"))
                .build()
                .apiInfo(metaInfo());
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/dist/resources/");

            registry.addResourceHandler("/dist/**")
                    .addResourceLocations("classpath:/dist/webjars/");
        }

    private ApiInfo metaInfo() {
        // Title description, version, termsOfServiceUrl, Contact, license, licenseUrl, vendorExtension
        Contact contact = new Contact("Brian Collins", "https://www.youtube.com", "snilloc@live.com");
        Collection<VendorExtension> vendorExtensions = new ArrayList<>();
        VendorExtension vendor = new MyVendorExtension("spring", "value");
        vendorExtensions.add(vendor);
        ApiInfo apiInfo = new ApiInfo("BackEnd API Example", "REST API Documentation",
                "0.1", "Terms of Service", contact, "Apache License Version 2.0",
                "https://www.apache.org/license.html", vendorExtensions);
        return apiInfo;
    }

    private class  MyVendorExtension implements VendorExtension {
        private String name;
        private String value;

        public MyVendorExtension(String name, String value) {
            this.name = name;
            this.value = value;
        }
        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }


}
