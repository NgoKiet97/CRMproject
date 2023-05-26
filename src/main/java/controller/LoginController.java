package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.LoginService;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"} )
public class LoginController extends HttpServlet{
	
	private LoginService loginService = new LoginService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
//		LoginDto loginDto = new LoginDto();
//		loginDto.setEmail(email);
//		loginDto.setPassword(password);
		
		boolean isSuccess = loginService.checkLogin(email, password, req);
		if (isSuccess) {
//			HttpSession session = req.getSession();
//			session.setAttribute("LOGIN_USER", loginDto);
//			session.setMaxInactiveInterval(3600);
			resp.sendRedirect(req.getContextPath() + "/dashboard");
		} else {
			req.setAttribute("message", "Email hoặc Password không đúng!");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
	}
}
