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

public class WriteAction implements Action {
	
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
		
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		int newId = -1;
		
		// 새글인 경우
		if(request.getParameter("id") == null) {
			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setUserId(authUser.getId());
			
			newId = new BoardDao().insert(vo);			
		}
		// 댓글인 경우 (기존 게시물의 id 값 있음)
		else {
			Long id = Long.valueOf(request.getParameter("id"));
			
			// 부모글 정보(gNo, oNo, depth) 찾아오기
			BoardVo vo = new BoardDao().findParentById(id);

			// 같은 그룹의 글들 중 새 댓글의 oNo보다 큰 글들의 oNo는 1씩 더함
			vo = new BoardDao().updateOrderNo(vo);
			
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setUserId(authUser.getId());
			
			System.out.println(vo);
			
			newId = new BoardDao().insertReply(vo);
		}
		
		response.sendRedirect(request.getContextPath() + "/board?a=view&id=" + newId);
	}
}
