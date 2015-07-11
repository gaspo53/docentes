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
package ar.com.dera.simor.service;

import java.io.Serializable;
import java.util.Collection;

import ar.com.dera.simor.common.exception.BusinessException;
import ar.com.dera.simor.common.filter.Filter;
import ar.com.dera.simor.common.filter.Page;
import ar.com.dera.simor.common.filter.Result;

/**
 * Generic Service defining common operations.<br>
 * 
 * @author Gaspar Rajoy - <a href="mailto:gaspo53@gmail.com">gaspo53@gmail.com</a>
 * @param <E>
 */
public interface GenericService<E> {

	/**
	 * Returns a Result containing the objects that match the current filter.
	 * @param filter
	 * @param p
	 * @see {@link Filter}
	 * @return Result
	 */
	Result<E> search(Filter filter, Page p) ;

	/**
	 * Returns a Result containing the objects that match the current filter.
	 * @param filter
	 * @param p
	 * @see {@link Filter}
	 * @return Result
	 */
	Result<E> searchWithReferences(Filter filter, Page p) throws BusinessException;
	
	/**
	 * Gets the object with the id received, in a non-eager fetch.
	 * @param id
	 * @return
	 */
	E getById(Serializable id);

	/**
	 * Gets the object with the id received, and all the referenced objects (eager fetch).
	 * @param id
	 * @return E. Null if not found
	 */
	E getByIdWithReferences(Serializable id) throws BusinessException;

	/**
	 * Saves or updates (if exists) the object in the DAO. 
	 * @param object
	 * @return
	 */
	E save(E o) throws BusinessException;

	/**
	 * Saves or updates (if exists) each object of collection in the DAO. 
	 * @param object
	 * @return
	 */
	Collection<E> save(Collection<E> collection) throws BusinessException;

	/**
	 * Deletes the object with the received ID, and all it's referenced objects.
	 * @param object
	 */
	void delete(Serializable id) throws BusinessException;

	/**
	 * Deletes the object, and all it's referenced objects.
	 * @param object
	 */
	void delete(E o) throws BusinessException;

	/**
	 * Returns the count of the total objects matching the current filter.
	 * @param filter
	 * @return
	 * @throws BusinessException
	 */
	Long count(Filter filter);
	
	/**
	 * Returns the entire collection.
	 * @return
	 */
	Result<E> getAll() throws BusinessException;

	/**
	 * Get all, and all the referenced objects (eager fetch).
	 * @return Result
	 */
	Result<E> getAllWithReferences() throws BusinessException;
}
