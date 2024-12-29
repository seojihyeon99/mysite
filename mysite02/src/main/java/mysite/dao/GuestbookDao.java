package mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mysite.util.DBConnectionUtil;
import mysite.vo.GuestbookVo;

public class GuestbookDao {

	public List<GuestbookVo> findAll() {
		List<GuestbookVo> result = new ArrayList<>();
		
		try (
			Connection conn = DBConnectionUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select id, name, contents, date_format(reg_date, '%Y-%m-%d %h:%i:%s') from guestbook order by reg_date desc");	
			ResultSet rs = pstmt.executeQuery();
		) {
			while(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String contents = rs.getString(3);
				String regDate = rs.getString(4);
				
				GuestbookVo vo = new GuestbookVo();
				vo.setId(id);
				vo.setName(name);
				vo.setContents(contents);
				vo.setRegDate(regDate);
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return result;
	}

	public int insert(GuestbookVo vo) {
		int count = 0;
		
		try (
			Connection conn = DBConnectionUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into guestbook values(null, ?, ?, ?, now())");
		) {
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContents());
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;		
	}

	public int deleteByIdAndPassword(Long id, String password) {
		int count = 0;
		
		try (
			Connection conn = DBConnectionUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from guestbook where id=? and password=?");
		) {
			pstmt.setLong(1, id);
			pstmt.setString(2, password);
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;		
	}
	
}
