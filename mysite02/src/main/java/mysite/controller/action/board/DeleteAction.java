package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class DeleteAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		HttpSession session = request.getSession();
		
//		// Access Control
//		if(session == null) {
//			response.sendRedirect(request.getContextPath());
//			return;
//		}
//		UserVo authUser = (UserVo)session.getAttribute("authUser");
//		if(authUser == null) {
//			response.sendRedirect(request.getContextPath());
//			return;
//		}
		
		///////////////////////////////////////////////////////////

		Long id = Long.parseLong(request.getParameter("id"));
		
		new BoardDao().deleteById(id);
		
		response.sendRedirect(request.getContextPath() + "/board");
	}
}
