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
	
	private final BoardDao boardDao;
	
	public ModifyAction() {
		this.boardDao = new BoardDao();
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// Access Control
		if(session == null) {
			response.sendRedirect(request.getContextPath() + "/user?a=loginform");
			return;
		}
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user?a=loginform");
			return;
		}
		
		// 해당 글을 쓴 사용자가 아닌 경우
		Long id = Long.parseLong(request.getParameter("id"));
		BoardVo vo = boardDao.findById(id);
		if(vo.getUserId() != authUser.getId()) {
			response.sendRedirect(request.getContextPath() + "/board?a=view&id=" + id);
			return;			
		}
		
		///////////////////////////////////////////////////////////
		
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		
		vo.setId(id);
		vo.setTitle(title);
		vo.setContents(contents);
//		vo.setUserId(authUser.getId());
		
		new BoardDao().update(vo);
		
		response.sendRedirect(request.getContextPath() + "/board?a=view&id=" +id);
	}
}
