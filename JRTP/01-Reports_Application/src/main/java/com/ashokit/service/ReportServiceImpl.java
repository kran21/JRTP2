package com.ashokit.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
	public boolean exportExcel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exportPdf() {
		// TODO Auto-generated method stub
		return false;
	}

}
