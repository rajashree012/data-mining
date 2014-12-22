import java.sql.*;
import java.util.*;
public class database
{	
	List<points> dots=new ArrayList<points>();
	void connect()
	{
  		System.out.println("MySQL Connect");
  		Connection conn = null;
  		String url = "jdbc:mysql://localhost:3306/";
  		String dbName = "datamining";
  		String driver = "com.mysql.jdbc.Driver";
  		String userName = "root"; 
  		String password = "";
  		try 
		{
  			Class.forName(driver).newInstance();
  			conn = DriverManager.getConnection(url+dbName,userName,password);
			ResultSet rst1=null;
			Statement stmt1=null;
			stmt1=conn.createStatement();
			String check="select * from coordinates";
			rst1=stmt1.executeQuery(check);
			while(rst1.next())
			{
				points p1=new points();
				p1.slno=Integer.parseInt(rst1.getString("point"));
				p1.x_coord=Double.parseDouble(rst1.getString("x"));
				p1.y_coord=Double.parseDouble(rst1.getString("y"));
				p1.clusterno=0;
				dots.add(p1);
			}
			System.out.println(dots.size());
			rst1.close();
			stmt1.close();
  			conn.close();
  			System.out.println("Disconnected from database");
  		} catch (Exception e) 
		{
  			e.printStackTrace();
  		}
  	}
}