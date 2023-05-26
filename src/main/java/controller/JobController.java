package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.JobModel;
import model.TaskModel;
import model.UserModel;
import service.JobService;
import service.TaskService;
import service.UserService;

@WebServlet(name = "JobServlet", urlPatterns = {"/job", "/job/detail", "/job/add", "/job/edit", "/job/delete"})
public class JobController extends HttpServlet{
	
	private JobService jobService = new JobService();
	private TaskService taskService = new TaskService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = req.getServletPath();
		switch (action) {
		case "/job":
			getShowList(req, resp);
			break;
		case "/job/add":
			getAdd(req, resp);
			break;	
		case "/job/detail":
			getDetail(req, resp);
			break;
		case "/job/edit":
			getEdit(req, resp);
			break;
		case "/job/delete":
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
		case "/job/add":
			postAdd(req, resp);
			break;
		case "/job/edit":
			postEdit(req, resp);
			break;	
		default:
			break;
		}
	}
	

	private void getShowList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		req.setAttribute("listAllJob", jobService.findAllDto());
		req.getRequestDispatcher("job-table.jsp").forward(req, resp);		
	}

	private void getAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		req.getRequestDispatcher("../job-add.jsp").forward(req, resp);
	}
	
	private void getDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		String jobName = jobService.findById(id).getName();
		List<UserModel> listUser = taskService.findUserByJobId(id);
		
		float tong = 0;
    	float tongChuaHoanThanh = 0;
    	float tongDangThucHien = 0;
    	float tongDaHoanThanh = 0;
    	float tyLeChuaHoanThanh = 0; 
    	float tyLeDangThucHien = 0;
    	float tyLeDaHoanThanh = 0;
		
		List<ArrayList<TaskModel>> listStart = new ArrayList<ArrayList<TaskModel>>();
		List<ArrayList<TaskModel>> listDoing = new ArrayList<ArrayList<TaskModel>>();
		List<ArrayList<TaskModel>> listDone = new ArrayList<ArrayList<TaskModel>>();

		for (UserModel userModel  : listUser) {
			int userId = userModel.getId();
			List<TaskModel> listStartByUser = taskService.findByUserIdAndStatusIdAndJobId(userId, 1, id);
			listStart.add((ArrayList<TaskModel>) listStartByUser);
			
			List<TaskModel> listDoingByUser = taskService.findByUserIdAndStatusIdAndJobId(userId, 2, id);
			listDoing.add((ArrayList<TaskModel>) listDoingByUser);
			
			List<TaskModel> listDoneByUser = taskService.findByUserIdAndStatusIdAndJobId(userId, 3, id);
			listDone.add((ArrayList<TaskModel>) listDoneByUser);
		}
		
		for (ArrayList<TaskModel> listStartTask : listStart) {
			for (TaskModel task : listStartTask) {
				tong += 1;
				tongChuaHoanThanh += 1; 
			}
		}

		for (ArrayList<TaskModel> listDoingTask  : listDoing) {
			for (TaskModel task : listDoingTask) {
				tong += 1;
				tongDangThucHien += 1; 
			}
		}
		for (ArrayList<TaskModel> listDoneTask  : listDone) {
			for (TaskModel task : listDoneTask) {
				tong += 1;
				tongDaHoanThanh += 1; 
			}
		}
		
		if (tong != 0) {
			tyLeChuaHoanThanh = Float.parseFloat(String.format("%.1f", tongChuaHoanThanh/tong*100))  ; 
	     	tyLeDangThucHien = Float.parseFloat(String.format("%.1f", tongDangThucHien/tong*100));
	     	tyLeDaHoanThanh = Float.parseFloat(String.format("%.1f", tongDaHoanThanh/tong*100));
		}
		req.setAttribute("jobName", jobName);

		req.setAttribute("listUser", listUser);

		req.setAttribute("tyLeChuaHoanThanh", tyLeChuaHoanThanh);
     	req.setAttribute("tyLeDangThucHien", tyLeDangThucHien);
     	req.setAttribute("tyLeDaHoanThanh", tyLeDaHoanThanh);
		
		req.setAttribute("listStart", listStart);
		req.setAttribute("listDoing", listDoing);
		req.setAttribute("listDone", listDone);

		req.getRequestDispatcher("../job-details.jsp").forward(req, resp);
	}
	
	private void getEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		JobModel jobModel = jobService.findById(id);
		UserService userService = new UserService();
		List<UserModel> listUser = userService.findAll();
		
		req.setAttribute("id", jobModel.getId());
		req.setAttribute("name", jobModel.getName());
		req.setAttribute("startDate", jobModel.getStartDate());
		req.setAttribute("endDate", jobModel.getEndDate());
		req.setAttribute("description", jobModel.getDescription());
		req.setAttribute("user", userService.findById(jobModel.getUserId()).getFullname());
		req.setAttribute("listUser", listUser);
		
		req.getRequestDispatcher("../job-edit.jsp").forward(req, resp);
	}

	
	private void getDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		jobService.deleteById(id);
		resp.sendRedirect(req.getContextPath() + "/job");
	}
	
	
	private void postAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		
		String name = req.getParameter("name");
		String startDateString = req.getParameter("startDate");
		String endDateString = req.getParameter("endDate");
		String description = req.getParameter("description");
		UserModel user = (UserModel)req.getSession().getAttribute("LOGIN_USER");
		int userId = user.getId();
		
		boolean addSuccess = jobService.addJob(name, startDateString, endDateString, description,  userId);
		if (addSuccess) {
			req.setAttribute("message", "Thêm thành công!");
		} else {
			req.setAttribute("message", "Thêm thất bại!");
		}
		req.getRequestDispatcher("../job-add.jsp").forward(req, resp);
	}
	
	private void postEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		int userId = Integer.parseInt(req.getParameter("userId"));
		
		jobService.editById(id, name, startDate, endDate, description, userId);
		resp.sendRedirect(req.getContextPath() + "/job");
	}


	

}
