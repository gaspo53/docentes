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
package ar.com.dera.simor.common.utils;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

/**
 * Singleton pattern.<br>
 * Simple Utils class to help with application logging.<br>
 * 
 * @author Gaspar Rajoy - <a href="mailto:gaspo53@gmail.com">gaspo53@gmail.com</a>
 *
 */
public class LogHelper{
	
	//To prevent instantiation
	private LogHelper(){}

	private static Method getCallingMethod() throws ClassNotFoundException{
	    final Thread t = Thread.currentThread();
	    final StackTraceElement[] stackTrace = t.getStackTrace();
	    final StackTraceElement ste = stackTrace[3];
	    final String methodName = ste.getMethodName();
	    final String className = ste.getClassName();
	    Class<?> kls = Class.forName(className);
	    while ( (kls != null) && (!kls.equals(LogHelper.class)) ){
	        for(final Method candidate : kls.getDeclaredMethods()){
	            if(candidate.getName().equals(methodName)){
	                return candidate;
	            }
	        }
	        kls = kls.getSuperclass();
		}
	    return null;
	}
	
	public static void error(Class<? extends Object> clazz, String string){
		Logger logger = Logger.getLogger(clazz);
		String stringToLog = string;
		try{
			Method callingMethod = getCallingMethod();
			if (callingMethod != null){
				stringToLog = callingMethod.getName() + " -- " + stringToLog;
			}
			logger.error(stringToLog);
		}catch (Exception e){
			logger.error("LogHelper --- " + e.getMessage());
		}		
	}

	public static void error(Object object, String string){
		String stringToLog = string;
		try{
			Method callingMethod = getCallingMethod();
			if (callingMethod != null){
				stringToLog = callingMethod.getName() + " -- " + stringToLog;
			}
			error(object.getClass(), stringToLog);
		}catch (Exception e){
			error("LogHelper --- ", stringToLog);
		}
	}
	
	public static void error(Class<? extends Object> clazz, Exception exception){
		
		try{
			String message = null;
			Method callingMethod = getCallingMethod();
			
			Logger logger = Logger.getLogger(clazz);
			
			if (callingMethod != null){
				message = callingMethod.getName() + " -- " + exception.getMessage();
			}
			
			logger.error(message);
		}catch (Exception e){
			error("LogHelper --- ", e.getMessage());
		}		
	}

	public static void error(Object object, Exception exception){
		try{
			String message = null;
			Method callingMethod = getCallingMethod();
			
			if (callingMethod != null){
				message = callingMethod.getName() + " -- " + exception.getMessage();
			}
			error(object.getClass(), message);
		}catch (Exception e){
			error("LogHelper --- ", e.getMessage());
		}
		
	}

	public static void info(Class<? extends Object> clazz, String string){
		Logger logger = Logger.getLogger(clazz);
		logger.info(string);
	}

	public static void info(Object object, String string){
		info(object.getClass(), string);
	}

	public static void warn(Class<? extends Object> clazz, String string){
		Logger logger = Logger.getLogger(clazz);
		logger.warn(string);
	}

	public static void warn(Object object, String string){
		warn(object.getClass(), string);
	}

	public static void debug(Class<? extends Object> clazz, String string){
		Logger logger = Logger.getLogger(clazz);
		logger.debug(string);
	}

	public static void debug(Object object, String string){
		debug(object.getClass(), string);
	}
	
	public static void debug(String string){
		Logger logger = Logger.getLogger("default");
		logger.debug(string);
	}
	
	public static void info(String string){
		Logger logger = Logger.getLogger("default");
		logger.info(string);
	}

	public static void warn(String string){
		Logger logger = Logger.getLogger("default");
		logger.warn(string);
	}
	

	/*
	 * 
	 * Methods receiving Exception directly (avoid discarding exception)
	 * 
	 */
	public static void info(Class<? extends Object> clazz, Exception exception){
		Logger logger = Logger.getLogger(clazz);
		logger.info(exception.getMessage());
	}

	public static void info(Object object, Exception exception){
		info(object.getClass(), exception.getMessage());
	}

	public static void warn(Class<? extends Object> clazz, Exception exception){
		Logger logger = Logger.getLogger(clazz);
		logger.warn(exception.getMessage());
	}

	public static void warn(Object object, Exception exception){
		warn(object.getClass(), exception.getMessage());
	}

	public static void debug(Class<? extends Object> clazz, Exception exception){
		Logger logger = Logger.getLogger(clazz);
		logger.debug(exception.getMessage());
	}

	public static void debug(Object object, Exception exception){
		debug(object.getClass(), exception.getMessage());
	}
	
}
