package ar.com.dera.simor.config.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * Main Application class - Run SpringBoot
 * @author Gaspar Rajoy - <a href="mailto:gaspo53@gmail.com">gaspo53@gmail.com</a>
 *
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"ar.com.dera.simor"})
public class Application extends SpringBootServletInitializer{
	//http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        Assert.notNull(ctx);
    }

}
