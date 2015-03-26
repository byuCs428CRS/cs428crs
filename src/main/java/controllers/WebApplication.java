package controllers;

import org.apache.catalina.Context;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.net.InetAddress;
import java.util.*;

/**
 * @author: Nick Humrich
 * @date: 1/18/14
 */
@ComponentScan
@EnableAutoConfiguration
public class WebApplication
{
    private static String service = "http://www.registerbyu.com/";

	public static void main(String[] args)
    {
		SpringApplication app = new SpringApplication(WebApplication.class);
		System.out.print("Starting app with System Args: [" );
        for (String s : args)
        {
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

    /*
    Authenticates a user via the CAS server.
     */
    @Bean()
    public FilterRegistrationBean authorizationFilter()
    {
        FilterRegistrationBean filterRegBean = new FilterRegistrationBean();

        AuthenticationFilter authFilter = new AuthenticationFilter();
        authFilter.setRenew(false);
        authFilter.setGateway(false);
        Map<String, String> params = new HashMap<String,String>();
        params.put("service", service);
        params.put("gateway", "false");
        params.put("casServerLoginUrl", "https://cas.byu.edu/cas/login");
        filterRegBean.setInitParameters(params);
        filterRegBean.setFilter(authFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/auth/service");
        filterRegBean.setUrlPatterns(urlPatterns);
        return filterRegBean;
    }

    /*
    Validates a ticket from the CAS server.
     */
    @Bean()
    public FilterRegistrationBean ticketValidationFilter()
    {
        FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        Cas20ProxyReceivingTicketValidationFilter validFilter = new Cas20ProxyReceivingTicketValidationFilter();

        Cas20ServiceTicketValidator validator = new Cas20ServiceTicketValidator("https://cas.byu.edu/cas");
        validFilter.setTicketValidator(validator);
        Map<String, String> params = new HashMap<String,String>();
        params.put("service", service);
        params.put("casServerUrlPrefix", "https://cas.byu.edu/cas");
        params.put("ticketValidatorClass", "org.jasig.cas.client.validation.Cas20ServiceTicketValidator");
        filterRegBean.setInitParameters(params);

        filterRegBean.setFilter(validFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/auth/service");
        filterRegBean.setUrlPatterns(urlPatterns);
        return filterRegBean;
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
