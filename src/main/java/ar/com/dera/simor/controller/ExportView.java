package ar.com.dera.simor.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import ar.com.dera.simor.common.entities.DOCENTES.PuntajeAnualDocente;
import ar.com.dera.simor.common.filter.Result;

@Component("exportView")
public class ExportView extends AbstractExcelView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Result<PuntajeAnualDocente> result = (Result<PuntajeAnualDocente>) model.get("result");
		// create a wordsheet
		HSSFSheet sheet = workbook.createSheet("Puntaje anual docente (mas cursos y titulos)");

		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("Month");
		header.createCell(1).setCellValue("Revenue");

		List<PuntajeAnualDocente> puntajes = result.getResult();
		puntajes.parallelStream().forEach(puntaje -> {
			// create the row data
			int lastRowNum = sheet.getPhysicalNumberOfRows();
			HSSFRow row = sheet.createRow(lastRowNum);
			row.createCell(0).setCellValue(puntaje.getApellidoNombre());
			row.createCell(1).setCellValue(puntaje.getDni());
		});

	}
}
