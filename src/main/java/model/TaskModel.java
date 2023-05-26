package model;

import java.text.SimpleDateFormat;
import java.util.Date;

import dto.TaskModelDto;
import service.JobService;
import service.StatusService;
import service.UserService;

public class TaskModel {
	private int id; 
	private String name; 
	private Date startDate; 
	private Date endDate; 
	private int userId; 
	private int jobId; 
	private int statusId; 

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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public TaskModelDto changeToDto() {
		TaskModelDto taskModelDto = new TaskModelDto();
		UserService userService = new UserService();
		JobService jobService = new JobService();
		StatusService statusService = new StatusService();
		taskModelDto.setId(this.getId());
		taskModelDto.setName(this.getName());
		taskModelDto.setStartDate(dateToString(new java.util.Date(this.getStartDate().getTime())));
		taskModelDto.setEndDate(dateToString(new java.util.Date(this.getEndDate().getTime())));
		taskModelDto.setUserId(userService.findById(this.getUserId()).getFullname());
		taskModelDto.setJobId(jobService.findById(this.getJobId()).getName());
		taskModelDto.setStatusId(statusService.findById(this.getStatusId()).getName());
		return taskModelDto; 
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
