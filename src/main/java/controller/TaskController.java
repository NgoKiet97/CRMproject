package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.JobModelDto;
import dto.TaskModelDto;
import model.JobModel;
import model.TaskModel;
import model.UserModel;
import repository.TaskRepository;
import service.JobService;
import service.StatusService;
import service.TaskService;
import service.UserService;

@WebServlet(name = "TaskServlet", urlPatterns = {"/task", "/task/add", "/task/edit", "/task/delete", "/task/detail" })
public class TaskController extends HttpServlet{
	
	private TaskService taskService = new TaskService();
	private UserService userService = new UserService();
	private JobService jobService = new JobService();
	private StatusService statusService = new StatusService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = req.getServletPath();
		switch (action) {
		case "/task":
			getShowList(req, resp);			
			break;
		case "/task/add":
			getAdd(req, resp);			
			break;
		case "/task/edit":
			getEdit(req, resp);			
			break;
		case "/task/detail":
			getDetail(req, resp);			
			break;
		case "/task/delete":
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
		case "/task/add":
			postAdd(req, resp);			
			break;
		case "/task/edit":
			postEdit(req, resp);			
			break;
		default:
			break;
		}		
	}

	

	private void getShowList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		UserModel user = (UserModel)req.getSession().getAttribute("LOGIN_USER");
		List<TaskModelDto> listTaskDto = new ArrayList<TaskModelDto>();

		if(user.getRoleId() == 1) {
			for (TaskModel taskModel : taskService.findAll()) {
				listTaskDto.add(taskModel.changeToDto());
			}
		} else if(user.getRoleId() == 2) {
			List<JobModel> listJobManage = user.findJobManage();
			for (JobModel jobModel : listJobManage) {
				List<TaskModel> listTask = taskService.findByJobId(jobModel.getId());
				for (TaskModel taskModel : listTask) {
					listTaskDto.add(taskModel.changeToDto());
				}
			}
		}
		req.setAttribute("listAllTask", listTaskDto);
		req.getRequestDispatcher("task-table.jsp").forward(req, resp);		
	}

	private void getAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserModel user = (UserModel)req.getSession().getAttribute("LOGIN_USER");
		List<JobModelDto> listJob = new ArrayList<JobModelDto>();
		if(user.getRoleId() == 1) {
			listJob = jobService.findAllDto();
		} else if(user.getRoleId() == 2){
			List<JobModel> listJobManage = user.findJobManage();
			for (JobModel jobModel : listJobManage) {
				listJob.add(jobModel.changeToDto());
			}
		}
		
		req.setAttribute("listUser", userService.findAll());
		req.setAttribute("listJob", listJob);
		req.setAttribute("listStatus", statusService.findAll());
		req.getRequestDispatcher("../task-add.jsp").forward(req, resp);		
	}

	private void getEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		TaskModel taskModel = taskService.findById(id);
		
		req.setAttribute("id", taskModel.getId());
		req.setAttribute("name", taskModel.getName());
		req.setAttribute("startDate", taskModel.changeToDto().getStartDate());
		req.setAttribute("endDate", taskModel.changeToDto().getEndDate());
		req.setAttribute("job", jobService.findById(taskModel.getJobId()).getName() );
		req.setAttribute("user", userService.findById(taskModel.getUserId()).getFullname());
		req.setAttribute("status", taskService.findById(taskModel.getStatusId()).getName());
		
		req.setAttribute("listUser", userService.findAll());
		req.setAttribute("listJob", jobService.findAll());
		req.setAttribute("listStatus", statusService.findAll());
		
		req.getRequestDispatcher("../task-edit.jsp").forward(req, resp);
	}

	private void getDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		
	}

	private void getDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		userService.deleteById(id);
		resp.sendRedirect(req.getContextPath() + "/task");
	}
	
	private void postAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		String name = req.getParameter("name");
		int jobId = Integer.parseInt(req.getParameter("jobId"));
		int userId =Integer.parseInt(req.getParameter("userId"));
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		int statusId = Integer.parseInt(req.getParameter("statusId"));
		
		taskService.addTask(name, startDate, endDate, userId, jobId, statusId);
		resp.sendRedirect(req.getContextPath() + "/task");

		
	}

	private void postEdit(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));		
		String name = req.getParameter("name");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		int userId = Integer.parseInt(req.getParameter("userId"));		
		int statusId = Integer.parseInt(req.getParameter("statusId"));		
		int jobId = Integer.parseInt(req.getParameter("jobId"));		
		taskService.editById(id, name, startDate, endDate, userId, jobId, statusId);
		resp.sendRedirect(req.getContextPath() + "/task");
	}
}
