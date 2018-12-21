package bioproject.dal;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractTableDAL implements Closeable{

	private Connection conn;
	
	public AbstractTableDAL()
	{
		try {
		  conn = DriverManager.getConnection(DBConstants.url, DBConstants.username, DBConstants.password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected Connection getConnection()
	{
		return conn;
	}
	
	@Override
	public void close()
	{
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
