package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.UserModel;
import repository.UserRepository;

public class LoginService {
	
	private UserRepository userRepository = new UserRepository();
	
	public boolean checkLogin(String email, String password, HttpServletRequest req) {
		List<UserModel> list = userRepository.findByEmailAndPassword(email, password);
		if(list.size() > 0) {
			
//			userModel.setId(list.get(0).getId());
//			userModel.setEmail(list.get(0).getEmail());
//			userModel.setFullname(list.get(0).getFullname());
//			userModel.setAvatar(list.get(0).getAvatar());
//			userModel.setRoleId(list.get(0).getRoleId());
//			userModel.setPhone(list.get(0).getPhone());
			
			HttpSession session = req.getSession();
			session.setAttribute("LOGIN_USER", list.get(0));
			session.setMaxInactiveInterval(3600);
			
//			UserModel user  = (UserModel)session.getAttribute("LOGIN_USER");
			return true;
		} else {
			return false;
		}
	}

}
