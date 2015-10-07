package ar.com.dera.simor.common.entities.DOCENTES;


public class Puntaje {

	private String distrito;
	
	private String anio;

	private String codigoEscuela;

	private String tipoEscuela;

	private String cargo;
	
	private String puntaje;
	
	public Puntaje() {}
	
	public Puntaje(String cargo, String puntaje, String distrito, String anio, String codigoEscuela, String tipoEscuela) {
		this.cargo = cargo;
		this.puntaje = puntaje;
		this.distrito = distrito;
		this.anio = anio;
		this.codigoEscuela = codigoEscuela;
		this.tipoEscuela = tipoEscuela;
	}

	
	//Getters and setters
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
	
	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(String puntaje) {
		this.puntaje = puntaje;
	}

}
