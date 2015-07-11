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
package ar.com.dera.simor.service.impl;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.dera.simor.common.exception.BusinessException;
import ar.com.dera.simor.common.exception.DataAccessException;
import ar.com.dera.simor.common.filter.Filter;
import ar.com.dera.simor.common.filter.Page;
import ar.com.dera.simor.common.filter.Result;
import ar.com.dera.simor.common.utils.LogHelper;
import ar.com.dera.simor.dao.GenericDAO;
import ar.com.dera.simor.service.GenericService;

public abstract class GenericServiceImpl<E> implements GenericService<E> {

	@Autowired
	private GenericDAO<E> dao;

	@Override
	public E getById(Serializable id) {
		return this.getDao().getById(id);
	}
	
	@Override
	public Result<E> search(Filter filter, Page p) {
		return this.getDao().search(filter, p);
	}
	
	@Override
	public E save(E o) throws BusinessException{
		return this.getDao().save(o);
	}

	@Override
	public Collection<E> save(Collection<E> collection) throws BusinessException{
		return this.getDao().save(collection);
	}

	
	@Override
	public void delete(E o){
		this.getDao().delete(o);
	}

	@Override
	public void delete(Serializable id){
		E o = this.getDao().getById(id);
		this.getDao().delete(o);
	}
	
	@Override
	public Long count(Filter filter) {
		long count = 0;
		
		try{
			count = this.getDao().count(filter);
		}
		catch(Exception e){
			LogHelper.error(this, e);
		}
		
		return count;
	}

	@Override
	public Result<E> getAll() throws BusinessException{
		try{
			return this.getDao().getAll();
		}
		catch(Exception e){
			LogHelper.error(this, e);
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public Result<E> searchWithReferences(Filter filter, Page p)
			throws BusinessException {
		try {
			return this.getDao().searchWithReferences(filter, p);
		} catch (Exception e) {
			LogHelper.error(this, e);
			throw new BusinessException(e.getMessage());
		}			
	}

	@Override
	public E getByIdWithReferences(Serializable id) throws BusinessException {
		return this.getDao().getByIdWithReferences(id);
	}

	@Override
	public Result<E> getAllWithReferences() throws BusinessException {
		try {
			return this.getDao().getAllWithReferences();
		} catch (DataAccessException e) {
			LogHelper.error(this, e);
			throw new BusinessException(e.getMessage());
		}			
	}
	
	
	//Getters and setters
	public GenericDAO<E> getDao() {
		return dao;
	}

	public void setDao(GenericDAO<E> dao) {
		this.dao = dao;
	}
	
}
