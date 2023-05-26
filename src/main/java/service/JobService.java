package service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dto.JobModelDto;
import model.JobModel;
import repository.JobRepository;

public class JobService {
	
	private JobRepository jobRepository = new JobRepository();
	
	public List<JobModel> findAll(){
		return jobRepository.findAll();
	}
	
	public List<JobModelDto> findAllDto(){
		List<JobModelDto> listJobModelDto = new ArrayList<JobModelDto>();
		for (JobModel jobModel : jobRepository.findAll()) {
			listJobModelDto.add(jobModel.changeToDto());
		}
		return listJobModelDto;
	}
	
	public boolean addJob(String name, String startDateString, String endDateString, String description, int userId) {
		Date startDate = new Date(stringToDate(startDateString).getTime());
		Date endDate = new Date(stringToDate(endDateString).getTime());
		return jobRepository.addJob(name, startDate, endDate, description, userId);
	}

	public boolean deleteById(int id) {
		return jobRepository.deleteById(id);
	}
	
	public JobModel findById(int id) {
		return jobRepository.findById(id);
	}
	
	public boolean editById(int id, String name, String startDateString, String endDateString, String description, int userId) {
		Date startDate = new Date(stringToDate(startDateString).getTime());
		Date endDate = new Date(stringToDate(endDateString).getTime()); 
		return jobRepository.editById(id, name, startDate, endDate, description, userId);
	}
	
	public String dateToString (java.util.Date date) {
		String dateString = "";
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			dateString = formatter.format(date);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return dateString;
	}
	
	public java.util.Date stringToDate(String dateString){
		java.util.Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date = formatter.parse(dateString);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return date;
	}
	
}
