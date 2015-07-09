package ar.com.dera.simor.DOCENTES;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ar.com.dera.simor.common.entities.DOCENTES.PuntajeAnualDocente;
import ar.com.dera.simor.common.entities.DOCENTES.PuntajeAnualDocenteFilter;
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
	@Test
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
										pda.add(cargo, puntaje, distrito, String.valueOf(anio), String.valueOf(escuela), tipoEscuela);
												
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
