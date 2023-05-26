package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.RoleModel;
import service.RoleService;

@WebServlet(name = "RoleServlet", urlPatterns = {"/role", "/role/add", "/role/edit", "/role/delete"})
public class RoleController extends HttpServlet{
	
	private RoleService roleService = new RoleService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String action = req.getServletPath();
		switch (action) {
		case "/role":
			getShowList(req, resp);
			break;
		case "/role/add":
			getAdd(req, resp);
			break;
		case "/role/edit":
			getEdit(req, resp);
			break;
		case "/role/delete":
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
		case "/role/add":
			postAdd(req, resp);
			break;
		case "/role/edit":
			postEdit(req, resp);
			break;

		default:
			break;
		}
	}
	
	private void getShowList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setAttribute("listAllRole", roleService.findAll());
		req.getRequestDispatcher("role-table.jsp").forward(req, resp);;
	}
	
	private void getAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		req.getRequestDispatcher("../role-add.jsp").forward(req, resp);
	}
	
	private void getEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		RoleModel roleModel = roleService.findById(id);
		
		req.setAttribute("id", roleModel.getId());
		req.setAttribute("name", roleModel.getName());
		req.setAttribute("description", roleModel.getDescription());
		
		req.getRequestDispatcher("../role-edit.jsp").forward(req, resp);
	}
	
	private void getDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		
		roleService.deleteById(id);
		resp.sendRedirect(req.getContextPath() + "/role");
	}

	
	
	private void postAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		boolean addSuccess = roleService.addRole(name, description);
		if (addSuccess) {
			req.setAttribute("message", "Thêm thành công!");
		} else {
			req.setAttribute("message", "Thêm thất bại!");
		}
		req.getRequestDispatcher("../role-add.jsp").forward(req, resp);
	}
	
	private void postEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("newName");
		String description = req.getParameter("newDescription");
		
		roleService.editById(id, name, description);
		resp.sendRedirect(req.getContextPath() + "/role");
	}

	

}
