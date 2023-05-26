package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "AuthFilter",  urlPatterns = {"/*"})
public class AuthFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		if (!req.getServletPath().startsWith("/login")) {
			if (req.getSession().getAttribute("LOGIN_USER") != null) {
				chain.doFilter(req, resp);
			} else {
				req.setAttribute("message", "Vui lòng đăng nhập!");
				resp.sendRedirect(req.getContextPath() + "/login");
			}
		} else {
			chain.doFilter(req, resp);
		}
	}

}
