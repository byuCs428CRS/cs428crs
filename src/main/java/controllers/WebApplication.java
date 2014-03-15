package controllers;

import org.apache.catalina.Context;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

/**
 * @author: Nick Humrich
 * @date: 1/18/14
 */
@ComponentScan
@EnableAutoConfiguration
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WebApplication.class);
		System.out.print("Starting app with System Args: [" );
    for (String s : args) {
      System.out.print(s + " ");
    }
    System.out.println("]");
		app.run(args);
	}
  @Bean
  public EmbeddedServletContainerFactory servletContainer() {
    TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
    factory.setTomcatContextCustomizers(Arrays.asList(new CustomCustomizer()));
    return factory;
  }

  static class CustomCustomizer implements TomcatContextCustomizer {
    @Override
    public void customize(Context context) {
      context.setUseHttpOnly(false);
      context.setSessionTimeout(30);
      context.setSessionCookieName("cr_ident");
      context.setDisplayName("BYU Course Planning");
    }
  }
}
