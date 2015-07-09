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

import java.util.List;

/**
 * Entity to serve as a Result container from a Database query.<br>
 * Also handle pagination, using {@link Page}
 * 
 * @author Gaspar Rajoy - <a href="mailto:gaspo53@gmail.com">gaspo53@gmail.com</a>
 *
 * @param <T>
 */
public class Result<T> {

	private static final int PAGINATOR_SIZE = 10;

	private List<T> result;
	
	private long totalResults;
	
	private Page page;

	//Pagination properties
	/**
	 * Number of pages to show in pagination bar (optional)
	 */
	private int paginatorSize;
	
	private int actualOffset;
	
	private int startOffset;
	
	private long beginPage;
	
	private long remainingPages;
	
	private long endPage;
	
	
	public Result(){
		this.setPaginatorSize(PAGINATOR_SIZE);
	}
	
	public long getTotalPages() {
		long ret;
		if (page.getPageSize() > 0){
			if(totalResults % page.getPageSize() == 0){
				ret = totalResults / page.getPageSize();
			} else {
				ret = (totalResults / page.getPageSize()) + 1;
			}
		}else{
			ret = 1;
		}
		return ret;
	}
	
	//Private methods
	private void initializePagination(){
		//Pages
		int pageNumber = this.getPage().getPageNumber();
		this.actualOffset = Integer.divideUnsigned(pageNumber, this.getPaginatorSize());
		this.startOffset = pageNumber % this.getPaginatorSize();
		this.beginPage = (this.actualOffset * this.getPaginatorSize()) + 1;
		this.remainingPages = this.getTotalPages() - this.getBeginPage();
		this.endPage = ((this.getBeginPage() + this.getPaginatorSize()) <= this.getTotalPages())
						?(this.getBeginPage() + this.getPaginatorSize())
					    :(this.getTotalPages());
		//TODO ugly thing
		this.beginPage = ( ((this.getTotalPages() - this.getBeginPage()) < this.getPaginatorSize())
						   && (this.getTotalPages() > this.getPaginatorSize()))
					 	 ?(this.getBeginPage() - this.getPaginatorSize() + this.getStartOffset())
					 	 :(this.getBeginPage());
	}

	//Getters & setters
	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
		this.initializePagination();
	}

	 public long getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(long totalResults) {
		this.totalResults = totalResults;
		if (this.page != null) {
			this.page.setTotalResults(totalResults);
		}
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}


	public int getPaginatorSize() {
		return paginatorSize;
	}


	public void setPaginatorSize(int paginatorSize) {
		this.paginatorSize = paginatorSize;
	}

	public int getActualOffset() {
		return actualOffset;
	}

	public void setActualOffset(int actualOffset) {
		this.actualOffset = actualOffset;
	}

	public int getStartOffset() {
		return startOffset;
	}

	public void setStartOffset(int startOffset) {
		this.startOffset = startOffset;
	}

	public long getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(long beginPage) {
		this.beginPage = beginPage;
	}

	public long getEndPage() {
		return endPage;
	}

	public void setEndPage(long endPage) {
		this.endPage = endPage;
	}

	public long getRemainingPages() {
		return remainingPages;
	}

	public void setRemainingPages(long remainingPages) {
		this.remainingPages = remainingPages;
	}

}
