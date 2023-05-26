package service;

import java.util.List;

import model.UserModel;
import repository.UserRepository;

public class UserService {
	
	private UserRepository userRepository = new UserRepository();
	
	public List<UserModel> findAll(){
		return userRepository.findAll();
	}
	
	public UserModel findById(int id) {
		return userRepository.findById(id);
	}
	
	public boolean addUser(String email, String password, String fullname, String avatar, int roleId, String phone) {
		return userRepository.addUser(email, password, fullname, avatar, roleId, phone);
	}
	
	public boolean deleteById(int id) {
		return userRepository.deleteById(id);
	}
	
	public boolean editById(int id, String email, String fullname, String avatar, int roleId, String phone) {
		return userRepository.editById(id, email, fullname, avatar, roleId, phone);
	}
}
