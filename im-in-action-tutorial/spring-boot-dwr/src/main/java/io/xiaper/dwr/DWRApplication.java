package io.xiaper.dwr;

import org.directwebremoting.spring.DwrSpringServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class DWRApplication {

    public static void main(String[] args) {
        SpringApplication.run(DWRApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new DwrSpringServlet());
        registration.setName("dwr");
        registration.setEnabled(true);
        registration.addUrlMappings("/dwr/*");
        Map<String, String> initParameters = new HashMap<>(1);
        initParameters.put("debug", "true");
        registration.setInitParameters(initParameters);
        return registration;
    }
}