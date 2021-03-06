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
package ar.com.dera.simor.common.filter;

import org.springframework.data.mongodb.core.query.Query;

/**
 * Filter interface to build customs queries.<br>
 * Only works with Spring Data MongoDB
 *
 * @author Gaspar Rajoy - <a href="mailto:gaspo53@gmail.com">gaspo53@gmail.com</a>
 */
public interface Filter {

	
	/**
	 * Fills the current query, according with values in the filter.
	 * @param criteria
	 */
	public void fillQuery(Query query);
}
