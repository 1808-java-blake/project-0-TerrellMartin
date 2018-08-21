package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.revature.beans.User;
import com.revature.util.ConnectionUtil;

public class DatabaseDao implements UserDao{
	private ConnectionUtil cu = ConnectionUtil.cu;
	private Logger log = Logger.getRootLogger();
	
	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void createUser(User u) {
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO pyramid_users (username, password, firstname, lastname) VALUES (?,?,?,?)");
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getFirstName());
			ps.setString(4, u.getLastName());
			int recordsCreated = ps.executeUpdate();
			log.trace(recordsCreated + " records created");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			for(StackTraceElement ste: e.getStackTrace()) {
				log.error(ste);
			}
			log.warn("failed to create new user");
		}
		
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		try (Connection conn = cu.getConnection()) {
			// don't do this
//			Statement s = conn.createStatement();
//			ResultSet rs = s.executeQuery("SELECT * FROM app_users WHERE username='" + 
//							username + "' AND pass='" + password + "'");
			
			// do this
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM app_users WHERE username=? and pass=?");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
					
			if(rs.next()) {
				User u = new User();
				u.setAge(rs.getInt("age"));
				u.setFirstName(rs.getString("firstname"));
				u.setLastName(rs.getString("lastname"));
				u.setUsername(rs.getString("username"));
				return u;
			} else {
				log.warn("failed to find user with provided credentials from the db");
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateUser(User u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(User u) {
		try (Connection conn = cu.getConnection()) {
			// don't do this

			PreparedStatement ps = conn.prepareStatement(
					"DELETE * FROM pyramid_users WHERE username=?" );
			ps.setString(1, u.getUsername());
			ResultSet rs = ps.executeQuery();
					
//			if(rs.next()) {
//				User u = new User();
//				u.setAge(rs.getInt("age"));
//				u.setFirstName(rs.getString("firstname"));
//				u.setLastName(rs.getString("lastname"));
//				u.setUsername(rs.getString("username"));
//
//			} else {
//				log.warn("failed to find user with provided credentials from the db");
//			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
