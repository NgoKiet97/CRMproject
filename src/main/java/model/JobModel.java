package model;

import java.sql.Date;
import java.text.SimpleDateFormat;

import dto.JobModelDto;
import service.UserService;

public class JobModel {
	private int id; 
	private String name; 
	private String description;
	private int userId;
	private Date startDate; 
	private Date endDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	} 
	
	public JobModelDto changeToDto() {
		JobModelDto jobModelDto = new JobModelDto();
		UserService userService = new UserService();
		jobModelDto.setId(this.getId());
		jobModelDto.setStartDate(dateToString(new java.util.Date(this.getStartDate().getTime())));
		jobModelDto.setEndDate(dateToString(new java.util.Date(this.getEndDate().getTime())));
		jobModelDto.setName(this.getName());
		jobModelDto.setDescription(this.getDescription());
		jobModelDto.setUser(userService.findById(this.getUserId()).getFullname());
		return jobModelDto; 
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
	
	
	
	
}
