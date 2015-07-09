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
package ar.com.dera.simor.config.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Bean definitions for the application.<br>
 * Here are defined beans that require initialisation.
 * 
 * @author Gaspar Rajoy - <a href="mailto:gaspo53@gmail.com">gaspo53@gmail.com</a>
 */
@Configuration
public class BeansDefinition {
	
	@Autowired
	private Environment env;
    
	@Autowired
	private ApplicationContext applicationContext;
	


  
    
    //Getters and setters 
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
