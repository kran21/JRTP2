package com.ashokit.controller;

import com.ashokit.entity.CitizenPlan;
import com.ashokit.request.SearchRequest;

import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ashokit.service.ReportService;

@Controller
public class ReportController {
	@Autowired
	private ReportService service;

	@GetMapping("/excel")
	public void excelExport(HttpServletResponse response) throws Exception{
		response.setContentType("application/octet-stream");

		response.addHeader("content-Disposition","attachment;filename=plans.xls");

        service.exportExcel(response);
	}

	@GetMapping("/pdf")
	public void pdfExport(HttpServletResponse response) throws Exception{
		response.setContentType("application/pdf");

		response.addHeader("content-Disposition","attachment;filename=plans.pdf");

		service.exportPdf(response);
	}
	
	@PostMapping("/search")
	public String handleSearchRequest(@ModelAttribute("search")    SearchRequest request, Model model) {
		System.out.println(request);
		List<CitizenPlan> plans = service.search(request);
		model.addAttribute("plans",plans);
		init(model);
		return "index";
	}

	/**
	 * This method is used to load index page
	 * @param model
	 * @return String
	 */
	@GetMapping("/")
	public String indexPage(Model model) {
		model.addAttribute("search",new SearchRequest());
		
		init(model);
		
		return "index";
	}

	private void init(Model model) {
		
		model.addAttribute("planName",service.getPlanName());
		model.addAttribute("planStatus",service.getPlanStatus());
	}
}
