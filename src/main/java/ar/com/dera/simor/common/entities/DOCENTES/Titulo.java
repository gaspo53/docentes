package ar.com.dera.simor.common.entities.DOCENTES;

public class Titulo{

	private String egreso;	
	private String promedio;	
	private String nroRegistro;	
	private String denominacion;	
	private String expedido;	
	private String especOrient;	
	private String resolucion;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Titulo [egreso=");
		builder.append(egreso);
		builder.append(", promedio=");
		builder.append(promedio);
		builder.append(", nroRegistro=");
		builder.append(nroRegistro);
		builder.append(", denominacion=");
		builder.append(denominacion);
		builder.append(", expedido=");
		builder.append(expedido);
		builder.append(", especOrient=");
		builder.append(especOrient);
		builder.append(", resolucion=");
		builder.append(resolucion);
		builder.append("]");
		return builder.toString();
	}
	//Getters and setters
	public String getEgreso() {
		return egreso;
	}
	public void setEgreso(String egreso) {
		this.egreso = egreso;
	}
	public String getPromedio() {
		return promedio;
	}
	public void setPromedio(String promedio) {
		this.promedio = promedio;
	}
	public String getNroRegistro() {
		return nroRegistro;
	}
	public void setNroRegistro(String nroRegistro) {
		this.nroRegistro = nroRegistro;
	}
	public String getDenominacion() {
		return denominacion;
	}
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	public String getExpedido() {
		return expedido;
	}
	public void setExpedido(String expedido) {
		this.expedido = expedido;
	}
	public String getEspecOrient() {
		return especOrient;
	}
	public void setEspecOrient(String especOrient) {
		this.especOrient = especOrient;
	}
	public String getResolucion() {
		return resolucion;
	}
	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}
	
	

}
