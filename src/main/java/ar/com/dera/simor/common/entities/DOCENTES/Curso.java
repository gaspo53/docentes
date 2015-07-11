package ar.com.dera.simor.common.entities.DOCENTES;

public class Curso{

	private String anio;	
	private String denominacion;	
	private String organismo;	
	private String hs;	
	private String resolucion;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Curso [anio=");
		builder.append(anio);
		builder.append(", denominacion=");
		builder.append(denominacion);
		builder.append(", organismo=");
		builder.append(organismo);
		builder.append(", hs=");
		builder.append(hs);
		builder.append(", resolucion=");
		builder.append(resolucion);
		builder.append("]");
		return builder.toString();
	}
	//Getters and setters
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public String getDenominacion() {
		return denominacion;
	}
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	public String getOrganismo() {
		return organismo;
	}
	public void setOrganismo(String organismo) {
		this.organismo = organismo;
	}
	public String getHs() {
		return hs;
	}
	public void setHs(String hs) {
		this.hs = hs;
	}
	public String getResolucion() {
		return resolucion;
	}
	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}	

	
	
		
}
