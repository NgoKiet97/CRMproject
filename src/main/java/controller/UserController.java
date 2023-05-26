package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.TaskModelDto;
import dto.UserModelDto;
import model.TaskModel;
import model.UserModel;
import service.RoleService;
import service.TaskService;
import service.UserService;

@WebServlet(name = "UserServlet", urlPatterns = {"/user","/user/add", "/user/detail", "/user/edit", "/user/delete"})
public class UserController extends HttpServlet{
	
	private UserService userService = new UserService();
	private RoleService roleService = new RoleService();
	private TaskService taskService = new TaskService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = req.getServletPath();
		switch (action) {
		case "/user":
			getShowList(req, resp);			
			break;
		case "/user/add":
			getAdd(req, resp);			
			break;
		case "/user/edit":
			getEdit(req, resp);			
			break;
		case "/user/detail":
			getDetail(req, resp);			
			break;
		case "/user/delete":
			getDelete(req, resp);
			break;	
		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = req.getServletPath();
		switch (action) {
		case "/user/add":
			postAdd(req, resp);			
			break;
		case "/user/edit":
			postEdit(req, resp);			
			break;
		default:
			break;
		}		
	}
	
	private void getShowList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		List<UserModelDto> listUserDto = new ArrayList<UserModelDto>();
		for (UserModel userModel : userService.findAll()) {
			listUserDto.add(userModel.changeToDto()); 
		}
		req.setAttribute("listAllUser", listUserDto);
		req.getRequestDispatcher("user-table.jsp").forward(req, resp);		
	}

	private void getAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		req.setAttribute("listAllRole", roleService.findAll());
		req.getRequestDispatcher("../user-add.jsp").forward(req, resp);
	}

	private void getEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		UserModel userModel = userService.findById(id);
		
		req.setAttribute("id", userModel.getId());
		req.setAttribute("fullname", userModel.getFullname());
		req.setAttribute("email", userModel.getEmail());
		req.setAttribute("avatar", userModel.getAvatar());
		req.setAttribute("roleId", userModel.changeToDto().getRole());
		req.setAttribute("phone", userModel.getPhone());
		req.setAttribute("listAllRole", roleService.findAll());
		
		req.getRequestDispatcher("../user-edit.jsp").forward(req, resp);

	}

	private void getDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		UserModel user = userService.findById(id);
		float tong = 0;
    	float tongChuaHoanThanh = 0;
    	float tongDangThucHien = 0;
    	float tongDaHoanThanh = 0;
    	float tyLeChuaHoanThanh = 0; 
    	float tyLeDangThucHien = 0;
    	float tyLeDaHoanThanh = 0;
		List<TaskModelDto> listTaskStart = new ArrayList<TaskModelDto>();
		List<TaskModelDto> listTaskDoing = new ArrayList<TaskModelDto>();
		List<TaskModelDto> listTaskDone = new ArrayList<TaskModelDto>();
		
		for (TaskModel task : taskService.findByUserIdAndStatusId(id, 1)) {
			tong += 1;
			tongChuaHoanThanh += 1; 
			listTaskStart.add(task.changeToDto());
		}

		for (TaskModel task : taskService.findByUserIdAndStatusId(id, 2)) {
			listTaskDoing.add(task.changeToDto());
			tong += 1;
			tongDangThucHien += 1; 
		}
		for (TaskModel task : taskService.findByUserIdAndStatusId(id, 3)) {
			listTaskDone.add(task.changeToDto());
			tong += 1;
			tongDaHoanThanh += 1;
		}
		if (tong != 0) {
			tyLeChuaHoanThanh = Float.parseFloat(String.format("%.1f", tongChuaHoanThanh/tong*100))  ; 
	     	tyLeDangThucHien = Float.parseFloat(String.format("%.1f", tongDangThucHien/tong*100));
	     	tyLeDaHoanThanh = Float.parseFloat(String.format("%.1f", tongDaHoanThanh/tong*100));
		}
		
		req.setAttribute("tyLeChuaHoanThanh", tyLeChuaHoanThanh);
     	req.setAttribute("tyLeDangThucHien", tyLeDangThucHien);
     	req.setAttribute("tyLeDaHoanThanh", tyLeDaHoanThanh);


		req.setAttribute("listTaskStart", listTaskStart);
		req.setAttribute("listTaskDoing", listTaskDoing);
		req.setAttribute("listTaskDone", listTaskDone);
		req.setAttribute("user", user);
		req.getRequestDispatcher("../user-details.jsp").forward(req, resp);		
	}

	private void getDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		userService.deleteById(id);
		resp.sendRedirect(req.getContextPath() + "/user");
	}

	private void postAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		String fullname = req.getParameter("fullname"); 
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String avatar = req.getParameter("avatar");
		int roleId = Integer.parseInt(req.getParameter("roleId"));
		String phone = req.getParameter("phone");
		
		userService.addUser(email, password, fullname, avatar, roleId, phone);
		resp.sendRedirect(req.getContextPath() + "/user");		
	}

	private void postEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));		
		String fullname = req.getParameter("fullname"); 
		String email = req.getParameter("email");
		String avatar = req.getParameter("avatar");
		int roleId = Integer.parseInt(req.getParameter("roleId"));
		String phone = req.getParameter("phone");
		
		userService.editById(id, email, fullname, avatar, roleId, phone);
		resp.sendRedirect(req.getContextPath() + "/user");
	}


}
