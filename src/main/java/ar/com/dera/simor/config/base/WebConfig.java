package ar.com.dera.simor.config.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping;
import org.springframework.web.servlet.view.XmlViewResolver;

/**
 * Annotated web-config.<br>
 * 
 * @author Gaspar Rajoy - <a href="mailto:gaspo53@gmail.com">gaspo53@gmail.com</a>
 *
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private Environment env;

	@Bean 
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {  
		return new PropertySourcesPlaceholderConfigurer();  
	} 

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/");
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	  
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		registry.addInterceptor(localeChangeInterceptor);
	}

	@Bean(name="localeResolver")
	public LocaleResolver sessionLocaleResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(null);
		return sessionLocaleResolver;
	}

	//Request UTF-8 encoding
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
	    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	    registrationBean.setFilter(characterEncodingFilter);
	    characterEncodingFilter.setEncoding("UTF-8");
	    characterEncodingFilter.setForceEncoding(true);
	    registrationBean.setOrder(Integer.MIN_VALUE);
	    registrationBean.addUrlPatterns("/*");
	    return registrationBean;
	}

	@Bean
	public ControllerClassNameHandlerMapping  classNameHandlerMappingConfigurer(){
		return new ControllerClassNameHandlerMapping();
	}
	
	
	@Bean
	public XmlViewResolver xmlViewResolverConfigurer(){
		XmlViewResolver bean = new XmlViewResolver();
		Resource resource = new ClassPathResource("export-view.xml");  
		bean.setLocation(resource);
		return bean;
	}
	
	//Getters and Setters
	public Environment getEnv() {
		return this.env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
