package dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Commidity;
import model.NoticeInform;
import model.PersonCommidity;
import tools.DButil;

public class CommidityDaoImpl implements CommidityDao {
	DButil dbutil = DButil.getInstance();
	@Override
	public List<Commidity> findAllCommidity() {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		List<Commidity> result = new ArrayList<Commidity>();// 查询结果集合

		try {
			String sql = " select * from commidity ";
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);
			resultSet = ptmt.executeQuery(); // 执行查询操作
			ResultSetMetaData metaData = resultSet.getMetaData();
			int cols_len = metaData.getColumnCount();
			int n=0;
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
				n++;
				Commidity s = new Commidity(map);
				result.add(s);
				
			}
		} catch (SQLException e) {
			throw new RuntimeException("查询所有数据异常！", e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
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
	
	public int judgeIfExist(String stu_id,String commidity_id) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;

		try {
			String sql = " select quantity from studentpurchase where (stu_id=? and commidity_id=?) ";
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1,stu_id);
			ptmt.setString(2, commidity_id);
			resultSet = ptmt.executeQuery(); // 执行查询操作
			if(resultSet.next()) {
				return resultSet.getInt(1);
			}else {
				return 0;
			}
				
		} catch (SQLException e) {
			throw new RuntimeException("查询所有数据异常！", e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
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
		return 0;
	}
	
	
	public int insertCart(String stu_id,String id,int quantity,String name,double price) {
		Connection conn = null;
		PreparedStatement ptmt = null;

		try {
			String sql = " insert into studentpurchase (stu_id,commidity_id,quantity,commidity_name,price) values(?,?,?,?,?) ";
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1,stu_id);
			ptmt.setString(2, id);
			ptmt.setInt(3, quantity+1);
			ptmt.setString(4, name);
			ptmt.setDouble(5, price);
			int n=ptmt.executeUpdate();
			return n;
				
		} catch (SQLException e) {
			throw new RuntimeException("查询所有数据异常！", e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	
	public int addCart(String stu_id,int quantity,String commidity_id) {
		Connection conn = null;
		PreparedStatement ptmt = null;

		try {
			String sql = " update studentpurchase set quantity=? where (stu_id=? and commidity_id=?) ";
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1,quantity+1);
			ptmt.setString(2, stu_id);
			ptmt.setString(3, commidity_id);
			int n=ptmt.executeUpdate();
			return n;
				
		} catch (SQLException e) {
			throw new RuntimeException("查询所有数据异常！", e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public List<PersonCommidity> getAllPersonCommidity(String stu_id) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		List<PersonCommidity> result = new ArrayList<PersonCommidity>();// 查询结果集合

		try {
			String sql = " select commidity_name,quantity,price from studentpurchase where stu_id=?  ";
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, stu_id);
			resultSet = ptmt.executeQuery(); // 执行查询操作
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
				PersonCommidity s = new PersonCommidity(map);
				result.add(s);
				
			}
		} catch (SQLException e) {
			throw new RuntimeException("查询所有数据异常！", e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
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

	@Override
	public int deleteCommidity(String stu_id, String commidity_name) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		try {
		conn = dbutil.getConnection();
		String sql1=" select quantity from studentpurchase where (stu_id=? and commidity_name=?) ";
		ptmt=conn.prepareStatement(sql1);
		ptmt.setString(1,stu_id);
		ptmt.setString(2, commidity_name);
		ResultSet rs=ptmt.executeQuery();
		if(rs.next()) {
			if(rs.getInt(1)==1) {
				String sql2 = " delete from studentpurchase where (stu_id=? and commidity_name=?) ";
				ptmt = conn.prepareStatement(sql2);
				ptmt.setString(1,stu_id);
				ptmt.setString(2, commidity_name);
				int n=ptmt.executeUpdate();
				return n;
			}
		}
			String sql = " update studentpurchase set quantity=quantity-1 where (stu_id=? and commidity_name=?) ";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1,stu_id);
			ptmt.setString(2, commidity_name);
			int n=ptmt.executeUpdate();
			return n;
				
		} catch (SQLException e) {
			throw new RuntimeException("删除数据异常！", e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public int deleteChart(String stu_id) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		try {
			String sql = " delete from studentpurchase where stu_id=? ";
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1,stu_id);
			int n=ptmt.executeUpdate();
			return n;
				
		} catch (SQLException e) {
			throw new RuntimeException("删除数据异常！", e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}
