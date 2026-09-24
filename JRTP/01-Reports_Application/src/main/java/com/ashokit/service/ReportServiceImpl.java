package com.ashokit.service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.ashokit.entity.CitizenPlan;
import com.ashokit.repo.CitizenPlanRepository;
import com.ashokit.request.SearchRequest;
@Service
public class ReportServiceImpl implements ReportService {
	@Autowired
	private CitizenPlanRepository repo;

	@Override
	public List<String> getPlanName() {
		
		return repo.getPlanNames();
	}

	@Override
	public List<String> getPlanStatus() {
		
		return repo.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> search(SearchRequest request) {
		CitizenPlan citizenPlan=new CitizenPlan();
		if(null!=request.getPlanName() && !"".equals(request.getPlanName())) {
			citizenPlan.setPlanName(request.getPlanName());
		}
		
		if(null!=request.getPlanStatus() && !"".equals(request.getPlanStatus())) {
			citizenPlan.setPlanStatus(request.getPlanStatus());
		}
		
		if(null!=request.getGender() && !"".equals(request.getGender())) {
			citizenPlan.setGender(request.getGender());
		}
		
		if(null!=request.getStartDate() && !"".equals(request.getStartDate())) {
			String startDate = request.getStartDate();
			DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate=LocalDate.parse(startDate,dateTimeFormatter);
			citizenPlan.setPlanStartDate(localDate);
		}
		
		if(null!=request.getEndDate() && !"".equals(request.getEndDate())) {
			String endDate = request.getEndDate();
			DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(endDate,dateTimeFormatter);
			citizenPlan.setPlanEndDate(localDate);
		}
	    return repo.findAll(Example.of(citizenPlan));
	}

	@Override
	public boolean exportExcel(HttpServletResponse response) throws Exception{

		Workbook workbook=new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("plans-data");
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("ID");
		headerRow.createCell(1).setCellValue("Citizen Name");
		headerRow.createCell(2).setCellValue("Plan Name");
		headerRow.createCell(3).setCellValue("Plan Status");
		headerRow.createCell(4).setCellValue("Plan start Date");
		headerRow.createCell(5).setCellValue("Plan End Date");
		headerRow.createCell(6).setCellValue("Benefit Amount");

		List<CitizenPlan> records = repo.findAll();

		int dataRow=1;

		for(CitizenPlan plans : records) {
			Row row = sheet.createRow(dataRow);
			row.createCell(0).setCellValue(plans.getCitizenId());
			row.createCell(1).setCellValue(plans.getCitizenName());
			row.createCell(2).setCellValue(plans.getPlanName());
			row.createCell(3).setCellValue(plans.getPlanStatus());
			if(null!= plans.getPlanStartDate()) {
				row.createCell(4).setCellValue(plans.getPlanStartDate());
			}else{
				row.createCell(4).setCellValue("N/A");
			}
			if(null!= plans.getPlanEndDate()) {
				row.createCell(5).setCellValue(plans.getPlanEndDate());
			}else{
				row.createCell(5).setCellValue("N/A");
			}
			if(null!=plans.getBenefitAmount()) {
				row.createCell(6).setCellValue(plans.getBenefitAmount());
			}else{
				row.createCell(6).setCellValue("N/A");
			}
			dataRow++;
		}

		FileOutputStream file=new FileOutputStream(new File("plans.xls"));
		workbook.write(file);
		workbook.close();

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		return true;
	}

	@Override
	public boolean exportPdf(HttpServletResponse response) throws Exception {
           Document document=new Document(PageSize.A4);
		PdfWriter.getInstance(document,response.getOutputStream());
		document.open();

		Font headingFont=new Font(Font.HELVETICA,16,Font.BOLD);
		Paragraph p=new Paragraph("Citizen-Plan info",headingFont);
		p.setAlignment(Element.ALIGN_CENTER);
		p.setSpacingBefore(10);
		p.setSpacingAfter(10);
		p.setLeading(15);
		document.add(p);
		PdfPTable table=new PdfPTable(6);
		table.addCell("Id");
		table.addCell("Citizen Name");
		table.addCell("Plan Name");
		table.addCell("Plan Status");
		table.addCell("Plan Start Date");
		table.addCell("Plan End Date");

		List<CitizenPlan> repoAll = repo.findAll();
		for(CitizenPlan plans : repoAll){
			table.addCell(String.valueOf(plans.getCitizenId()));
			table.addCell(plans.getCitizenName());
			table.addCell(plans.getPlanName());
			table.addCell(plans.getPlanStatus());
			table.addCell(String.valueOf(plans.getPlanStartDate()));
			table.addCell(String.valueOf(plans.getPlanEndDate()));
		}

		document.add(table);
		document.close();

		return true;
	}

}
