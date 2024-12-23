package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.dao.UserDao;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

public class ModifyFormAction implements Action {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
//		// Access Control
//		if(session == null) {
//			response.sendRedirect(request.getContextPath());
//			return;
//		}
//		
//		UserVo authUser = (UserVo)session.getAttribute("authUser");
//		if(authUser == null) {
//			response.sendRedirect(request.getContextPath());
//			return;
//		}
		
		//////////////////////////////////////////////////////////
		Long id = Long.valueOf(request.getParameter("id"));
		BoardVo vo = new BoardDao().findById(id);
		
		request.setAttribute("vo", vo);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/modify.jsp");
		rd.forward(request, response);
	}
	
}
