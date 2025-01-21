//package mysite.service;
//
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.stereotype.Service;
//
//import mysite.repository.BoardRepository;
//import mysite.vo.BoardVo;
//
//@Service
//public class BoardService {
//	private BoardRepository boardRepository;
//	
//	public BoardService(BoardRepository boardRepository) {
//		this.boardRepository = boardRepository;
//	}
//
//	public void addContents(BoardVo vo) {
//		// 새 글인 경우
//		if(vo.getgNo() != null) {
//			
//		}
//		// 댓글인 경우 (기존 게시물의 id 값 있음)
//		
//		boardRepository.insert(vo);
//	}
//	
//	public BoardVo getContents(Long id) {
//		return boardRepository.findById(id);
//	}
//	
//	public BoardVo getContents(Long id, Long userId) {
//		return boardRepository.findByIdAndUserId(id, userId);
//	}
//	
//	public void updateContents(BoardVo vo) {
//		boardRepository.update(vo);
//	}
//	
//	public void deleteContents(Long id, Long userId) {
//		boardRepository.deleteByIdAndUserId(id, userId);
//	}
//	
//	public Map<String, Object> getContentsList(int currentPage, String keyword) {
//		List<BoardVo> list = boardRepository.findByCurrentPageAndKeyword(currentPage, keyword);
//		
//		
//		// view의 pagination를 위한 데이터 값 계산
//		
//		
//		return null;
//	}
//}
//
