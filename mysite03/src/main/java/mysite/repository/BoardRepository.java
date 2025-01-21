//package mysite.repository;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import mysite.util.DBConnectionUtil;
//import mysite.vo.BoardVo;
//
//public class BoardRepository {
//	
//	public List<BoardVo> findAll(Map<String, Object> params) {
//		StringBuilder sql = new StringBuilder();
//		sql.append("select id, title, hit, (select name from user where id = user_id) 'name', date_format(reg_date, '%Y-%m-%d %H:%i:%s') 'reg_date', g_no, o_no, depth, user_id \n");
//		sql.append("from board \n");
//		
//		String keyword = (String) params.get("keyword");
//		// 검색어가 있는 경우
//		if(!keyword.isEmpty()) {
//			sql.append("where title like concat('%', ?, '%') \n");
//		}
//		sql.append("order by g_no desc, o_no asc \n");
//		sql.append("limit ?, ?");
//		
//		//////////////////////////////////////////
//		
//		List<BoardVo> result = new ArrayList<>();
//		
//		try (
//			Connection conn = DBConnectionUtil.getConnection();
//			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
//		) {
//			int idx = 1; // 검색어에 따른 ? 값의 순서 처리를 위함
//			// 검색어가 있는 경우
//			if(!keyword.isEmpty()) {
//				pstmt.setString(idx++, keyword);
//			}
//			
//			pstmt.setInt(idx++, (int)params.get("start"));
//			pstmt.setInt(idx++, (int)params.get("listSize"));
//			
//			ResultSet rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				BoardVo vo = new BoardVo();
//				vo.setId(rs.getLong("id"));
//				vo.setTitle(rs.getString("title"));
//				vo.setHit(rs.getInt("hit"));
//				vo.setName(rs.getString("name"));
//				vo.setRegDate(rs.getString("reg_date"));
//				vo.setgNo(rs.getInt("g_no"));
//				vo.setoNo(rs.getInt("o_no"));
//				vo.setDepth(rs.getInt("depth"));
//				vo.setUserId(rs.getLong("user_id"));
//				
//				result.add(vo);
//			}
//			
//			rs.close();
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} 
//		
//		return result;
//	}
//	
//	public int getTotalCount(Map<String, Object> params) {
//		StringBuilder sql = new StringBuilder();
//		sql.append("select count(id) \n");
//		sql.append("from board \n");
//		
//		String keyword = (String) params.get("keyword");
//		// 검색어가 있는 경우
//		if(!keyword.isEmpty()) {
//			sql.append("where title like concat('%', ?, '%') \n");
//		}
//		
//		//////////////////////////////////////////
//		
//		int cnt = 0;
//		
//		try (
//			Connection conn = DBConnectionUtil.getConnection();
//			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
//		) {
//			// 검색어가 있는 경우
//			if(!keyword.isEmpty()) {
//				pstmt.setString(1, keyword);
//			}
//			
//			ResultSet rs = pstmt.executeQuery();
//			
//			if(rs.next()) {
//				cnt = rs.getInt(1);
//			}
//			
//			rs.close();
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} 
//		
//		return cnt;
//	}
//	
//
//	public BoardVo findParentById(Long id) {
//		BoardVo boardVo = null;
//		
//		try (
//			Connection conn = DBConnectionUtil.getConnection();
//			PreparedStatement pstmt = conn.prepareStatement("select g_no, o_no, depth from board where id = ?");
//		) {
//			pstmt.setLong(1, id);
//			
//			ResultSet rs = pstmt.executeQuery();
//			if(rs.next()) {
//				int gNo = rs.getInt(1);
//				int oNo = rs.getInt(2);
//				int depth = rs.getInt(3);
//				
//				boardVo = new BoardVo();
//				boardVo.setgNo(gNo);
//				boardVo.setoNo(oNo);
//				boardVo.setDepth(depth);
//			}
//			
//			rs.close();
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} 
//		
//		return boardVo;
//	}	
//	
//	public BoardVo findById(Long id) {
//		BoardVo boardVo = null;
//		
//		try (
//			Connection conn = DBConnectionUtil.getConnection();
//			PreparedStatement pstmt = conn.prepareStatement("select title, contents, g_no, o_no, depth, user_id from board where id = ?");
//		) {
//			pstmt.setLong(1, id);
//			
//			ResultSet rs = pstmt.executeQuery();
//			if(rs.next()) {
//				String title = rs.getString(1);
//				String contents = rs.getString(2);
//				int gNo = rs.getInt(3);
//				int oNo = rs.getInt(4);
//				int depth = rs.getInt(5);
//				Long userId = rs.getLong(6);
//				
//				boardVo = new BoardVo();
//				boardVo.setId(id);
//				boardVo.setTitle(title);
//				boardVo.setContents(contents);
//				boardVo.setgNo(gNo);
//				boardVo.setoNo(oNo);
//				boardVo.setDepth(depth);
//				boardVo.setUserId(userId);
//			}
//			
//			rs.close();
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} 
//		
//		return boardVo;
//	}	
//	
//	public int insert(BoardVo vo) {
//		int newId = 0;
//		
//		try (
//			Connection conn = DBConnectionUtil.getConnection();
//			PreparedStatement pstmt = conn.prepareStatement("insert into board (title, contents, hit, reg_date, g_no, o_no, depth, user_id) "
//					+ "select ?, ?, 0, now(), ifnull(max(g_no), 0) + 1, 1, 0, ? from board",
//					PreparedStatement.RETURN_GENERATED_KEYS);
//		) {
//			pstmt.setString(1, vo.getTitle());
//			pstmt.setString(2, vo.getContents());
//			pstmt.setLong(3, vo.getUserId());
//			
//			int count = pstmt.executeUpdate();
//			
//			if(count > 0) {
//				ResultSet rs = pstmt.getGeneratedKeys();
//				if(rs.next()) {
//					newId = rs.getInt(1);
//				}
//			}
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} 
//		
//		return newId;			
//	}
//	
//	public int insertReply(BoardVo vo) {
//		int newId = 0;
//		
//		try (
//			Connection conn = DBConnectionUtil.getConnection();
//			PreparedStatement pstmt = conn.prepareStatement("insert into board (title, contents, hit, reg_date, g_no, o_no, depth, user_id) "
//															+ "values (?, ?, 0, now(), ?, ?, ?, ?)",
//															PreparedStatement.RETURN_GENERATED_KEYS);
//		) {
//			pstmt.setString(1, vo.getTitle());
//			pstmt.setString(2, vo.getContents());
//			pstmt.setInt(3, vo.getgNo()); 		// 부모글의 group_no
//			pstmt.setInt(4, vo.getoNo() + 1); 	// 부모글의 order_No + 1
//			pstmt.setInt(5, vo.getDepth() + 1); // 부모글의 depth + 1
//			pstmt.setLong(6, vo.getUserId());
//			
//			int count = pstmt.executeUpdate();
//			
//			if(count > 0) {
//				ResultSet rs = pstmt.getGeneratedKeys();
//				if(rs.next()) {
//					newId = rs.getInt(1);
//				}
//			}
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} 
//		
//		return newId;
//	}	
//	
//	public int update(BoardVo vo) {
//		int result = 0;
//		
//		try (
//			Connection conn = DBConnectionUtil.getConnection();
//			PreparedStatement pstmt = conn.prepareStatement("update board set title=?, contents=? where id=?");
//		) {
//			pstmt.setString(1, vo.getTitle());
//			pstmt.setString(2, vo.getContents());
//			pstmt.setLong(3, vo.getId());
//			
//			result = pstmt.executeUpdate();
//		} catch (SQLException e) {
//			System.out.println("Error:" + e);
//		}
//		
//		return result;				
//	}
//	
//	public BoardVo updateOrderNo(BoardVo vo) {
//		try (
//			Connection conn = DBConnectionUtil.getConnection();
//			PreparedStatement pstmt = conn.prepareStatement("update board set o_no = o_no + 1 where g_no = ? and o_no > ?");
//		) {
//			pstmt.setInt(1, vo.getgNo());
//			pstmt.setInt(2, vo.getoNo());
//			
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			System.out.println("Error:" + e);
//		}
//		
//		return vo;	
//	}
//	
//	public int updateHit(Long id) {
//		int result = 0;
//		
//		try (
//			Connection conn = DBConnectionUtil.getConnection();
//			PreparedStatement pstmt = conn.prepareStatement("update board set hit = hit + 1 where id=?");
//		) {
//			pstmt.setLong(1, id);
//			
//			result = pstmt.executeUpdate();
//		} catch (SQLException e) {
//			System.out.println("Error:" + e);
//		}
//		
//		return result;				
//	}
//
//	public int deleteById(Long id) {
//		int count = 0;
//		
//		try (
//			Connection conn = DBConnectionUtil.getConnection();
//			PreparedStatement pstmt = conn.prepareStatement("delete from board where id=?");
//		) {
//			pstmt.setLong(1, id);
//			
//			count = pstmt.executeUpdate();
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} 
//		
//		return count;	
//	}
//
//
//}
