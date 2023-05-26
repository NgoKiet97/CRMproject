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
import model.TaskModel;
import model.UserModel;
import service.TaskService;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile", "/profile-task", "/profile/edit"})
public class ProfileController extends HttpServlet{
	
	TaskService taskService = new TaskService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = req.getServletPath();
		switch (action) {
		case "/profile":
			getShowDetail(req, resp);
			break;
		case "/profile-task":
			getShowTask(req, resp);
			break;
		case "/profile/edit":
			getEdit(req, resp);
			break;
		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		postEdit(req, resp);
	}
	
	private void postEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));	
		int userId = Integer.parseInt(req.getParameter("userId"));		
		int statusId = Integer.parseInt(req.getParameter("statusId"));		
		taskService.editStatusById(id, statusId);
		resp.sendRedirect(req.getContextPath() + "/profile-task?userId=" + userId);
	}


	private void getEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		int userId = Integer.parseInt(req.getParameter("userId"));
		TaskModelDto taskModel = taskService.findByUserIdAndId(userId, id).changeToDto();
		req.setAttribute("taskModel", taskModel);
		req.getRequestDispatcher("../profile-edit.jsp").forward(req, resp);		
	}

	private void getShowDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		UserModel userModel = (UserModel)req.getSession().getAttribute("LOGIN_USER");		
		req.setAttribute("id", userModel.getId());
		req.setAttribute("fullname", userModel.getFullname());
		req.setAttribute("email", userModel.getEmail());
		req.setAttribute("avatar", userModel.getAvatar());
		req.setAttribute("role", userModel.changeToDto().getRole());
		req.setAttribute("phone", userModel.getPhone());
		
		req.getRequestDispatcher("profile.jsp").forward(req, resp);		
	}
	
	private void getShowTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		int userId = Integer.parseInt(req.getParameter("userId"));
		float tong = 0;
    	float tongChuaHoanThanh = 0;
    	float tongDangThucHien = 0;
    	float tongDaHoanThanh = 0;
    	float tyLeChuaHoanThanh = 0; 
    	float tyLeDangThucHien = 0;
    	float tyLeDaHoanThanh = 0;
    	
		List<TaskModelDto> listTaskDto = new ArrayList<TaskModelDto>();
		for (TaskModel taskModel : taskService.findByUserId(userId)) {
			listTaskDto.add(taskModel.changeToDto());
			tong += 1;
			if (taskModel.getStatusId() == 1) {
				tongChuaHoanThanh +=1 ;
			} else if (taskModel.getStatusId() == 2) {
				tongDangThucHien +=1 ;
			} else {
				tongDaHoanThanh +=1 ;
			}
		}
		

		if (tong != 0) {
			tyLeChuaHoanThanh = Float.parseFloat(String.format("%.1f", tongChuaHoanThanh/tong*100))  ; 
	     	tyLeDangThucHien = Float.parseFloat(String.format("%.1f", tongDangThucHien/tong*100));
	     	tyLeDaHoanThanh = Float.parseFloat(String.format("%.1f", tongDaHoanThanh/tong*100));
		}
		
		req.setAttribute("tyLeChuaHoanThanh", tyLeChuaHoanThanh);
     	req.setAttribute("tyLeDangThucHien", tyLeDangThucHien);
     	req.setAttribute("tyLeDaHoanThanh", tyLeDaHoanThanh);
		req.setAttribute("listTask", listTaskDto);
		req.getRequestDispatcher("profile-task.jsp").forward(req, resp);		
	}

}
