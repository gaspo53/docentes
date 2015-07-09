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
package ar.com.dera.simor.common.exception;

/**
 * This exception, and all subclasses, has to be used in the Data acces layer (DAO)
 * 
 * @author Gaspar Rajoy - <a href="mailto:gaspo53@gmail.com">gaspo53@gmail.com</a>
 *
 */
public class DataAccessException extends Exception{

	private static final long serialVersionUID = 5289447833720832409L;
	
	//Default constructor
	public DataAccessException(){}
	
	public DataAccessException(String message){
		super(message);
	}
}
