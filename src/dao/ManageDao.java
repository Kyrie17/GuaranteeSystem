package dao;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

public interface ManageDao {
	//验证用户名和密码是否正确
	public int ManageloginJudge(String username,String password)throws SQLException, PropertyVetoException, ClassNotFoundException;
}
