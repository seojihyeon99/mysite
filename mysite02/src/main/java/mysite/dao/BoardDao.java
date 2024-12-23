package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mysite.vo.BoardVo;

public class BoardDao {
	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select id, title, hit, (select name from user where id = user_id) 'name', date_format(reg_date, '%Y-%m-%d %H:%i:%s'), g_no, o_no, depth, user_id "
															+ "from board "
															+ "order by g_no desc, o_no asc");
			ResultSet rs = pstmt.executeQuery();
		) {
			while(rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				int hit = rs.getInt(3);
				String name = rs.getString(4);
				String regDate = rs.getString(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				Long userId = rs.getLong(9);
				
				BoardVo vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setHit(hit);
				vo.setName(name);
				vo.setRegDate(regDate);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserId(userId);
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return result;
	}

	public BoardVo findParentById(Long id) {
		BoardVo boardVo = null;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select g_no, o_no, depth from board where id = ?");
		) {
			pstmt.setLong(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				int gNo = rs.getInt(1);
				int oNo = rs.getInt(2);
				int depth = rs.getInt(3);
				
				boardVo = new BoardVo();
				boardVo.setgNo(gNo);
				boardVo.setoNo(oNo);
				boardVo.setDepth(depth);
			}
			
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return boardVo;
	}	
	
	public BoardVo findById(Long id) {
		BoardVo boardVo = null;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select title, contents, g_no, o_no, depth, user_id from board where id = ?");
		) {
			pstmt.setLong(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				String title = rs.getString(1);
				String contents = rs.getString(2);
				int gNo = rs.getInt(3);
				int oNo = rs.getInt(4);
				int depth = rs.getInt(5);
				Long userId = rs.getLong(6);
				
				boardVo = new BoardVo();
				boardVo.setId(id);
				boardVo.setTitle(title);
				boardVo.setContents(contents);
				boardVo.setgNo(gNo);
				boardVo.setoNo(oNo);
				boardVo.setDepth(depth);
				boardVo.setUserId(userId);
			}
			
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return boardVo;
	}	
	
	public int insert(BoardVo vo) {
		int count = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into board (title, contents, hit, reg_date, g_no, o_no, depth, user_id) "
					+ "select ?, ?, 0, now(), ifnull(max(g_no), 0) + 1, 1, 0, ? from board");
		) {
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUserId());
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;			
	}
	
	public int insertReply(BoardVo vo) {
		int count = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into board (title, contents, hit, reg_date, g_no, o_no, depth, user_id) "
															+ "values (?, ?, 0, now(), ?, ?, ?, ?)");
		) {
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getgNo()); 		// 부모글의 group_no
			pstmt.setInt(4, vo.getoNo() + 1); 	// 부모글의 order_No + 1
			pstmt.setInt(5, vo.getDepth() + 1); // 부모글의 depth + 1
			pstmt.setLong(6, vo.getUserId());
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;			
	}	
	
	public int update(BoardVo vo) {
		int result = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update board set title=?, contents=? where id=?");
		) {
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getId());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}
		
		return result;				
	}
	
	public int deleteById(Long id) {
		int count = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from board where id=?");
		) {
			pstmt.setLong(1, id);
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;	
	}
	
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		
			String url = "jdbc:mariadb://192.168.0.18:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 
		
		return conn;
	}

}
