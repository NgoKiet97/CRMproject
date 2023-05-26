package repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MySQLConfig;
import model.JobModel;

public class JobRepository {
	
	Connection connection = MySQLConfig.getConnection();
	
	public List<JobModel> findAll(){
		
		List<JobModel> list = new ArrayList<JobModel>();
		String query = "SELECT * FROM jobs";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				JobModel jobModel = new JobModel();
				jobModel.setId(resultSet.getInt("id"));
				jobModel.setName(resultSet.getString("name"));
				jobModel.setStartDate(resultSet.getDate("start_date"));
				jobModel.setEndDate(resultSet.getDate("end_date"));
				jobModel.setDescription(resultSet.getString("description"));
				jobModel.setUserId(resultSet.getInt("user_id"));
				
				list.add(jobModel);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error query jobs findAll: " + e.getMessage());
		}
		return list;
	}

	public boolean addJob(String name, Date startDate, Date endDate, String description, int userId) {
		String query = "INSERT INTO jobs (name, start_date, end_date, description, user_id) VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setDate(2, startDate);
			preparedStatement.setDate(3, endDate);
			preparedStatement.setString(4, description);
			preparedStatement.setInt(5, userId);

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
		String query = "DELETE FROM jobs WHERE id = ?";
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
			System.out.println("Error jobs deleteById: " + e.getMessage());
			return false;
		}
	}
	
	
	public JobModel findById(int id) {
		JobModel jobModel = new JobModel();
		String query = "SELECT * FROM jobs WHERE id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				jobModel.setId(resultSet.getInt("id"));
				jobModel.setName(resultSet.getString("name"));
				jobModel.setStartDate(resultSet.getDate("start_date"));
				jobModel.setEndDate(resultSet.getDate("end_date"));
				jobModel.setDescription(resultSet.getString("description"));
				jobModel.setUserId(resultSet.getInt("user_id"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error query jobs findById: " + e.getMessage());
		}
		return jobModel;
	}
	

	public boolean editById(int id, String name, Date startDate, Date endDate, String description, int userId) {
		String query = "UPDATE jobs SET name = ?, start_date = ?, end_date = ?, description = ?, user_id = ? WHERE id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setDate(2, startDate);
			preparedStatement.setDate(3, endDate);
			preparedStatement.setString(4, description);
			preparedStatement.setInt(5, userId);
			preparedStatement.setInt(6, id);
			
			int result = preparedStatement.executeUpdate();
			if(result == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error jobs editById: " + e.getMessage());			
			return false;
		}
	}
	
	
	
}
