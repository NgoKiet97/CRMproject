package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.TaskModel;
import service.TaskService;

@WebServlet(name = "DashBoardServlet", urlPatterns =  {"/dashboard"})
public class DashBoardController extends HttpServlet {
	
	private TaskService taskService = new TaskService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		float tong = 0;
    	float tongChuaHoanThanh = 0;
    	float tongDangThucHien = 0;
    	float tongDaHoanThanh = 0;
    	float tyLeChuaHoanThanh = 0; 
    	float tyLeDangThucHien = 0;
    	float tyLeDaHoanThanh = 0;
    	
		for (TaskModel taskModel : taskService.findAll()) {
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
		req.setAttribute("tongChuaHoanThanh", Math.round(tongChuaHoanThanh));
		req.setAttribute("tongDangThucHien", Math.round(tongDangThucHien));
		req.setAttribute("tongDaHoanThanh", Math.round(tongDaHoanThanh));
		
		req.setAttribute("tyLeChuaHoanThanh", tyLeChuaHoanThanh);
     	req.setAttribute("tyLeDangThucHien", tyLeDangThucHien);
     	req.setAttribute("tyLeDaHoanThanh", tyLeDaHoanThanh);
		
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
}
