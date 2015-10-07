package ar.com.dera.simor.controller;

import java.util.Map;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import ar.com.dera.simor.common.entities.DOCENTES.Puntaje;
import ar.com.dera.simor.common.entities.DOCENTES.PuntajeAnualDocente;
import ar.com.dera.simor.common.entities.DOCENTES.PuntajeAnualDocenteFilter;
import ar.com.dera.simor.common.filter.Page;
import ar.com.dera.simor.common.filter.Result;
import ar.com.dera.simor.service.impl.DOCENTES.PuntajeAnualDocenteServiceImpl;

@Component("exportView")
public class ExportView extends AbstractExcelView {

	@Autowired
	private PuntajeAnualDocenteServiceImpl service;
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		
		int offset = 1;
		PuntajeAnualDocenteFilter puntajeAnualDocenteFilter = new PuntajeAnualDocenteFilter();
		Result<PuntajeAnualDocente> result = this.getService().search(puntajeAnualDocenteFilter, new Page(offset,1));
		
		// create a wordsheet
		HSSFSheet sheet = workbook.createSheet("Puntaje anual docente (mas cursos y titulos)");

		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("Apellido y Nombre");
		header.createCell(1).setCellValue("DNI");
		header.createCell(2).setCellValue("Puntaje");
		header.createCell(3).setCellValue("Distrito");
		header.createCell(4).setCellValue("Codigo escuela");
		header.createCell(5).setCellValue("Tipo escuela");
		header.createCell(6).setCellValue("Cargo");
		header.createCell(7).setCellValue("Año");
		header.createCell(8).setCellValue("Cantidad de titulos");
		header.createCell(9).setCellValue("Cantidad de cursos");

		Stack<PuntajeAnualDocente> puntajes = new Stack<>();
		puntajes.addAll(result.getResult());
		int rowCount = 1;
		while (!puntajes.isEmpty()) {
			PuntajeAnualDocente puntaje = puntajes.pop();
			// create the row data
			if (CollectionUtils.isNotEmpty(puntaje.getPuntaje())){
				for (Puntaje p : puntaje.getPuntaje()){
					if ((rowCount % 65535) == 0){
						String sheetname = "(cont.) " + (int)(rowCount / 65535);
						sheet = workbook.createSheet(sheetname);
						HSSFRow innerHeader = sheet.createRow(0);
						innerHeader.createCell(0).setCellValue("Apellido y Nombre");
						innerHeader.createCell(1).setCellValue("DNI");
						innerHeader.createCell(2).setCellValue("Puntaje");
						innerHeader.createCell(3).setCellValue("Distrito");
						innerHeader.createCell(4).setCellValue("Codigo escuela");
						innerHeader.createCell(5).setCellValue("Tipo escuela");
						innerHeader.createCell(6).setCellValue("Cargo");
						innerHeader.createCell(7).setCellValue("Año");
						innerHeader.createCell(8).setCellValue("Cantidad de titulos");
						innerHeader.createCell(9).setCellValue("Cantidad de cursos");						
					}
					int lastRowNum = sheet.getPhysicalNumberOfRows();
					HSSFRow innerRow = sheet.createRow(lastRowNum);
					innerRow.createCell(0).setCellValue(puntaje.getApellidoNombre());
					innerRow.createCell(1).setCellValue(puntaje.getDni());
					innerRow.createCell(2).setCellValue(p.getPuntaje());
					innerRow.createCell(3).setCellValue(p.getDistrito());
					innerRow.createCell(4).setCellValue(p.getCodigoEscuela());
					innerRow.createCell(5).setCellValue(p.getTipoEscuela());
					innerRow.createCell(6).setCellValue(p.getCargo());
					innerRow.createCell(7).setCellValue(p.getAnio());
					innerRow.createCell(8).setCellValue(CollectionUtils.size(puntaje.getCursos()));
					innerRow.createCell(9).setCellValue(CollectionUtils.size(puntaje.getTitulos()));
					
					rowCount++;
				}
			}
			if ( (offset % 1000) == 0){
				System.out.println("Registro "+offset);
			}

			offset++;
			result = this.getService().search(puntajeAnualDocenteFilter, new Page(offset,1));
			if (CollectionUtils.isNotEmpty(result.getResult())){
				puntajes.push(result.getResult().get(0));
			}
		}
		System.out.println("END OF REPORT");
	}

	public PuntajeAnualDocenteServiceImpl getService() {
		return service;
	}

	public void setService(PuntajeAnualDocenteServiceImpl service) {
		this.service = service;
	}
}
