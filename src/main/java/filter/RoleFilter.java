package filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.JobModel;
import model.TaskModel;
import model.UserModel;
import service.JobService;
import service.TaskService;

@WebFilter(filterName = "RoleFilter", 
	urlPatterns = {"/role/*","/user/*","/task/*","/job/*", "/profile/*", "/role","/user","/task","/job","/profile", "/profile-task"})
public class RoleFilter implements Filter{
	private TaskService taskService = new TaskService();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		UserModel user = (UserModel)req.getSession().getAttribute("LOGIN_USER");
		List<TaskModel> listTask = taskService.findByUserId(user.getId());
		
		String action = req.getServletPath();
		
		if(action.startsWith("/user")) {
			if( (user.getRoleId() == 1) || (user.getRoleId() == 2 && action.equals("/user"))) {
				chain.doFilter(req, resp);
			} else {
				resp.sendRedirect(req.getContextPath() + "/error");
			}
		}
		
		if(action.startsWith("/role")) {
			if(user.getRoleId() == 1) {
				chain.doFilter(req, resp);
			} else {
				resp.sendRedirect(req.getContextPath() + "/error");
			}
		}
		
		
		if(action.startsWith("/job")) {
			if( (user.getRoleId() == 1) || (user.getRoleId() == 2 && (action.equals("/job/add") || action.equals("/job")))) {
				chain.doFilter(req, resp);
			} else if(user.getRoleId() == 2 && (action.startsWith("/job/delete") || action.startsWith("/job/edit")|| action.startsWith("/job/detail"))){
				JobService jobService = new JobService();
				int jobId = Integer.parseInt(req.getParameter("id"));
				JobModel jobModel = jobService.findById(jobId);
				if(jobModel.getUserId() == user.getId()) {
					chain.doFilter(req, resp);
				} else {
					resp.sendRedirect(req.getContextPath() + "/error");
				}
			} else {
				resp.sendRedirect(req.getContextPath() + "/error");
			}
		}
		
		if(action.startsWith("/task")) {
			if( (user.getRoleId() == 1) || (user.getRoleId() == 2 && ( action.equals("/task") || action.equals("/task/add")))) {
				chain.doFilter(req, resp);
			} else if(user.getRoleId() == 2 && (action.startsWith("/task/delete") || action.startsWith("/task/edit"))){
				int taskId = Integer.parseInt(req.getParameter("id"));
				
				TaskModel taskSelected = taskService.findById(taskId);
				List<JobModel> listJobManage = user.findJobManage();
				boolean error = false;
				
				if (listJobManage != null) {
					for (JobModel jobModel : listJobManage) {
						if(jobModel.getId() == taskSelected.getJobId()) {
							error = true;
						}
					}
				} 
				
				if(error) {
					chain.doFilter(req, resp);
				} else {
					resp.sendRedirect(req.getContextPath() + "/error");
				}
			} else {
				resp.sendRedirect(req.getContextPath() + "/error");
			}
		}
		
		if(action.startsWith("/profile") || action.equals("/profile-task")) {
			if(action.equals("/profile")) {
				chain.doFilter(req, resp);
			} else {
				int userId = Integer.parseInt(req.getParameter("userId"));
				if(userId == user.getId()) {
					chain.doFilter(req, resp);
				} else {
					resp.sendRedirect(req.getContextPath() + "/error");
				}
			}	
		}
		
	}

}
