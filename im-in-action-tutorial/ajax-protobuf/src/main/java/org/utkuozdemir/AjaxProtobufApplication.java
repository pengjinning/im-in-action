package org.utkuozdemir;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;

import java.awt.*;
import java.net.URI;

@SpringBootApplication
public class AjaxProtobufApplication {
	public static void main(String[] args) {
		//noinspection Convert2Lambda
		new SpringApplicationBuilder()
				.sources(AjaxProtobufApplication.class)
				.main(AjaxProtobufApplication.class)
				.headless(false)
				.listeners(new ApplicationListener<ContextRefreshedEvent>() {
					@Override
					public void onApplicationEvent(ContextRefreshedEvent event) {
						Desktop desktop = Desktop.getDesktop();
						if (desktop != null) {
							try {
								desktop.browse(new URI("http://localhost:8080/"));
							} catch (Exception e) {
								System.out.println("Error occured...");
							}
						}
					}
				})
				.run(args);
	}

	@Bean
	public MappingJackson2HttpMessageWithContentLengthConverter mappingJsonHttpMessageConverter() {
		return new MappingJackson2HttpMessageWithContentLengthConverter();
	}
}
