package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MySQLConfig;
import model.RoleModel;

public class RoleRepository {
	
	private Connection connection = MySQLConfig.getConnection();
	
	public List<RoleModel> findAll(){
		List<RoleModel> list = new ArrayList<RoleModel>();
		String query = "SELECT * FROM roles";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				RoleModel roleModel = new RoleModel();
				roleModel.setId(resultSet.getInt("id"));
				roleModel.setName(resultSet.getString("name"));
				roleModel.setDescription(resultSet.getString("description"));
				list.add(roleModel);
			}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error query roles findAll: " + e.getMessage());
		}		
		return list;
	}
	
	public boolean addRole(String name, String description) {
		String query = "INSERT INTO roles (name, description) VALUES  (? , ?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, description);
			
			int result = preparedStatement.executeUpdate();
			if(result == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error roles addRole: " + e.getMessage());
			return false; 
		}
	}

	public RoleModel findById(int id) {
		String query = "SELECT * FROM roles WHERE id = ?";
		RoleModel roleModel = new RoleModel();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				roleModel.setId(resultSet.getInt("id"));
				roleModel.setName(resultSet.getString("name"));
				roleModel.setDescription(resultSet.getString("description"));
			}
			return roleModel;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error query roles findById: " + e.getMessage());
			return roleModel;
		}		
	}
	
	public boolean editById(int id, String name, String description) {
		String query = "UPDATE roles SET name = ?, description = ? WHERE id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, description);
			preparedStatement.setInt(3, id);
			
			int result = preparedStatement.executeUpdate();
			if(result == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error roles editById: " + e.getMessage());
			return false;
		}		
	}
	
	public boolean deleteById(int id) {
		String query = "DELETE FROM roles WHERE id = ?"; 
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
			System.out.println("Error roles deleteById: " + e.getMessage());
			return false;
		}
	}
}
