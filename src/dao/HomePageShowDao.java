package dao;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.List;

public interface HomePageShowDao {
	
	//用于显示页面信息
	public List<Object> show() throws ClassNotFoundException, PropertyVetoException, SQLException;
}
