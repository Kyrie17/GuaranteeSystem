package dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import model.RepairForm;
import model.RepairMan;
import tools.Constant;
import tools.DButil;

public class RepairManDaoImpl implements RepairManDao {
	DButil dbutil = DButil.getInstance();
	
	
	/**
	 * 获取某一维修类别的所有维修工
	 */
	public List<RepairMan> getRepairMan(int major) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		
		List<RepairMan> result = new ArrayList<RepairMan>();	//查询结果集合
		
		try {
			String sql = "select * from admin where a_major=?";
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, major);//获取PreparedStatement
			resultSet = ptmt.executeQuery();	//执行更新操作
			ResultSetMetaData metaData = resultSet.getMetaData();
			int cols_len = metaData.getColumnCount();
			while (resultSet.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = metaData.getColumnName(i + 1);
					Object cols_value = resultSet.getObject(cols_name);
					if (cols_value == null) {
						cols_value = "";
					}
					map.put(cols_name, cols_value);
				}
				
				RepairMan s = new RepairMan(map);
				result.add(s);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 获取所有维修人员
	 */
	public List<RepairMan> getAllRepairMan() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		
		List<RepairMan> result = new ArrayList<RepairMan>();	//查询结果集合
		
		try {
			String sql = "select * from admin";
			conn = dbutil.getConnection();
			stmt =  conn.createStatement();
			resultSet = stmt.executeQuery(sql);
			ResultSetMetaData metaData = resultSet.getMetaData();
			int cols_len = metaData.getColumnCount();
			while (resultSet.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = metaData.getColumnName(i + 1);
					Object cols_value = resultSet.getObject(cols_name);
					if (cols_value == null) {
						cols_value = "";
					}
					map.put(cols_name, cols_value);
				}
				
				RepairMan s = new RepairMan(map);
				result.add(s);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
