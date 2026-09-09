package com.ashokit.service;

import java.util.List;

import com.ashokit.entity.CitizenPlan;
import com.ashokit.request.SearchRequest;

public interface ReportService {
	public List<String> getPlanName();
	
	public List<String> getPlanStatus();
	
	public List<CitizenPlan> search(SearchRequest request);
	
	public boolean exportExcel();
	
	public boolean exportPdf();

}
