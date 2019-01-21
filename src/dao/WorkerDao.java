package dao;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

public interface WorkerDao {
	//验证用户名和密码是否正确
	public int WorkerloginJudge(String username,String password)throws SQLException, PropertyVetoException, ClassNotFoundException;
}
