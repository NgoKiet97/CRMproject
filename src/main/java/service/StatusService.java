package service;

import java.util.List;

import model.StatusModel;
import repository.StatusRepository;

public class StatusService {
	
	private StatusRepository statusRepository = new StatusRepository();
	
	public List<StatusModel> findAll(){
		return statusRepository.findAll();
	}
	
	public StatusModel findById(int id) {
		return statusRepository.findById(id);
	}
	
	
}
