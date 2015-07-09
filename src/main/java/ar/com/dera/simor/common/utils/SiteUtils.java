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

import java.net.URI;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Simple util class with Site-related methods.<br>
 * 
 * @author Gaspar Rajoy - <a href="mailto:gaspo53@gmail.com">gaspo53@gmail.com</a>
 *
 */
@Component("siteUtils")
public class SiteUtils {

	@Autowired
	private Environment env;
	
	@Autowired
	private MessageSource messageSource;

	/**
	 * From a String with an URL-format; extract the domain.<br>
	 * i.e.: http://www.google.com/search?q=search_string ---> google.com
	 * @param url
	 * @return the domain as String
	 */
	public String getUrlDomainName(String url) {
		  String domainName = new String(url);
		  int index = domainName.indexOf("://");

		  if (index != -1) {
		    // keep everything after the "://"
		    domainName = domainName.substring(index + 3);
		  }
		  index = domainName.indexOf('/');
		  if (index != -1) {
		    // keep everything before the '/'
		    domainName = domainName.substring(0, index);
		  }
		  // check for and remove a preceding 'www'
		  // followed by any sequence of characters (non-greedy)
		  // followed by a '.'
		  // from the beginning of the string
		  domainName = domainName.replaceFirst("^www.*?\\.", "");

		  return domainName;
	}

	/**
	 * Checks if to URI have the same domain.
	 * @param linkUri
	 * @param siteLinkUri
	 * @return
	 */
	public boolean isUrlSameDomain(URI linkUri, URI siteLinkUri) {
		boolean isSameDomain = !linkUri.isAbsolute();
		linkUri = linkUri.normalize();

		if (linkUri.isAbsolute()){
			String linkDomainName = this.getUrlDomainName(linkUri.toString());
			String siteDomainName = this.getUrlDomainName(siteLinkUri.toString());
			isSameDomain = StringUtils.equals(linkDomainName, siteDomainName);
		}
		
		return isSameDomain;
	}

	/**
	 * Wrapper of {@link #isUrlSameDomain(URI, URI)
	 */
	public boolean isUrlSameDomain(String linkUri, String siteLinkUri) {
		
		URI linkUriParsed = this.createUriFromString(linkUri);
		boolean isSameDomain = !linkUriParsed.isAbsolute();
		linkUriParsed = linkUriParsed.normalize();

		if (linkUriParsed.isAbsolute()){
			String linkDomainName = this.getUrlDomainName(linkUri.toString());
			String siteDomainName = this.getUrlDomainName(siteLinkUri);
			isSameDomain = StringUtils.equals(linkDomainName, siteDomainName);
		}
		
		return isSameDomain;
	}
	
	/**
	 * Checks if the String received has a valid URL format.
	 * @param url
	 * @return
	 */
	public boolean isUriValid(String url){
		String[] schemes = {"http","https"};
		Long options = UrlValidator.ALLOW_LOCAL_URLS;
		UrlValidator validator = new UrlValidator(schemes, options );
		
		return validator.isValid(url);
	}

	/**
	 * Checks if the URI received has a valid format.
	 * @param url
	 * @return
	 */
	public boolean isUriValid(URI uri){
		boolean isUriValid = (uri != null);
		isUriValid = isUriValid && this.isUriValid(uri.toString());
		
		return isUriValid;
	}

	/**
	 * Creates an instance of URI, from a string.
	 * @param sourceUrl
	 * @return
	 */
	public URI createUriFromString(String sourceUrl) {
		URI returnUri = null;
		try{
			sourceUrl = StringUtils.substringBefore(sourceUrl, "#");
			URL url = new URL(sourceUrl);
			URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), 
							  url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			//TODO is it OK to remove the query part?
			if (StringUtils.isNotBlank(uri.getQuery())){
				StringBuffer sb = new StringBuffer();
				sb.append(uri.getScheme()).append(":").append(uri.getSchemeSpecificPart());
				sourceUrl = sb.toString();
				returnUri = new URI(sourceUrl);
			}else{
				returnUri = uri;
			}
			return returnUri;
		}
		catch (Exception e){
			LogHelper.info(this, e);
			return null;
		}
	}
	
	
	//Getters and setters
	public Environment getEnv() {
		return env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
