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

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		System.out.println("password : ====" + password);
		int result = new UserDao().updateByEmail(email, name, password, gender);
		
		// 업데이트 실패
		if(result != 1) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/updateform.jsp");
			rd.forward(request, response);
			
			return;			
		}
		
		// 업데이트 성공
		HttpSession session = request.getSession(true);
		UserVo vo = (UserVo)session.getAttribute("authUser");
		vo.setName(name);
		vo.setGender(gender);
		if(password != null) vo.setPassword(password);
		
		System.out.println(vo.toString());
		session.setAttribute("authUser", vo);	
		response.sendRedirect(request.getContextPath() + "/user?a=updateform");
		
		System.out.println(vo);

	}
}