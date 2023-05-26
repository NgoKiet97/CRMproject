package model;

import java.util.ArrayList;
import java.util.List;

import dto.JobModelDto;
import dto.UserModelDto;
import service.JobService;
import service.RoleService;
import service.TaskService;

public class UserModel {
	private int id; 
	private String email;
	private String fullname; 
	private String avatar;
	private int roleId; 
	private String phone;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	} 
	
	public UserModelDto changeToDto() {
		UserModelDto userDto = new UserModelDto();
		RoleService roleService = new RoleService();
		userDto.setId(this.getId());
		userDto.setEmail(this.getEmail());
		userDto.setFullname(this.getFullname());
		userDto.setAvatar(this.getAvatar());
		userDto.setRole(roleService.findById(this.getRoleId()).getDescription());
		userDto.setPhone(this.getPhone());
		return userDto;
	}
	
	public List<JobModel> findJobManage(){
		if(this.getRoleId() == 2) {
			JobService jobService = new JobService();
			List<JobModel> listJobManage = new ArrayList<JobModel>();
			List<JobModel> listAllJob = jobService.findAll();
			for (JobModel jobModel : listAllJob) {
				if(jobModel.getUserId() == this.getId()) {
					listJobManage.add(jobModel);
				}
			}
			return listJobManage;
		} else {
			return null;
		}
	}
	
	
}
