package com.meitu.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.meitu.bean.User;
import com.meitu.dao.UserDao;
import com.meitu.db.DBConnection;
import com.meitu.enums.ErrorEnum;

@Repository
public class UserDaoImpl implements UserDao {

	public boolean add(User user) {
		Connection conn = DBConnection.getConnection(); // 获得连接对象
		String addSQL = "insert into user(user_name,user_phone,user_gender,user_avatar,user_password,user_birthday) values(?,?,?,?,?,?)";
		PreparedStatement pstmt = null; // 声明预处理对象
		try {
			pstmt = conn.prepareStatement(addSQL); // 获得预处理对象并赋值
			pstmt.setString(1, user.getUser_name());
			pstmt.setString(2, user.getUser_phone());// 设置第二个参数
			pstmt.setString(3, user.getUser_gender());
			pstmt.setString(4, user.getUser_avatar());
			pstmt.setString(5, user.getUser_password());
			pstmt.setString(6, user.getUser_birthday());
			int count = pstmt.executeUpdate();
			System.out.println(count);
			return count > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt); // 关闭预处理对象
		}
		return false;
	}

	public boolean verifyCellphone(String cellPhone) {
		Connection conn = DBConnection.getConnection(); // 获得连接对象
		String sql = "select user_id from user where user_phone = ? ";
		PreparedStatement pstmt = null; // 声明预处理对象
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql); // 获得预处理对象并赋值
			pstmt.setString(1, cellPhone);
			rs = pstmt.executeQuery(); // 执行查询
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs); // 关闭结果集对象
			DBConnection.close(pstmt); // 关闭预处理对象
		}
		return false;
	}

	public Object[] login(String phone, String password) {
		System.out.println("login:" + phone + "    " + password);
		Connection conn = DBConnection.getConnection(); // 获得连接对象
		String sql = "select * from user where user_phone = ? and user_password = ?";
		PreparedStatement pstmt = null; // 声明预处理对象
		ResultSet rs = null;
		User user = new User();
		try {
			pstmt = conn.prepareStatement(sql); // 获得预处理对象并赋值
			pstmt.setString(1, phone);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery(); // 执行查询
			while (rs.next()) {
				user.setUser_avatar(rs.getString("user_avatar"));
				user.setUser_birthday(rs.getString("user_birthday"));
				user.setUser_gender(rs.getString("user_gender"));
				user.setUser_id(rs.getInt("user_id"));
				user.setUser_name(rs.getString("user_name"));
				return new Object[] { ErrorEnum.NONE, user };
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs); // 关闭结果集对象
			DBConnection.close(pstmt); // 关闭预处理对象
		}
		return new Object[] { ErrorEnum.WRONG_PASSWORD, null };
	}

	public User getUserInfo(int user_id) {
		Connection conn = DBConnection.getConnection(); // 获得连接对象
		String sql = "select * from user where user_id = ? ";
		PreparedStatement pstmt = null; // 声明预处理对象
		ResultSet rs = null;
		User user = new User();
		try {
			pstmt = conn.prepareStatement(sql); // 获得预处理对象并赋值
			pstmt.setInt(1, user_id);
			rs = pstmt.executeQuery(); // 执行查询
			while (rs.next()) {
				user.setUser_avatar(rs.getString("user_avatar"));
				user.setUser_birthday(rs.getString("user_birthday"));
				user.setUser_gender(rs.getString("user_gender"));
				user.setUser_id(rs.getInt("user_id"));
				user.setUser_name(rs.getString("user_name"));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs); // 关闭结果集对象
			DBConnection.close(pstmt); // 关闭预处理对象
		}
		return null;
	}

}
