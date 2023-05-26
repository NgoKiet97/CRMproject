package service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.TaskModel;
import model.UserModel;
import repository.TaskRepository;
import repository.UserRepository;

public class TaskService {

	private TaskRepository taskRepository = new TaskRepository();
	private UserRepository userRepository = new UserRepository();
	
	public List<TaskModel> findAll(){
		return taskRepository.findAll();
	}
	
	public boolean addTask(String name, String startDateString, String endDateString, int userId, int jobId, int statusId) {
		Date startDate = new Date(stringToDate(startDateString).getTime());
		Date endDate = new Date(stringToDate(endDateString).getTime()); 
		return taskRepository.addTask(name, startDate, endDate, userId, jobId, statusId);
	}
	
	public boolean deleteById(int id) {
		return taskRepository.deleteById(id);
	}
	
	public TaskModel findById(int id) {
		return taskRepository.findById(id);
	}
	
	public List<TaskModel> findByUserId(int userId){
		return taskRepository.findByUserId(userId);
	}
	
	public List<TaskModel> findByJobId(int jobId){
		return taskRepository.findByJobId(jobId);
	}
	
	public TaskModel findByUserIdAndId(int userId, int id) {
		return taskRepository.findByUserIdAndId(userId, id);
	}
	
	public List<TaskModel> findByUserIdAndStatusId(int userId, int statusId){
		return taskRepository.findByUserIdAndStatusId(userId, statusId);
	}
	
	public List<TaskModel> findByUserIdAndStatusIdAndJobId(int userId, int statusId, int jobId){
		return taskRepository.findByUserIdAndStatusIdAndJobId(userId, statusId, jobId);
	}

	public List<UserModel> findUserByJobId(int jobId){
		List<UserModel> listUser = new ArrayList<UserModel>();
		List<TaskModel> listTask = taskRepository.findByJobId(jobId);
		for (TaskModel taskModel : listTask) {
			int userId = taskModel.getUserId();
			boolean sameId = false;
			for (UserModel userModel : listUser) {
				if(userId == userModel.getId()) {
					sameId = true;
					break;
				}
			}
			if(sameId == false) {
				listUser.add(userRepository.findById(userId));
			}
		}	
		return listUser;
	}
	
	
	public boolean editById(int id, String name, String startDateString, String endDateString, int userId, int jobId, int statusId) {
		
		java.util.Date startDateUtil = stringToDate(startDateString);
		java.util.Date endDateUtil = stringToDate(endDateString);
	
		Date startDate = new Date(startDateUtil.getTime());
		Date endDate = new Date(endDateUtil.getTime());

		return taskRepository.editById(id, name, startDate, endDate, userId, jobId, statusId);
	}
	
	public boolean editStatusById(int id, int statusId) {
		return taskRepository.editStatusById(id, statusId);
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
