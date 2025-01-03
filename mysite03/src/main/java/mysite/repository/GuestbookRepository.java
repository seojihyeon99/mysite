package mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	private SqlSession sqlSession;
	
	public GuestbookRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public List<GuestbookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");
	}

	public int insert(GuestbookVo vo) {
		return sqlSession.insert("guestbook.insert", vo);	
	}

	public int deleteByIdAndPassword(Long id, String password) {
		Map<String, Object> map = Map.of(
									"id", id, 
									"password", password);
		
		return sqlSession.delete("guestbook.deleteByIdAndPassword", map);		
	}	
}
