package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.MySQLConfig;
import model.StatusModel;

public class StatusRepository {
	
	Connection connection  = MySQLConfig.getConnection();
	
	public List<StatusModel> findAll(){
		List<StatusModel> list = new ArrayList<StatusModel>();
		String query = "SELECT * FROM status";
		try {
			PreparedStatement preparedStatement =  connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				StatusModel statusModel = new StatusModel();
				statusModel.setId(resultSet.getInt("id"));
				statusModel.setName(resultSet.getString("name"));
				list.add(statusModel);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public StatusModel findById(int id) {
		StatusModel model = new StatusModel();
		String query = "SELECT * FROM status WHERE id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				model.setId(resultSet.getInt("id"));
				model.setName(resultSet.getString("name"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error query status findById: " + e.getMessage());
		}
		return model; 
	}
}
