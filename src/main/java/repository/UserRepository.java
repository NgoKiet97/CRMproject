package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MySQLConfig;
import model.UserModel;

public class UserRepository {

	private Connection connection = MySQLConfig.getConnection();

	public List<UserModel> findByEmailAndPassword(String email, String password) {
		List<UserModel> list = new ArrayList<UserModel>();
		
		String query = "SELECT * FROM users WHERE email = ? and  password = ?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UserModel userModel = new UserModel();
				userModel.setId(resultSet.getInt("id"));
				userModel.setEmail(resultSet.getString("email"));
				userModel.setFullname(resultSet.getString("fullname"));
				userModel.setAvatar(resultSet.getString("avatar"));
				userModel.setRoleId(resultSet.getInt("role_id"));
				userModel.setPhone(resultSet.getString("phone"));
				list.add(userModel);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error query user findByEmailAndPassword: " + e.getMessage());
		}
		return list;
	}
	
	public UserModel findById(int id) {
		UserModel userModel = new UserModel();
		String query = "SELECT * FROM users WHERE id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				userModel.setId(resultSet.getInt("id"));
				userModel.setEmail(resultSet.getString("email"));
				userModel.setFullname(resultSet.getString("fullname"));
				userModel.setAvatar(resultSet.getString("avatar"));
				userModel.setRoleId(resultSet.getInt("role_id"));
				userModel.setPhone(resultSet.getString("phone"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error query users findById: " + e.getMessage());
		}
		return userModel;
	}
	
	public List<UserModel> findAll() {
		List<UserModel> list = new ArrayList<UserModel>();
		String query = "SELECT * FROM users";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UserModel userModel = new UserModel();
				userModel.setId(resultSet.getInt("id"));
				userModel.setEmail(resultSet.getString("email"));
				userModel.setFullname(resultSet.getString("fullname"));
				userModel.setAvatar(resultSet.getString("avatar"));
				userModel.setRoleId(resultSet.getInt("role_id"));
				userModel.setPhone(resultSet.getString("phone"));
				list.add(userModel);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error query user findAll: " + e.getMessage());
		}
		return list;
	}

	public boolean addUser(String email, String password, String fullname, String avatar, int roleId, String phone) {
		String query = "INSERT INTO users (email, password, fullname, avatar, role_id, phone) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, fullname);
			preparedStatement.setString(4, avatar);
			preparedStatement.setInt(5, roleId);
			preparedStatement.setString(6, phone);
			int result = preparedStatement.executeUpdate();
			if (result == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error users addUser: " + e.getMessage());
			return false; 
		}		
	}
	
	public boolean deleteById(int id) {		
		String query = "DELETE FROM users WHERE id = ?";
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
			System.out.println("Error users deleteById: " + e.getMessage());
			return false;
		}
	}
	
	public boolean editById(int id, String email, String fullname, String avatar, int roleId, String phone) {
		String query = "UPDATE users SET email = ?, fullname = ?, avatar =?, role_id = ?, phone = ? WHERE id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, fullname);
			preparedStatement.setString(3, avatar);
			preparedStatement.setInt(4, roleId);
			preparedStatement.setString(5, phone);
			preparedStatement.setInt(6, id);
			int result = preparedStatement.executeUpdate();
			if (result == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error users editById: " + e.getMessage());
			return false; 
		}		
	}
}
