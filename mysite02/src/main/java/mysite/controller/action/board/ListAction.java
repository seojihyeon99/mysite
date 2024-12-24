package mysite.controller.action.board;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.util.PageNavigation;
import mysite.vo.BoardVo;

public class ListAction implements Action {

	private final static int COUNT_PER_PAGE = 5;	// 페이지 당 데이터 수 크기
	private final static int NAVIGATION_SIZE = 5; 	// 네비게이션 크기
	private final BoardDao boardDao;
	
	public ListAction() {
		this.boardDao = new BoardDao();
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page")); // 현재 페이지 번호
		int start = page * COUNT_PER_PAGE - COUNT_PER_PAGE; // 현재 페이지의 시작 글 번호
		String keyword = request.getParameter("keyword") == null ? "" : request.getParameter("keyword"); // 검색어
		
		Map<String, Object> params = new HashMap<>();
		params.put("page", page);
		params.put("start", start);
		params.put("keyword", keyword);			
		params.put("listSize", COUNT_PER_PAGE);
		
		List<BoardVo> list = boardDao.findAll(params);
		
		// 페이징 처리를 위한 객체
		PageNavigation pageNavigation = makePageNavigation(params);

		request.setAttribute("list", list);
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);
		request.setAttribute("navigation", pageNavigation);
		
		System.out.println(pageNavigation.toString());
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		rd.forward(request, response);
	}
	
	public PageNavigation makePageNavigation(Map<String, Object> params) {
		int naviSize = NAVIGATION_SIZE;
		int sizePerPage = COUNT_PER_PAGE;
		int currentPage = (int)(params.get("page"));

		PageNavigation pageNavigation = new PageNavigation();
		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		
		int totalCount = boardDao.getTotalCount(params);
		pageNavigation.setTotalCount(totalCount);
		
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);

		int startPage = (currentPage - 1) / naviSize * naviSize + 1;
		pageNavigation.setStartPage(startPage);
		
		int endPage = startPage + naviSize - 1;
		if(totalPageCount < endPage)
			endPage = totalPageCount;
		pageNavigation.setEndPage(endPage);
		
		return pageNavigation;
	}

}
