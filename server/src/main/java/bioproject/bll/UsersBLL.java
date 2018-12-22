package bioproject.bll;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import bioproject.dal.UsersTableDAL;
import bioproject.types.User;

public class UsersBLL {

	public static User getUser(String id)
	{
		try(UsersTableDAL utd = new UsersTableDAL())
		{
			return utd.getUser(id);
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
	
	public static boolean alreadyExist(String id)
	{
		User u = getUser(id);
		if(u == null || u.getUserName() == null)
			return false;
		return true;
	}
	
	public static boolean insertUser(User u)
	{
		u.setScore(0);
		UUID uuid = UUID.randomUUID();
		u.setID(uuid.toString());
		try(UsersTableDAL utd = new UsersTableDAL())
		{
			return utd.insert(u);
		}
	}
	
	public static boolean updateScore(String id, int newScore) 
	{
		User u = getUser(id);
		try(UsersTableDAL utd = new UsersTableDAL())
		{
			return utd.updateScore(u, newScore);
		}
	}
}
