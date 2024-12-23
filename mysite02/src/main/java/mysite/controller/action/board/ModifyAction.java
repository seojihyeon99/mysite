package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

public class ModifyAction implements Action {
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
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		
		BoardVo vo = new BoardVo();
		vo.setId(id);
		vo.setTitle(title);
		vo.setContents(contents);
//		vo.setUserId(authUser.getId());
		
		new BoardDao().update(vo);
		
		response.sendRedirect(request.getContextPath() + "/board?a=view&id=" +id);
	}
}
