package ar.com.dera.simor.common.entities.DOCENTES;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="puntaje_anual_docente")
public class PuntajeAnualDocente {

	@Id
	private ObjectId id;
	
	@Indexed
	private String dni;

	private String apellidoNombre;

	private List<Puntaje> puntaje;
	private List<Titulo> titulos;
	private List<Curso> cursos;
	
	public PuntajeAnualDocente() {
		this.id = ObjectId.get();
		this.puntaje = new ArrayList<Puntaje>();
		this.setTitulos(new ArrayList<Titulo>());
		this.setCursos(new ArrayList<Curso>());
	}
	
	public void addTitulo(Titulo titulo){
		this.getTitulos().add(titulo);
	}
	
	public void addCurso(Curso curso){
		this.getCursos().add(curso);
	}
	
	public void addPuntaje(String cargo, String puntaje, String distrito, String anio, String codigoEscuela, String tipoEscuela) {
		Puntaje p = new Puntaje(cargo,puntaje,distrito, anio, codigoEscuela, tipoEscuela);
		this.getPuntaje().add(p);
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PuntajeAnualDocente [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (dni != null) {
			builder.append("dni=");
			builder.append(dni);
			builder.append(", ");
		}
		if (apellidoNombre != null) {
			builder.append("apellidoNombre=");
			builder.append(apellidoNombre);
			builder.append(", ");
		}
		if (puntaje != null) {
			builder.append("puntaje=");
			builder.append(puntaje);
		}
		if (getTitulos() != null) {
			builder.append("titulos=");
			builder.append(getTitulos());
			builder.append(", ");
		}
		if (getCursos() != null) {
			builder.append("cursos=");
			builder.append(getCursos());
			builder.append(", ");
		}
		builder.append("]");
		return builder.toString();
	}


	//Getters and setters
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public List<Puntaje> getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(List<Puntaje> puntaje) {
		this.puntaje = puntaje;
	}


	public String getApellidoNombre() {
		return apellidoNombre;
	}


	public void setApellidoNombre(String apellidoNombre) {
		this.apellidoNombre = apellidoNombre;
	}

	public List<Titulo> getTitulos() {
		return titulos;
	}

	public void setTitulos(List<Titulo> titulos) {
		this.titulos = titulos;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

}
