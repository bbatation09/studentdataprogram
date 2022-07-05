package studentProgram;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;

public class DBUtility {

	//멤버함수: 데이터베이스 접속 요청
	//connection: 데이터베이스 연결하는 핸들값
	public static Connection getConnection() {
		
		Connection con = null;	
		
        FileReader fr =null;	
		
		try {
			fr=new FileReader("src\\studentProgram\\db.properties");
			Properties properties =new Properties();
			properties.load(fr);
			
			String DRIVER=properties.getProperty("DRIVER");
			String URL=properties.getProperty("URL");
			String userID=properties.getProperty("userID");
			String userPassword=properties.getProperty("userPassword");
			
		    //드라이버 적재
			Class.forName(DRIVER);
			//데이터베이스 연결
			con= (Connection) DriverManager.getConnection(URL,userID,userPassword);
		} catch (ClassNotFoundException e) {
			System.out.println("FAIL: mysql database connection");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("FAIL: mysql database connection");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("files not found db.properties");
			e.printStackTrace();
		} catch(IOException e) {
			System.out.println("files db.properties connection fail");
			e.printStackTrace();
		}
		
		
		return con;
	}
	
}

