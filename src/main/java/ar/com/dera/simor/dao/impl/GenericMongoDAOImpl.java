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
package ar.com.dera.simor.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import ar.com.dera.simor.common.exception.DataAccessException;
import ar.com.dera.simor.common.filter.Filter;
import ar.com.dera.simor.common.filter.Page;
import ar.com.dera.simor.common.filter.Result;
import ar.com.dera.simor.dao.GenericDAO;
/**
 * Generic DAO implemented using MongoDB storage.<br>
 * 
 * @author Gaspar Rajoy - <a href="mailto:gaspo53@gmail.com">gaspo53@gmail.com</a>
 * @param <E>
 */
public class GenericMongoDAOImpl<E> implements GenericDAO<E> {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private MongoOperations mongoOperations;

	private Class<E> type;
	
	public GenericMongoDAOImpl(Class<E> clazz){
		this.type = clazz;
	}
	
	@Override
	public Result<E> search(final Filter filter, final Page page) {
		
		MongoOperations getMongoOperations = this.mongoOperations();
		Result<E> result = new Result<E>();
		Query query = new Query();
		filter.fillQuery(query);
		
		long totalResults = getMongoOperations.count(query, this.getType());
		result.setTotalResults(totalResults);

		//Pagination
		query.skip((page.getPageNumber()-1)*page.getPageSize());
		query.limit(page.getPageSize());
		
		List<E> results = getMongoOperations.find(query, this.getType());
		result.setPage(new Page(page.getPageNumber(), page.getPageSize(), page.isTotalResultsCached()));
		result.setResult(results);
		
		return result;
	}

	@Override
	public Long count(final Filter filter) throws DataAccessException{
		Long count = new Long(0);
		
		MongoOperations getMongoOperations = this.mongoOperations();
		Query query = new Query();
		filter.fillQuery(query);
		
		count = getMongoOperations.count(query, this.getType());
		
		return count;
	}
	
	@Override
	public Result<E> getAll(){
		MongoOperations getMongoOperations = this.mongoOperations();
		
		Result<E> result = new Result<E>();
		List<E> results = getMongoOperations.findAll(this.getType());
		
		result.setPage(new Page());
		result.setResult(results);
		result.setTotalResults(CollectionUtils.size(results));
		
		return result;
	}

	@Override
	public E save(E entity) {
		MongoOperations getMongoOperations = this.mongoOperations();
		getMongoOperations.save(entity);

		return entity;
	}

	@Override
	public Collection<E> save(Collection<E> collection) {
		collection.parallelStream()
					.forEach(e -> this.save(e));
		
		return collection;
	}
	
	@Override
	public E getById(Serializable id) {
		MongoOperations getMongoOperations = this.mongoOperations();
		
		E object = getMongoOperations.findById(id, this.getType());
		
		return object;
	}

	@Override
	public boolean exists(Serializable id) {
		return (getById(id) != null);
	}

	@Override
	public void delete(Serializable id) {
		MongoOperations getMongoOperations = this.mongoOperations();
		
		E object = getMongoOperations.findById(id, this.getType());
		
		this.delete(object);
	}


	@Override
	public void delete(E object) {
		MongoOperations getMongoOperations = this.mongoOperations();
		getMongoOperations.remove(object);
	}

	@Override
	public E getByIdWithReferences(Serializable id) {
		E object = this.getById(id);
		this.getReferences(object);
		
		return object;
	}

	@Override
	public Result<E> searchWithReferences(Filter filter, Page p){
		Result<E> result = this.search(filter, p);
		result.getResult().parallelStream()
			.forEach( object -> {
				this.getReferences(object);
			});
		
		return result;
	}

	@Override
	public Result<E> getAllWithReferences() throws DataAccessException {
		Result<E> result = this.getAll();
		result.getResult().parallelStream()
		.forEach( object -> {
			this.getReferences(object);
		});
		
		return result;
		
	}

	@Override
	public void getReferences(E object) {
		//Implemented by extenders
	}
	
	/*
	 * Getters and setters
	 */
	
	public Class<E> getType() {
		return type;
	}

	public void setType(Class<E> type) {
		this.type = type;
	}

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public MongoOperations mongoOperations() {
		return mongoOperations;
	}

	public void setgetMongoOperations(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}
}


