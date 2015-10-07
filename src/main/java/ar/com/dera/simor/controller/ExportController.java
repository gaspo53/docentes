package ar.com.dera.simor.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import ar.com.dera.simor.common.entities.DOCENTES.PuntajeAnualDocente;
import ar.com.dera.simor.common.entities.DOCENTES.PuntajeAnualDocenteFilter;
import ar.com.dera.simor.common.filter.Page;
import ar.com.dera.simor.common.filter.Result;
import ar.com.dera.simor.service.impl.DOCENTES.PuntajeAnualDocenteServiceImpl;

@Controller
public class ExportController extends AbstractController{

	@Autowired
	private PuntajeAnualDocenteServiceImpl service;
	
	@Override
	@RequestMapping(value="/export", method=RequestMethod.GET)
	protected ModelAndView handleRequestInternal(HttpServletRequest request,HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String,Object>();
		
		return new ModelAndView("exportView", model);
	}

	public PuntajeAnualDocenteServiceImpl getService() {
		return service;
	}

	public void setService(PuntajeAnualDocenteServiceImpl service) {
		this.service = service;
	}
	
	
}
