package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	
	public static boolean logIn(String JDBCDriver, String username, String password) {
		try {
			Class.forName(JDBCDriver);
			
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rs = null;
			
			conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/F2GCT4gVsA?user=F2GCT4gVsA&password=fYBU5EacV2");
			ps = conn.prepareStatement("SELECT* FROM Users WHERE Username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			
			if (rs.next() == false) { // username does not exist in data 
				System.out.println("username does not exist in data");
				return false;
			}
			
			else if (password.equals(rs.getString("Password"))) { // given password matches password in database 
				System.out.println("password was correct");
				return true;
			}
			
			else { // password doesn't match
				System.out.println("password was incorrect");
				return false;	
			}
		} 
		catch (ClassNotFoundException ce) {
			System.out.println ("ClassNotFoundException: " + ce.getMessage());
			return false;
		} 
		catch(SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
			return false;
		}
	}
	
	public static boolean signUp(String JDBCDriver, String username, String password) {
		try {
			Class.forName(JDBCDriver);
			
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rs = null;
			
			conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/F2GCT4gVsA?user=F2GCT4gVsA&password=fYBU5EacV2");
			ps = conn.prepareStatement("SELECT* FROM Users WHERE Username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			
			if (rs.next() == true) { //username is already being used by another user
				System.out.println("username already exists");
				return false;
			}
			
			else { // adds user information to the database 
				ps.close();
				rs.close();
				
				ps = conn.prepareStatement("INSERT INTO Users(Username, Password, HighscoreEasy, HighscoreMedium, HighscoreHard) "
						+ "VALUES(?,?,0,0,0)");
				ps.setString(1, username);
				ps.setString(2, password);
				ps.executeUpdate();
				
				System.out.println("username added");
				return true;
			}
		}
		catch (ClassNotFoundException ce) {
			System.out.println ("ClassNotFoundException: " + ce.getMessage());
			return false;
		} 
		catch(SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
			return false;
		}
	}
	
	public static int getHighscore(String JDBCDriver, String username, String difficulty) {
		try {
			Class.forName(JDBCDriver);
		
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rs = null;
		
			conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/F2GCT4gVsA?user=F2GCT4gVsA&password=fYBU5EacV2");
			
			if (difficulty.equalsIgnoreCase("easy")) {
				ps = conn.prepareStatement("SELECT HighscoreEasy FROM Users WHERE Username=?");
			}
			
			else if (difficulty.equalsIgnoreCase("medium")) {
				ps = conn.prepareStatement("SELECT HighscoreMedium FROM Users WHERE Username=?");
			}
			
			else if(difficulty.equalsIgnoreCase("hard")) {
				ps = conn.prepareStatement("SELECT HighscoreHard FROM Users WHERE Username=?");
			}
			
			else {
				return -1;
			}
			ps.setString(1, username);
			rs = ps.executeQuery();
			
			rs.next();
			return rs.getInt(1);
		}
		catch (ClassNotFoundException ce) {
			System.out.println ("ClassNotFoundException: " + ce.getMessage());
			return -1;
		} 
		catch(SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
			return -1;
		}
	}
	
	public static void setHighscore(String JDBCDriver, String username, String difficulty, int score) {
		try {
			Class.forName(JDBCDriver);
		
			Connection conn = null;
			PreparedStatement ps =null;
			
			boolean rightDiff = false;
			
			conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/F2GCT4gVsA?user=F2GCT4gVsA&password=fYBU5EacV2");
			
			if (difficulty.equalsIgnoreCase("easy")) {
				ps = conn.prepareStatement("UPDATE Users SET HighscoreEasy =? WHERE Username=?");
				rightDiff = true;
			}
			
			else if (difficulty.equalsIgnoreCase("medium")) {
				ps = conn.prepareStatement("UPDATE Users SET HighscoreMedium =? WHERE Username=?");
				rightDiff = true;
			}
			
			else if(difficulty.equalsIgnoreCase("hard")) {
				ps = conn.prepareStatement("UPDATE Users SET HighscoreHard =? WHERE Username=?");
				rightDiff = true;
			}
			
			if (rightDiff) {
				ps.setInt(1, score);
				ps.setString(2, username);
				ps.executeUpdate();
			}
		}
		catch (ClassNotFoundException ce) {
			System.out.println ("ClassNotFoundException: " + ce.getMessage());
		} 
		catch(SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		}
	}
}