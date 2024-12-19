package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mysite.vo.UserVo;

public class UserDao {

	public int insert(UserVo vo) {
		int count = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into user(name, email, password, gender, join_date) values(?, ?, ?, ?, now())");
		) {
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;		
	}
	
	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo userVo = null;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select id, name, gender from user where email=? and password=?");
		) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String gender = rs.getString(3);
				
				userVo = new UserVo();
				userVo.setId(id);
				userVo.setName(name);
				userVo.setGender(gender);
				userVo.setEmail(email);
				userVo.setPassword(password);
			}
			
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return userVo;
	}	

	public int updateByEmail(String email, String name, String password, String gender) {
		int count = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("update user set name=?, gender=?, password=? where email=?");
			PreparedStatement pstmt2 = conn.prepareStatement("update user set name=?, gender=? where email=?");
		) {
			if(!password.trim().equals("")) {
				pstmt1.setString(1, name);
				pstmt1.setString(2, gender);				
				pstmt1.setString(3, password);				
				pstmt1.setString(4, email);			
				
				count = pstmt1.executeUpdate();
			} else {
				pstmt2.setString(1, name);
				pstmt2.setString(2, gender);				
				pstmt2.setString(3, email);			
				
				count = pstmt2.executeUpdate();				
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;
	}	
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {		
			// 1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			// 2. 연결하기
			String url = "jdbc:mariadb://192.168.200.132:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
		
		return conn;		
	}

}
