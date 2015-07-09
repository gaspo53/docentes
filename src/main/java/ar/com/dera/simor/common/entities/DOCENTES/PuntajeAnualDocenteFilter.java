package ar.com.dera.simor.common.entities.DOCENTES;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import ar.com.dera.simor.common.filter.Filter;

public class PuntajeAnualDocenteFilter implements Filter{

	private String dni;
	private String distrito;
	private String anio;
	private String codigoEscuela;
	private String tipoEscuela;
	
	@Override
	public void fillQuery(Query query) {
		if (StringUtils.isNotEmpty(this.getDni())){
			query.addCriteria(Criteria.where("dni").is(this.getDni()));
		}
		
		if (StringUtils.isNotEmpty(this.getDistrito())){
			query.addCriteria(Criteria.where("puntaje.distrito").is(this.getDistrito()));
		}
		
		if (StringUtils.isNotEmpty(this.getAnio())){
			query.addCriteria(Criteria.where("puntaje.anio").is(this.getAnio()));
		}
		
		if (StringUtils.isNotEmpty(this.getCodigoEscuela())){
			query.addCriteria(Criteria.where("puntaje.codigoEscuela").is(this.getCodigoEscuela()));
		}

		if (StringUtils.isNotEmpty(this.getTipoEscuela())){
			query.addCriteria(Criteria.where("puntaje.tipoEscuela").is(this.getTipoEscuela()));
		}
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getCodigoEscuela() {
		return codigoEscuela;
	}

	public void setCodigoEscuela(String codigoEscuela) {
		this.codigoEscuela = codigoEscuela;
	}

	public String getTipoEscuela() {
		return tipoEscuela;
	}

	public void setTipoEscuela(String tipoEscuela) {
		this.tipoEscuela = tipoEscuela;
	}
	
}
