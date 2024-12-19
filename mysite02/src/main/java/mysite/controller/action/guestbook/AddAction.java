package mysite.controller.action.guestbook;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.GuestbookDao;
import mysite.vo.GuestbookVo;

public class AddAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String contents = request.getParameter("contents");
		
		GuestbookVo vo = new GuestbookVo();
		vo.setName(name);
		vo.setPassword(password);
		vo.setContents(contents);
		
		new GuestbookDao().insert(vo);
		
		response.sendRedirect(request.getContextPath() + "/guestbook");
	}

}
