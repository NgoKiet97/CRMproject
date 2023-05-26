package service;

import java.util.List;

import model.RoleModel;
import repository.RoleRepository;

public class RoleService {

	private RoleRepository roleRepository = new RoleRepository(); 
	
	public List<RoleModel> findAll(){
		return roleRepository.findAll();
	} 
	
	public boolean addRole(String name, String description) {
		return roleRepository.addRole(name, description);
	}
	
	public RoleModel findById(int id) {
		return roleRepository.findById(id);
	}
	
	public  boolean editById(int id, String name, String description) {
		return roleRepository.editById(id, name, description);
	}
	
	public boolean deleteById(int id) {
		return roleRepository.deleteById(id);
	}
	
	
}
