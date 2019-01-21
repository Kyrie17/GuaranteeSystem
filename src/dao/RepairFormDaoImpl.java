package dao;

import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import model.Pager;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.RepairForm;
import tools.Constant;
import tools.DButil;
import tools.DateMinusUtil;

public class RepairFormDaoImpl implements RepairFormDao {
	DButil dbutil = DButil.getInstance();
	
	
	/**
	 * 提交报修表
	 */
	public int submitRepairForm(RepairForm rf) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		int m = 0;
		try {
			conn = dbutil.getConnection();
			//插入报修表的sql语句
			String sql = "insert into repairform(s_id,s_phone,r_sertype,r_seradd,r_serinform,r_sertime,r_detailtime,r_judgestate,r_filepath,a_id) values(?,?,?,?,?,?,?,?,?,?)";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, rf.getUsername());
			ptmt.setString(2, rf.getPhone());
			ptmt.setInt(3, rf.getSerType());
			ptmt.setString(4, rf.getSerAdd());
			ptmt.setString(5, rf.getSerInform());
			ptmt.setString(6, rf.getSerTime());
			ptmt.setString(7, rf.getDetailTime());
			ptmt.setInt(8, rf.getJudgeState());
			ptmt.setString(9, rf.getFile_path());
			ptmt.setString(10, rf.getRepairMan());
			m = ptmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return m ;	//返回执行的条数
	}


	/**
	 * 根据查询条件，查询报修单分页信息
	 * @param searchModel 封装查询条件
	 * @param pageNum 查询第几页数据
	 * @param pageSize 每页显示多少条记录
	 * @return 查询结果
	 */
	public Pager<RepairForm> findRepairForm(RepairForm searchModel, int pageNum,
			int pageSize) {
		List<RepairForm> allStudenList = getAllRepairForm(searchModel);
		
		Pager<RepairForm> pager = new Pager<RepairForm>(pageNum, pageSize,
				allStudenList);
		
		return pager;
	}
	
	
	/**
	 * 根据查询条件查询所有符合条件的报修单（若条件为空，则为显示全部报修单）
	 */
	public List<RepairForm> getAllRepairForm(RepairForm searchModel) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		
		List<RepairForm> result = new ArrayList<RepairForm>();	//查询结果集合
		List<Object> paramList = new ArrayList<Object>();	//sql语句参数集
		
		//获取查询条件
		int serType = searchModel.getSerType();
		int judgeState = searchModel.getJudgeState();
		String username = searchModel.getUsername();
		
		//sql语句
		StringBuilder sql = new StringBuilder(
				"select * from repairform where 1=1");

		//用户名模糊条件查询
		if (username != null && !username.equals("")) {
			sql.append(" and s_id like ?");
			paramList.add("%" + username + "%");
		}
		
		/*
		 * 报修类型查询
		 * serType = 1, 显示报修类型为水的报修单
		 * serType = 2, 显示报修类型为木的报修单
		 * serType = 3, 显示报修类型为电的报修单
		 * serType = 4, 显示报修类型为其他的报修单
		 * serType = 0, 显示所有的报修单
		 */
		if (serType != 0) {	
			sql.append(" and r_sertype = ?");
			paramList.add(serType);
		}

		/*
		 * 审核状态查询
		 * judgeState = -1, 显示报修状态为未受理的报修单
		 * judgeState = 1, 显示报修状态为已受理的报修单
		 * judgeState = 2, 显示报修状态为已完工的报修单
		 * judgeState = 0, 显示所有的报修单
		 */
		if (judgeState != 0) {	
			sql.append(" and r_judgestate = ?");
			paramList.add(judgeState);
		}
		
		try {
			conn = dbutil.getConnection();
			int index = 1;	//指向sql语句中的第一个'？'
			ptmt = conn.prepareStatement(sql.toString());	//获取PreparedStatement
			if (paramList != null && !paramList.isEmpty()) {
				for (int i = 0; i < paramList.size(); i++) {
					ptmt.setObject(index++, paramList.get(i));	//为sql语句中的？参数赋值
				}
			}
			resultSet = ptmt.executeQuery();	//执行更新操作
			//将数据为空的字段置为空字符串
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
				
				RepairForm s = new RepairForm(map);
				result.add(s);
			}
		} catch (SQLException e) {
			throw new RuntimeException("查询所有数据异常！", e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}finally {
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
	 * 获取最近几条报修信息
	 */
	public List<RepairForm> getLastestRF(){
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		
		List<RepairForm> result = new ArrayList<RepairForm>();	//查询结果集合
		
		String sql = "SELECT * FROM repairform order by r_ordernumber DESC limit ?";
		try {
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);	
			ptmt.setInt(1, Constant.DEFAULT_REPAIRFORM_NUM);//获取PreparedStatement
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
				
				RepairForm s = new RepairForm(map);
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
	 * 删除序号为orderNumber的保修记录
	 */
	public int removeRFInfor(String orderNumber) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		int m = 0;
		try {
			conn = dbutil.getConnection();
			//sql语句
			String sql = "delete from repairform where r_ordernumber=?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, orderNumber);
			m = ptmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return m;
	}


	/**
	 * 修改序号为orderNumber的报修记录
	 */
	public int updateRFInfor(RepairForm searchModel) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		int m = 0;

		try {
			conn = dbutil.getConnection();
			//sql语句
			String sql = "update repairform set r_judgestate=?,a_id=? where r_ordernumber=?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, searchModel.getJudgeState());
			ptmt.setString(2, searchModel.getRepairMan());
			ptmt.setInt(3, searchModel.getOrderNumber());
			m = ptmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return m;
	}


	/**
	 * 查询序号为orderNumber报修记录的详情
	 */
	public int getDetailRFInfor(String orderNumber) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		int m = 0;
		
		
		return m;
	}


	/**
	 * 获取单独用户的报修信息
	 */
	public List<RepairForm> getPerInform(String username) {
		
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
//		DButil dbutil = DButil.getInstance();
		List<RepairForm> result = new ArrayList<RepairForm>(); // 查询结果集合

		String sql = "SELECT * FROM repairform where s_id=? order by r_ordernumber DESC";
		try {
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, username);
			resultSet = ptmt.executeQuery(); // 执行更新操作
			ResultSetMetaData metaData = resultSet.getMetaData();
			int cols_len = metaData.getColumnCount();
			if (resultSet.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = metaData.getColumnName(i + 1);
					Object cols_value = resultSet.getObject(cols_name);
					if (cols_value == null) {
						cols_value = "";
					}

					map.put(cols_name, cols_value);

				}
				RepairForm s = new RepairForm(map);
				result.add(s);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}


	/**
	 * 查询报修类型是 @param serType 的所有维修工的名字
	 */
	public List<RepairForm> getRepairMan(int serType) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		
		List<RepairForm> result = new ArrayList<RepairForm>();	//查询结果集合
		
		try {
			String sql = "SELECT DISTINCT a_id FROM repairform WHERE a_id IN (SELECT a_id FROM repairform WHERE r_sertype = ?)";
			
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, serType);
			resultSet = ptmt.executeQuery(); //执行查询操作
			
			
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

				RepairForm s = new RepairForm(1,map);
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
	 * 查询维修工是？的报修表单数量
	 */
	public int getRepairManNum(String repairMan) {
		
		
		int m = 0;
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "SELECT * FROM repairform WHERE a_id=?";
			
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, repairMan);
			resultSet = ptmt.executeQuery(); // 执行更新操作

			resultSet.last();
			m=resultSet.getRow();
			resultSet.beforeFirst();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return m;
	}

	
	/**
	 * 查询没有维修工人没有确定完成的分页报修单
	 * @param searchModel 封装查询条件
	 * @param pageNum 查询第几页数据
	 * @param pageSize 每页显示多少条记录
	 * @return 查询结果
	 */
	public Pager<RepairForm> findUnfinishedRF(String username, int pageNum, int pageSize) {
		List<RepairForm> allStudenList = getUnfinishedRF(username);
		
		Pager<RepairForm> pager = new Pager<RepairForm>(pageNum, pageSize,
				allStudenList);
		
		return pager;
	}

	/**
	 * 查询审核状态为未审核和已审核的报修单
	 */
	public List<RepairForm> getUnfinishedRF(String username) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		
		List<RepairForm> result = new ArrayList<RepairForm>();	//查询结果集合
		
		try {
			String sql = "SELECT * FROM repairform WHERE r_judgestate IN (-1,1) and s_id=?";
			
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, username);
			resultSet = ptmt.executeQuery(); // 执行更新操作
			
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

				RepairForm s = new RepairForm(map);
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
	 * 根据查询条件，查询报修单分页信息
	 * @param searchModel 封装查询条件
	 * @param pageNum 查询第几页数据
	 * @param pageSize 每页显示多少条记录
	 * @return 查询结果
	 */
	public Pager<RepairForm> findFinishedRF(String username, int userConfirm, int pageNum, int pageSize) {
		List<RepairForm> allStudenList = getFinishedRF(username, userConfirm);
		
		Pager<RepairForm> pager = new Pager<RepairForm>(pageNum, pageSize,
				allStudenList);
		
		return pager;
	}
	

	/**
	 * 查询审核状态为已完成的报修单
	 */
	public List<RepairForm> getFinishedRF(String username, int userConfirm) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		
		List<RepairForm> result = new ArrayList<RepairForm>();	//查询结果集合
		
		try {
			String sql = "SELECT * FROM repairform WHERE r_judgestate=2 and r_userconfirm=? and s_id=?";
			
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, userConfirm);
			ptmt.setString(2, username);
			resultSet = ptmt.executeQuery(); // 执行更新操作
			
			
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

				RepairForm s = new RepairForm(map);
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
	 * 将报修单用户端更改为确认完成
	 */
	public int ChangeUserConfirm(int orderNumber) {
		Connection conn = null;
		Statement stmt = null;
		int m = 0;
		try {
			String sql = "update repairform set r_userconfirm=1 where r_ordernumber=" + orderNumber;
			
			conn = dbutil.getConnection();
			stmt =  conn.createStatement();
			m = stmt.executeUpdate(sql);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return m;
	}


	/**
	 * 根据报修工，查询报修单分页信息
	 * @param searchModel 封装查询条件
	 * @param pageNum 查询第几页数据
	 * @param pageSize 每页显示多少条记录
	 * @return 查询结果
	 */
	public Pager<RepairForm> findRFByRepairMan(String username, int pageNum, int pageSize) {
		List<RepairForm> allStudenList = getRFByRepairMan(username);
		
		Pager<RepairForm> pager = new Pager<RepairForm>(pageNum, pageSize,
				allStudenList);
		
		return pager;
	}
	
	
	/**
	 * 查询维修工为username的报修单
	 */
	public List<RepairForm> getRFByRepairMan(String username) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		
		List<RepairForm> result = new ArrayList<RepairForm>();	//查询结果集合
		
		try {
			String sql = "SELECT * FROM repairform WHERE a_id=?";
			
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, username);
			resultSet = ptmt.executeQuery(); // 执行更新操作
			
			
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

				RepairForm s = new RepairForm(map);
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
	
	
	public List<RepairForm> getPerInform(int orderNumber){
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		
		List<RepairForm> result = new ArrayList<RepairForm>(); // 查询结果集合

		String sql = "SELECT * FROM repairform where r_ordernumber=?";
		try {
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, orderNumber);
			resultSet = ptmt.executeQuery(); // 执行更新操作
			ResultSetMetaData metaData = resultSet.getMetaData();
			int cols_len = metaData.getColumnCount();
			if (resultSet.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = metaData.getColumnName(i + 1);
					Object cols_value = resultSet.getObject(cols_name);
					if (cols_value == null) {
						cols_value = "";
					}

					map.put(cols_name, cols_value);

				}
				RepairForm s = new RepairForm(map);
				result.add(s);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	 * 改变报修单的审核状态
	 */
	public int changeJudgeState(int i, int orderNumber) {
		
		Connection conn = null;
		Statement stmt = null;
		int m = 0;
		try {
			String sql = "";
			if(i == -1) {
				sql = "update repairform set r_judgeState=1 where r_ordernumber=" + orderNumber;
			}else {
				sql = "update repairform set r_judgeState=2 where r_ordernumber=" + orderNumber;
			}
			conn = dbutil.getConnection();
			stmt =  conn.createStatement();
			m = stmt.executeUpdate(sql);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return m;
	}


	/**
	 * 获取该学生的报修单数
	 */
	public int getSelfRFNum(String username) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		int num = 0;
		List<RepairForm> result = new ArrayList<RepairForm>(); // 查询结果集合

		try {
			String sql = "SELECT SUM(s_id='?') as num FROM repairform";
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, username);
			resultSet = ptmt.executeQuery(); // 执行更新操作
			
			if(resultSet.next()) {
				num = resultSet.getInt(0);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return num;
	}
}
