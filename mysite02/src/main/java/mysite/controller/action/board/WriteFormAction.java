package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.vo.UserVo;

public class WriteFormAction implements Action {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		// Access Control
		if(session == null) {
			response.sendRedirect(request.getContextPath()+ "/user?a=loginform");
			return;
		}
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath()+ "/user?a=loginform");
			return;
		}
		
		///////////////////////////////////////////////////////////
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/write.jsp");
		rd.forward(request, response);
	}
	
}
