package controllers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: Nick Humrich
 * @date: 1/18/14
 */
@ComponentScan
@EnableAutoConfiguration
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WebApplication.class);
		System.out.println("Starting app with System Args: ");
		app.run(args);
	}
}
