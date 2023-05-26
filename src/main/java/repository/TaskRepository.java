package repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.User;

import config.MySQLConfig;
import model.TaskModel;
import model.UserModel;

public class TaskRepository {
	private Connection connection = MySQLConfig.getConnection();

	public List<TaskModel> findAll(){
		List<TaskModel> list = new ArrayList<TaskModel>();
		String query = "SELECT * FROM tasks";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				TaskModel taskModel = new TaskModel();
				taskModel.setId(resultSet.getInt("id"));
				taskModel.setName(resultSet.getString("name"));
				taskModel.setStartDate(resultSet.getDate("start_date"));
				taskModel.setEndDate(resultSet.getDate("end_date"));
				taskModel.setUserId(resultSet.getInt("user_id"));
				taskModel.setJobId(resultSet.getInt("job_id"));
				taskModel.setStatusId(resultSet.getInt("status_id"));
				list.add(taskModel);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error query tasks findAll: " + e.getMessage());
		}
		return list;
	}

	public boolean addTask(String name, Date startDate, Date endDate, int userId, int jobId, int statusId) {
		String query = "INSERT INTO tasks (name, start_date, end_date, user_id, job_id, status_id) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setDate(2, startDate);
			preparedStatement.setDate(3, endDate);
			preparedStatement.setInt(4, userId);
			preparedStatement.setInt(5, jobId);
			preparedStatement.setInt(6, statusId);
			int result = preparedStatement.executeUpdate();
			if (result == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error jobs addJob: " + e.getMessage());
			return false; 
		}		
	}
	
	
	public boolean deleteById(int id) {		
		String query = "DELETE FROM tasks WHERE id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			int result = preparedStatement.executeUpdate();
			if(result == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error tasks deleteById: " + e.getMessage());
			return false;
		}
	}
	
	public TaskModel findById(int id) {
		TaskModel taskModel = new TaskModel();
		String query = "SELECT * FROM tasks WHERE id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				taskModel.setId(resultSet.getInt("id"));
				taskModel.setName(resultSet.getString("name"));
				taskModel.setStartDate(resultSet.getDate("start_date"));
				taskModel.setEndDate(resultSet.getDate("end_date"));
				taskModel.setUserId(resultSet.getInt("user_id"));
				taskModel.setJobId(resultSet.getInt("job_id"));
				taskModel.setStatusId(resultSet.getInt("status_id"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error query tasks findById: " + e.getMessage());
		}	
		return taskModel; 
	}
	
	public List<TaskModel> findByUserId(int userId){
		List<TaskModel> list = new ArrayList<TaskModel>();
		String query = "SELECT * FROM tasks WHERE user_id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				TaskModel taskModel = new TaskModel();
				taskModel.setId(resultSet.getInt("id"));
				taskModel.setName(resultSet.getString("name"));
				taskModel.setStartDate(resultSet.getDate("start_date"));
				taskModel.setEndDate(resultSet.getDate("end_date"));
				taskModel.setUserId(resultSet.getInt("user_id"));
				taskModel.setJobId(resultSet.getInt("job_id"));
				taskModel.setStatusId(resultSet.getInt("status_id"));
				
				list.add(taskModel);
			}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error query tasks findByUserId: " + e.getMessage());
		}
		return list;
	}
	
	public List<TaskModel> findByJobId(int jobId){
		List<TaskModel> list = new ArrayList<TaskModel>();
		String query = "SELECT * FROM tasks WHERE job_id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, jobId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				TaskModel taskModel = new TaskModel();
				taskModel.setId(resultSet.getInt("id"));
				taskModel.setName(resultSet.getString("name"));
				taskModel.setStartDate(resultSet.getDate("start_date"));
				taskModel.setEndDate(resultSet.getDate("end_date"));
				taskModel.setUserId(resultSet.getInt("user_id"));
				taskModel.setJobId(resultSet.getInt("job_id"));
				taskModel.setStatusId(resultSet.getInt("status_id"));
				
				list.add(taskModel);
			}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error query tasks findByJobId: " + e.getMessage());
		}
		return list;
	}
	
	public TaskModel findByUserIdAndId(int userId, int id){
		TaskModel taskModel = new TaskModel();
		String query = "SELECT * FROM tasks WHERE id = ? AND user_id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.setInt(2, userId);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				taskModel.setId(resultSet.getInt("id"));
				taskModel.setName(resultSet.getString("name"));
				taskModel.setStartDate(resultSet.getDate("start_date"));
				taskModel.setEndDate(resultSet.getDate("end_date"));
				taskModel.setUserId(resultSet.getInt("user_id"));
				taskModel.setJobId(resultSet.getInt("job_id"));
				taskModel.setStatusId(resultSet.getInt("status_id"));
			}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error query tasks findByUserIdAndId: " + e.getMessage());
		}
		return taskModel;
	}
	
	public List<TaskModel> findByUserIdAndStatusId(int userId, int statusId){
		List<TaskModel> list = new ArrayList<TaskModel>();
		String query = "SELECT * FROM tasks WHERE user_id = ? AND status_id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, statusId);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				TaskModel taskModel = new TaskModel();
				taskModel.setId(resultSet.getInt("id"));
				taskModel.setName(resultSet.getString("name"));
				taskModel.setStartDate(resultSet.getDate("start_date"));
				taskModel.setEndDate(resultSet.getDate("end_date"));
				taskModel.setUserId(resultSet.getInt("user_id"));
				taskModel.setJobId(resultSet.getInt("job_id"));
				taskModel.setStatusId(resultSet.getInt("status_id"));
				list.add(taskModel);
			}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error query tasks findByUserIdAndStatusId: " + e.getMessage());
		}
		return list;
	}
	
	public List<TaskModel> findByUserIdAndStatusIdAndJobId(int userId, int statusId, int jobId){
		List<TaskModel> list = new ArrayList<TaskModel>();
		String query = "SELECT * FROM tasks WHERE user_id = ? AND status_id = ? AND job_id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, statusId);
			preparedStatement.setInt(3, jobId);


			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				TaskModel taskModel = new TaskModel();
				taskModel.setId(resultSet.getInt("id"));
				taskModel.setName(resultSet.getString("name"));
				taskModel.setStartDate(resultSet.getDate("start_date"));
				taskModel.setEndDate(resultSet.getDate("end_date"));
				taskModel.setUserId(resultSet.getInt("user_id"));
				taskModel.setJobId(resultSet.getInt("job_id"));
				taskModel.setStatusId(resultSet.getInt("status_id"));
				list.add(taskModel);
			}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error query tasks findByUserIdAndStatusIdAndJobId: " + e.getMessage());
		}
		return list;
	}
	
	
	public boolean editById(int id, String name, Date startDate, Date endDate, int userId, int jobId, int statusId) {
		String query = "UPDATE tasks SET name = ?, start_date = ?, end_date =?, user_id = ?, job_id = ?, status_id = ?  WHERE id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setDate(2, startDate);
			preparedStatement.setDate(3, endDate);
			preparedStatement.setInt(4, userId);
			preparedStatement.setInt(5, jobId);
			preparedStatement.setInt(6, statusId);
			preparedStatement.setInt(7, id);
			int result = preparedStatement.executeUpdate();
			if (result == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error tasks editById: " + e.getMessage());
			return false; 
		}
	}
	
	public boolean editStatusById(int id, int statusId) {
		String query = "UPDATE tasks SET status_id = ?  WHERE id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, statusId);
			preparedStatement.setInt(2, id);
			int result = preparedStatement.executeUpdate();
			if (result == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error tasks editStatusById: " + e.getMessage());
			return false; 
		}
	}
	
}
