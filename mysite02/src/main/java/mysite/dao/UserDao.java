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
			PreparedStatement pstmt = conn.prepareStatement("select id, name from user where email=? and password=?");
		) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				
				userVo = new UserVo();
				userVo.setId(id);
				userVo.setName(name);
			}
			
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return userVo;
	}	
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {		
			// 1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			// 2. 연결하기
			String url = "jdbc:mariadb://192.168.0.18:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
		
		return conn;		
	}	
}
