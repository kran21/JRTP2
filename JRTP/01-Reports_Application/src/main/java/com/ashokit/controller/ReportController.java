package com.ashokit.controller;

import com.ashokit.entity.CitizenPlan;
import com.ashokit.request.SearchRequest;

import java.util.List;

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
