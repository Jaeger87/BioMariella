package bioproject.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bioproject.types.User;


public class UsersTableDAL extends AbstractTableDAL{

	private static final String querySelect = 
			"SELECT * FROM users where username =?";
	private static final String queryInsert = 
			"INSERT INTO users (username, score) VALUES (?, ?);";
	private static final String queryUpdateScore =
			"UPDATE users SET score = ? WHERE users.username = ?";
	private static final String queryGetScore = 
			"SELECT score FROM users WHERE username = ?";
	private static final String queryGetAllUsers =
			"SELECT * FROM users";
	
	public User getUser(String username)
	{
		try {
			User u = new User();
			PreparedStatement stmt =
			        getConnection().prepareStatement(querySelect);

		    stmt.setString(1, username);
		    ResultSet rs = stmt.executeQuery();
		    while (rs.next())
		    {
		       u = new User();
		       u.setUsername(rs.getString(1));
		       u.setScore(rs.getInt(2));
		    }
		return u;
	}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<User> getAllUsers()
	{
		List<User> users = new ArrayList<>();
		try {
			PreparedStatement stmt =
			        getConnection().prepareStatement(queryGetAllUsers);
		    ResultSet rs = stmt.executeQuery();
		    while (rs.next())
		    {
		       User u = new User();
		       u.setUsername(rs.getString(1));
		       u.setScore(Integer.parseInt(rs.getString(2)));
		       users.add(u);
		    }
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		return users;
	}
	
	public boolean insert(User u)
	{
		try {
			PreparedStatement stmt =
			        getConnection().prepareStatement(queryInsert);

		    stmt.setString(1, u.getUserName());
		    stmt.setInt(2, 0);
		    stmt.executeUpdate();
		    return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateScore(String username, int newScore) {
		try {
			PreparedStatement stmt = 
					this.getConnection().prepareStatement(queryGetScore);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			int score = 0;
			while (rs.next())
		    {
				score = rs.getInt(1);
		    }
			if(score >= newScore) return true;
			PreparedStatement stmt2 = 
					this.getConnection().prepareStatement(queryUpdateScore);
			stmt2.setInt(1, newScore);
			stmt2.setString(2, username);
			stmt2.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
}