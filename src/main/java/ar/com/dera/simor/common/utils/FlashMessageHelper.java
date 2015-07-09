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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Util class to handle FlashScope messages and custom attributes.<br>
 * Despite FlashScopes, it also can add those attributes to a ModelAndView.<br>
 * 
 * @author Gaspar Rajoy - <a href="mailto:gaspo53@gmail.com">gaspo53@gmail.com</a>
 * @see {@link ModelAndView}
 */

@Component("flashMessageHelper")
public class FlashMessageHelper {

	private String ALERT_ERROR = null;
	private String ALERT_WARNING = null;
	private String ALERT_SUCCESS = null;
	private String ALERT_INFO = null;
	
	@Autowired
	private CommonUtils commonUtils;
	
	@PostConstruct
	private void initialize(){
		 ALERT_ERROR 	= getCommonUtils().getProperty("css.alert-error");
		 ALERT_WARNING 	= getCommonUtils().getProperty("css.alert-warning");
		 ALERT_SUCCESS 	= getCommonUtils().getProperty("css.alert-success");
		 ALERT_INFO 	= getCommonUtils().getProperty("css.alert-info");
	}
	
	/**
	 * TODO
	 * To use when FlashMessageHelper has support form multiple messages
	 */
	private static Map<String, String> messages = new HashMap<String, String>();


	public Map<String, String> getMessages() {
		return messages;
	}

	private void addMessage(String messageCode, String messageClass, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message_code", messageCode);
		redirectAttributes.addFlashAttribute("message_class", messageClass);
	}

	private void addMessage(String messageCode, String messageClass, ModelAndView mv) {
		reset();
		mv.addObject("message_code", messageCode);
		mv.addObject("message_class", messageClass);
	}

	private void addMessage(String messageCode, String messageClass, String messageParameter, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message_code", messageCode);
		redirectAttributes.addFlashAttribute("message_class", messageClass);
		redirectAttributes.addFlashAttribute("message_parameter", messageParameter);
	}

	private void addMessage(String messageCode, String messageClass, String messageParameter, ModelAndView mv) {
		reset();
		mv.addObject("message_code", messageCode);
		mv.addObject("message_class", messageClass);
		mv.addObject("message_parameter", messageParameter);
	}

	// To add a custom attribute to a flash scope
	public void addCustomAttribute(String name, Object value, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(name, value);
	}

	public void addCustomAttribute(String name, Object value, ModelAndView mv) {
		mv.addObject(name, value);
	}

	// Flash messages for session (have to be cleared on each request)
	public void success(String messageCode, RedirectAttributes redirectAttributes) {
		addMessage(messageCode, ALERT_SUCCESS, redirectAttributes);
	}

	public void info(String messageCode, RedirectAttributes redirectAttributes) {
		addMessage(messageCode, ALERT_INFO, redirectAttributes);
	}

	public void warn(String messageCode, RedirectAttributes redirectAttributes) {
		addMessage(messageCode, ALERT_WARNING, redirectAttributes);
	}

	public void error(String messageCode, RedirectAttributes redirectAttributes) {
		addMessage(messageCode, ALERT_ERROR, redirectAttributes);
	}

	// With parameter
	public void success(String messageCode, String messageParameter, RedirectAttributes redirectAttributes) {
		addMessage(messageCode, ALERT_SUCCESS, messageParameter, redirectAttributes);
	}

	public void info(String messageCode, String messageParameter, RedirectAttributes redirectAttributes) {
		addMessage(messageCode, ALERT_INFO, messageParameter, redirectAttributes);
	}

	public void warn(String messageCode, String messageParameter, RedirectAttributes redirectAttributes) {
		addMessage(messageCode, ALERT_WARNING, messageParameter, redirectAttributes);
	}

	public void error(String messageCode, String messageParameter, RedirectAttributes redirectAttributes) {
		addMessage(messageCode, ALERT_ERROR, messageParameter, redirectAttributes);
	}

	// Flashmessages for ModelAndView (works on redirect)

	public void info(String messageCode, ModelAndView mv) {
		addMessage(messageCode, ALERT_INFO, mv);
	}

	public void success(String messageCode, ModelAndView mv) {
		addMessage(messageCode, ALERT_SUCCESS, mv);
	}

	public void warn(String messageCode, ModelAndView mv) {
		addMessage(messageCode, ALERT_WARNING, mv);
	}

	public void error(String messageCode, ModelAndView mv) {
		addMessage(messageCode, ALERT_ERROR, mv);
	}

	// With parameter
	public void info(String messageCode, String messageParameter, ModelAndView mv) {
		addMessage(messageCode, ALERT_INFO, messageParameter, mv);
	}

	public void success(String messageCode, String messageParameter, ModelAndView mv) {
		addMessage(messageCode, ALERT_SUCCESS, messageParameter, mv);
	}

	public void warn(String messageCode, String messageParameter, ModelAndView mv) {
		addMessage(messageCode, ALERT_WARNING, messageParameter, mv);
	}

	public void error(String messageCode, String messageParameter, ModelAndView mv) {
		addMessage(messageCode, ALERT_ERROR, messageParameter, mv);
	}

	// Reset funtions (clear all flash messages)
	public void reset() {
		try {
			messages = new HashMap<String, String>();
		} catch (Exception e) {
			LogHelper.info(FlashMessageHelper.class, e.getMessage());
		}

	}

	public CommonUtils getCommonUtils() {
		return commonUtils;
	}

	public void setCommonUtils(CommonUtils commonUtils) {
		this.commonUtils = commonUtils;
	}
}
