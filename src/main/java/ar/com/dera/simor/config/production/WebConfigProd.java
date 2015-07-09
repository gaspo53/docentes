/*******************************************************************************
 * Copyright (c) 2014 Gaspar Rajoy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Gaspar Rajoy - initial API and implementation
 ******************************************************************************/
package ar.com.dera.simor.config.production;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ar.com.dera.simor.config.base.Profiles;


/**
 * Annotated web-config.<br>
 * 
 * @author Gaspar Rajoy - <a href="mailto:gaspo53@gmail.com">gaspo53@gmail.com</a>
 *
 */
@Configuration
@Profile(Profiles.PRODUCTION)
public class WebConfigProd extends WebMvcConfigurerAdapter {
	
	@Bean
	public EmbeddedServletContainerCustomizer servletContainerCustomizer() {
	    return new EmbeddedServletContainerCustomizer() {
	        @Override
	        public void customize(ConfigurableEmbeddedServletContainer servletContainer) {
	            ((TomcatEmbeddedServletContainerFactory) servletContainer).addConnectorCustomizers(
	                    new TomcatConnectorCustomizer() {
	                        @Override
	                        public void customize(Connector connector) {
	                            AbstractHttp11Protocol<?> httpProtocol = (AbstractHttp11Protocol<?>) connector.getProtocolHandler();
	                            httpProtocol.setCompression("on");
	                            httpProtocol.setCompressionMinSize(256);
	                            String mimeTypes = httpProtocol.getCompressableMimeTypes()+",text/css,application/javascript";
	                            String mimeTypesWithJson = mimeTypes + "," + MediaType.APPLICATION_JSON_VALUE;
	                            httpProtocol.setCompressableMimeTypes(mimeTypesWithJson);
	                        }
	                    }
	            );
		        ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/unauthorized");
		        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/not_found");
		        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error");
		        servletContainer.addErrorPages(error401Page, error404Page, error500Page);
	        }
	    };
	}
}
