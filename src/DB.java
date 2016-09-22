import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;

public class DB 
{
	public static final String database="sql7136706";
	
	Connection connection;
	ResultSet  result_set;
	Statement  statement;
	
	public static String get_safe_string(String s)
	{
		String safe_string="";
		
		for(int i=0; i<s.length(); i++)
		{
			if(s.charAt(i)=='\'')
				safe_string=safe_string+"''";
			else
				safe_string=safe_string+s.charAt(i);
		}
		
		return safe_string;
	}
	
	public static void install_driver()
	{
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	DB()
	{
		try {
			//connection=(Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/Enrollment","root", "fuckoff");
			connection=(Connection)DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com:3306/sql7136706","sql7136706", "XNhkQDhPkQ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet query(String query)
	{
		if(result_set!=null)
			try {
				result_set.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		
		try {
            statement=connection.createStatement();
            result_set=statement.executeQuery(query);
            return result_set;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		
		return result_set;
	}
	
	public void update(String query)
	{
		try {
            statement=connection.createStatement();
            statement.executeUpdate(query);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	public void close()
	{
		if(connection!=null)
		{
			try {
				//connection.commit();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
