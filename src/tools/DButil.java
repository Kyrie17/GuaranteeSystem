package tools;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//c3p0数据库连接池的类
public class DButil {
	private static ComboPooledDataSource cpds = new ComboPooledDataSource();
	private static DButil dbutil = null;

	//私有的构造方法
	private DButil() {

	}

	//实例化DButil
	public static DButil getInstance() {
		if (dbutil == null) {
			dbutil = new DButil();
		}
		return dbutil;
	}

	//获取数据库
	public Connection getConnection() throws PropertyVetoException, SQLException, ClassNotFoundException {
		cpds.setDriverClass("com.mysql.jdbc.Driver");
		cpds.setJdbcUrl(
				"jdbc:mysql://127.0.0.1:3306/guaranteesystem?useSSL=false&useUnicode=true&characterEncoding=gb2312");
		cpds.setPassword("89894148");
		cpds.setUser("root");
		return cpds.getConnection();
	}

}
