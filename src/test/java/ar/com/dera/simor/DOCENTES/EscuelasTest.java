package ar.com.dera.simor.DOCENTES;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import ar.com.dera.simor.common.entities.DOCENTES.Curso;
import ar.com.dera.simor.common.entities.DOCENTES.PuntajeAnualDocente;
import ar.com.dera.simor.common.entities.DOCENTES.PuntajeAnualDocenteFilter;
import ar.com.dera.simor.common.entities.DOCENTES.Titulo;
import ar.com.dera.simor.common.exception.BusinessException;
import ar.com.dera.simor.common.filter.Page;
import ar.com.dera.simor.common.filter.Result;
import ar.com.dera.simor.common.utils.LogHelper;
import ar.com.dera.simor.config.boot.Application;
import ar.com.dera.simor.service.GenericService;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class EscuelasTest {

	@Autowired
	@Qualifier("puntajeAnualDocenteService")
	private GenericService<PuntajeAnualDocente> puntajeAnualDocenteService;
	
	List<String> tiposEscuela = Arrays.asList(new String[]{"MA","MT"});
	
	List<String> distritos = Arrays.asList(new String[]{"1","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40"
            ,"41","42","43","44","45","47","48","49","50","51","53","54","55","56","57","58","59","60","61","62","63","64","65","66","67"
            ,"68","69","70","71","72","73","74","75","77","78","79","80","81","82","83","84","85","86","87","88","89","90","91","92","93"
            ,"94","95","96","97","98","99","100","101","102","103","104","105","106","107","109","110","111","112","114","115","116","117"
            ,"118","120","121","123","124","125","126","127","128","129","130","131","132","133","134","135","136","137"});
	
	List<Integer> escuelas = IntStream.rangeClosed(1, 200).boxed().collect(Collectors.toList());
	List<Integer> anios = IntStream.rangeClosed(2004, 2014).boxed().collect(Collectors.toList());
	
	@SuppressWarnings("unused")
	@Ignore
	public void getDocentesTest(){
		String url = "http://servicios2.abc.gov.ar/servaddo/puntaje.anual.docente/redirector.cfm";
		
		try{
			escuelas.stream().forEach(escuela -> {
				distritos.stream().forEach(distrito -> {
					anios.stream().forEach(anio -> {
						tiposEscuela.stream().forEach(tipoEscuela -> {
							Map<String, String> params = new HashMap<String, String>();
							params.put("anio", String.valueOf(anio));
							params.put("documento", "");
							params.put("distrito", distrito);
							params.put("tipo_org", tipoEscuela);
							params.put("numero", String.valueOf(escuela));
							try{
								Document document = Jsoup.connect(url)
														 .timeout(6000)
														 .data(params)
														 .followRedirects(true)
														 .userAgent("Mozilla/5.0 (Macintosh; U; PPC Mac OS X; en) AppleWebKit/48 (like Gecko) Safari/48")
														 .referrer("http://servicios2.abc.gov.ar/servaddo/puntaje.anual.docente/puntaje.cfm")
														 .cookie("CFID","12963638;expires=Mon, 24-Apr-2045 20:57:06 GMT;path=/")
														 .post();
								
								Elements elements = document.select("div[id=webpad] h4");
								if (!elements.isEmpty()){
									Element organizacion = elements.get(0);
									Elements rows = document.select("table[id=pad_table] tbody tr");
									//First match - docente
									rows.stream().forEach(row -> {
										PuntajeAnualDocente pda = new PuntajeAnualDocente();

										Elements tds = row.select("td");
										String nombre = tds.get(0).text();
										String dni = tds.get(1).text();
										dni = StringUtils.remove(dni, "(");
										dni = StringUtils.remove(dni, ")");
										pda.setDni(dni);
										
										pda.setApellidoNombre(StringUtils.trim(StringUtils.substringBefore(nombre, "(")));
										String cargo = tds.get(2).text();
										String puntaje = tds.get(3).text();
										pda.addPuntaje(cargo, puntaje, distrito, String.valueOf(anio), String.valueOf(escuela), tipoEscuela);
												
										try {
											this.saveOrAddPuntaje(pda);
										} catch (Exception e) {
											LogHelper.error(this,e);
										}
									});
								}
							}catch(Exception e){
								LogHelper.error(this,e);
							}
						});
					});
				});
				System.out.println("Escuela "+escuela);
			});
		}catch(Exception e){
			LogHelper.error(this, e);
		}
	}

	
	@Test
	public void getTitulosYCursosTest(){
		String url = "http://servicios2.abc.gov.ar/servaddo/titulos_y_cursos/compact/default.cfm?doc=RA==&documento=DNI_BASE_64=&db=aW5ncmUyMDE1&anio=Y2ZTZXJ2YWRkbzJlY2ZjMTY5NDkyMzI2MSRmdW5jQU5JT0A1NDgwZGM=&all=Kg==";
		
		int currentIndex = 0;

		PuntajeAnualDocenteFilter filter = new PuntajeAnualDocenteFilter();
		Result<PuntajeAnualDocente> result = this.puntajeAnualDocenteService.search(filter, new Page(currentIndex++,1));
		List<PuntajeAnualDocente> puntajes = result.getResult();
		Stack<PuntajeAnualDocente> stack = new Stack<PuntajeAnualDocente>();
		stack.addAll(puntajes);
		try{
			while (!stack.isEmpty()){
				PuntajeAnualDocente puntaje = stack.pop();
				Map<String, String> params = new HashMap<String, String>();
				params.put("anio", "");
				params.put("documento", puntaje.getDni());
				params.put("distrito", "");
				params.put("tipo_org", "");
				params.put("numero", "");
				params.put("seleccion", "documento");
				params.put("distrito_name", "");
				params.put("tipoorg_name", "");
				try{
					String base64DNI = Base64.getEncoder().encodeToString(puntaje.getDni().getBytes());
					String encodedURL = StringUtils.replace(url, "DNI_BASE_64", base64DNI);
					Document document = Jsoup.connect(encodedURL)
											 .timeout(6000)
											 .data(params)
											 .followRedirects(true)
											 .userAgent("Mozilla/5.0 (Macintosh; U; PPC Mac OS X; en) AppleWebKit/48 (like Gecko) Safari/48")
											 .referrer("http://servicios2.abc.gov.ar/servaddo/puntaje.anual.docente/puntaje.cfm")
											 .cookie("CFID","12963638;expires=Mon, 24-Apr-2045 20:57:06 GMT;path=/")
											 .post();
					
					Elements titulos = document.select("table[id=titulos] > tbody > tr");
					Elements cursos = document.select("table[id=cursos] > tbody > tr");
					puntaje.getTitulos().clear();
					puntaje.getCursos().clear();
					titulos.stream().forEach(e -> {
						Titulo titulo = new Titulo();
						Elements tds = e.select("td");
						Element egreso = tds.get(0);
						Element promedio = tds.get(1);
						Element nroRegistro = tds.get(2);
						Element denominacion = tds.get(3);
						Element expedido = tds.get(4);
						Element especOrient = tds.get(5);
						Element resolucion = tds.get(6);
						
						titulo.setEgreso(egreso.text());
						titulo.setPromedio(promedio.text());
						titulo.setNroRegistro(nroRegistro.text());
						titulo.setDenominacion(denominacion.text());
						titulo.setExpedido(expedido.text());
						titulo.setEspecOrient(especOrient.text());
						titulo.setResolucion(resolucion.text());
						
						puntaje.addTitulo(titulo);
					});
					
					cursos.stream().forEach(e -> {
						Curso curso = new Curso();
						Elements tds = e.select("td");
						Element anio = tds.get(0);
						Element denominacion = tds.get(1);
						Element organismo = tds.get(2);
						Element hs = tds.get(3);
						Element resolucion = tds.get(4);
						
						curso.setAnio(anio.text());
						curso.setDenominacion(denominacion.text());
						curso.setOrganismo(organismo.text());
						curso.setHs(hs.text());
						curso.setResolucion(resolucion.text());
						
						puntaje.addCurso(curso);
					});

					System.out.println("Added cursos y titulos for "+puntaje.getDni());
					this.puntajeAnualDocenteService.save(puntaje);
					
					result = this.puntajeAnualDocenteService.search(filter, new Page(currentIndex++,1));
					if (result.getTotalResults() > 0){
						stack.push(result.getResult().get(0));
					}
				}catch(Exception e){
					LogHelper.error(this,e);
				}
			}
		}catch(Exception e){
			LogHelper.error(this, e);
		}		
	}
	
	
	//PRIVATE
	private void saveOrAddPuntaje(PuntajeAnualDocente pda) throws BusinessException {
		PuntajeAnualDocenteFilter filter = new PuntajeAnualDocenteFilter();
		filter.setDni(pda.getDni());
		Result<PuntajeAnualDocente> search = this.puntajeAnualDocenteService.search(filter, new Page());
		
		if (search.getTotalResults() > 0){
			PuntajeAnualDocente savedPDA = search.getResult().get(0);
			savedPDA.getPuntaje().addAll(pda.getPuntaje());
			this.puntajeAnualDocenteService.save(savedPDA);
		}else{
			this.puntajeAnualDocenteService.save(pda);
		}
	}
}
