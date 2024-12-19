package mysite.controller.action.user;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.UserDao;
import mysite.vo.UserVo;

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVo vo = new UserDao().findByEmailAndPassword(email, password);
		
		System.out.println(vo);
		
		// 로그인 실패
		if(vo == null) {
//			response.sendRedirect(request.getContextPath() + "/user?a=loginform&result=fail");

			request.setAttribute("result", "fail");
			request.setAttribute("email", email);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp");
			rd.forward(request, response);
			
			return;
		}
		
		// 로그인 처리
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", vo);
		
		response.sendRedirect(request.getContextPath());
	}
}