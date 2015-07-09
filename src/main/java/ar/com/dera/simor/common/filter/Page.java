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

/**
 * Entity to Model a Page; for pagination purposes
 * 
 * @author Gaspar Rajoy - <a href="mailto:gaspo53@gmail.com">gaspo53@gmail.com</a>
 *
 */
public class Page {

	/**
	 * Requesting page number
	 */
	private int pageNumber=1;
	
	/**
	 * Page size
	 */
	private int pageSize=-1;

	private boolean totalResultsCached = false;
	
	private long totalResults = 0;

	public Page(int pageNumber, int pageSize) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public Page(int pageNumber, int pageSize, boolean totalResultsCached) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalResultsCached = totalResultsCached;
	}	

	public Page() {
		this(1,0);
	}

	
	// Getters & setters
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(long totalResults) {
		this.totalResults = totalResults;
	}

	public boolean isTotalResultsCached() {
		return totalResultsCached;
	}

	public void setTotalResultsCached(boolean totalResultsCached) {
		this.totalResultsCached = totalResultsCached;
	}
}
