package ar.com.dera.simor.common.utils;

import java.util.Set;

import org.reflections.Reflections;
import org.springframework.stereotype.Component;

/**
 * Util class to work with {@link Reflections}
 * @author Gaspar Rajoy - <a href="mailto:gaspo53@gmail.com">gaspo53@gmail.com</a>
 *
 */
@Component("reflectionUtils")
public class ReflectionUtils {

	public Set<?> getSubtypesOf(Class<?> clazz){
		Reflections reflections = new Reflections("ar.com.dera.simor");    
		Set<?> classesSet = reflections.getSubTypesOf(clazz);
		
		return classesSet;
	}
}
