package bioproject.bll;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import bioproject.dal.UsersTableDAL;
import bioproject.types.User;

public class UsersBLL {

	public static User getUser(String username)
	{
		try(UsersTableDAL utd = new UsersTableDAL())
		{
			return utd.getUser(username);
		}
	}
	
	public static List<User> getRanking(){
		try(UsersTableDAL utd = new UsersTableDAL())
		{
			List<User> users = utd.getAllUsers();
			Collections.sort(users);
			return users;
		}
	}
	
	public static boolean alreadyExist(String username)
	{
		User u = getUser(username);
		if(u == null || u.getUserName() == null)
			return false;
		return true;
	}
	
	public static boolean insertUser(String username)
	{
		User u = new User();
		u.setUsername(username);
		u.setScore(0);
		try(UsersTableDAL utd = new UsersTableDAL())
		{
			return utd.insert(u);
		}
	}
	
	public static boolean updateScore(String username, int newScore) 
	{
		if(!alreadyExist(username)) return false;
		try(UsersTableDAL utd = new UsersTableDAL())
		{
			return utd.updateScore(username, newScore);
		}
	}
}
