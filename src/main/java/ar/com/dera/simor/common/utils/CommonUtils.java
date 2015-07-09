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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Decoder;
import ar.com.dera.simor.common.filter.Page;
import ar.com.dera.simor.common.filter.Result;
/**
 * This class has a set of utils, mostly for property handling.<br>
 * 
 * @author Gaspar Rajoy - <a href="mailto:gaspo53@gmail.com">gaspo53@gmail.com</a>
 *
 */
@Component("commonUtils")
public class CommonUtils {

	@Autowired
	private Environment env;

	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Get a message with the key received, in a locale context of the actual user.<br>
	 * Also, it can receive as many parameters as the user want.<br>
	 * 
	 * @param key (the placeholder)
	 * @param params (array of strings)
	 * @param locale
	 * @return the message in the current context locale
	 */
	public String getMessage(String key, Object[] params, Locale locale){
		String message = this.getMessageSource().getMessage(key, params, locale);
		return message;		
	}

	/**
	 * Get a message with the key received, in the default locale context<br>
	 * Also, it can receive as many parameters as the user want.<br>
	 * 
	 * @param key (the placeholder)
	 * @param params (array of strings)
	 * @param locale
	 * @return the message in the actual context locale
	 */
	public String getMessage(String key, Object[] params){
		String message = this.getMessage(key, params, LocaleContextHolder.getLocale());
		return message;		
	}
	
	/**
	 * Same as {@link #getMessage(String, Object[], Locale)}, but with no params
	 * @param key
	 * @param locale
	 * @return the message in the actual context locale
	 */
	public String getMessage(String key, Locale locale){
		return this.getMessage(key, new Object[]{}, locale);		
	}
	
	/**
	 * Same as {@link #getMessage(String, Locale)}, but with no Locale
	 * @param key
	 * @param locale
	 * @return the message in the actual context locale
	 */
	public String getMessage(String key){
		return this.getMessage(key, LocaleContextHolder.getLocale());		
	}
	
	/**
	 * Resolves the placeholder received in key, but on ly in the application.properties file.<br>
	 * @param key
	 * @return the value as string
	 */
	public String getProperty(String key) {
		return this.getEnv().getProperty(key);
	}

	/**
	 * Returns the line number where the word is found in the text.<br>
	 * @param word
	 * @param text
	 * @return
	 */
	public int getLineNumber(String word, String text) {
		int lineNumber = -1;
		List<String> textLines = this.stringToLinesArray(text);
		List<String> wordLines = this.stringToLinesArray(word);
		int sizeOfTextLines = textLines.size();
		int sizeOfWordLines = wordLines.size();
		
		try{
			if (sizeOfTextLines >= sizeOfWordLines){
				boolean equals = false;
				int wordLinesIndex = 0;
				lineNumber = this.indexOfStringInLines(wordLines.get(wordLinesIndex), textLines);
				if (lineNumber != -1){
					while ( (wordLinesIndex < sizeOfWordLines) && (!equals) ){
						equals = StringUtils.equals(wordLines.get(wordLinesIndex), textLines.get(lineNumber));
						lineNumber++;
						wordLinesIndex++;
					}
				}
			}
		}
		catch(Exception e){
			LogHelper.error(this, e);
		}
			
		return lineNumber;
	}
	
	/**
	 * Splits a String into its lines.
	 * @return a {@link List} of Strings
	 */
    private List<String> stringToLinesArray(String string) {
    	List<String> lines = new ArrayList<String>();
    	try{
	    	StringReader sr = new StringReader(string);
			BufferedReader br = new BufferedReader(sr);
			String strLine;
			while ((strLine = br.readLine()) != null) {
				lines.add(strLine);
			}
    	}
    	catch(Exception e){
    		LogHelper.error(this,e);
    	}
		return lines;
    }

	/**
     * Paginates a Result, according to the Page passed.<br>
     * Useful to do pagination when consuming WebServices, or Lists.
     * 
     * @param list
     * @param page
     * @see {@link Result}
     * @return
     */
    
    @SuppressWarnings("unchecked")
	public <E> Result<E> getResult(List<E> list, Page page){
    	Result<E> result = new Result<E>();
    	result.setPage(page);
    	result.setTotalResults(CollectionUtils.size(list));
    	int from = 0;
    	
    	if (page.getPageNumber() <= 1){
    		from = 0;
    	}else{
    		from = (page.getPageNumber()-1) * page.getPageSize();
    	}

    	int to = from + page.getPageSize();
    	Object[] array = list.toArray();
    	Object[] subarray = ArrayUtils.subarray(array, from, to);
    	
		List<E> subList = new ArrayList<E>();
		subList.addAll((Collection<? extends E>) Arrays.asList(subarray));
    	
    	result.setResult(subList);
    	
    	return result;
    }
	
    /**
     * Converts a Base64 String (image) into a byte[]
     * @param imageString
     * @return 
     * @throws IOException
     */
	public byte[] base64ImageToByte(String imageString){
		byte[] byteArray = null;
		try{
			ByteArrayInputStream byteInputStream = null;
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] imageByte = decoder.decodeBuffer(imageString);
			byteInputStream = new ByteArrayInputStream(imageByte);
			byteArray = IOUtils.toByteArray(byteInputStream);
			byteInputStream.close();
		}catch (Exception e){
			LogHelper.warn(this,e);
		}
		
		return byteArray;
	}
	
	//Private methods
	
	/**
	 * Indicates the index of the search string, in the array.<br>
	 * @param search
	 * @param lines
	 * @return
	 */
	private int indexOfStringInLines(String search, List<String> lines) {
		int linesSize = lines.size();
		int index = 0;
		boolean contains = false;
		while ((index < linesSize) && (!contains) ){
			String line = StringUtils.trimToNull(lines.get(index));
			contains = StringUtils.contains(line, StringUtils.trimToNull(search));
			index++;
		}
		return (index==0)?0:(index-1);
	}

	/**
	 * Gives the ratio (0.0 -- 1.0) between the two strings.<br>
	 * <b>0.0</b> Means that the strings are equals.<br>
	 * <b>1.0</b> Means that the strings are totally different
	 * 
	 * @param html1
	 * @param html2
	 * @return the ratio
	 */
	public double stringMatchRatio(String html1, String html2){
		int levenstein = StringUtils.getLevenshteinDistance(html1, html2);
		double ratio = ((double) levenstein) / (Math.max(html1.length(), html2.length()));
		
		return ratio;
	}
	
	//Getters and setters
	public Environment getEnv() {
		return this.env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}


	public MessageSource getMessageSource() {
		return this.messageSource;
	}


	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
