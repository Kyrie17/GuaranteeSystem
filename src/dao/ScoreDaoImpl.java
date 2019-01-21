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

import model.Pager;
import model.RepairForm;
import model.RepairMan;
import model.Score;
import tools.Constant;
import tools.DButil;

public class ScoreDaoImpl implements ScoreDao {
	DButil dbutil = DButil.getInstance();
	
	/**
	 * 根据查询条件，查询报修单评价分页信息
	 * @param searchModel 封装查询条件
	 * @param pageNum 查询第几页数据
	 * @param pageSize 每页显示多少条记录
	 * @return 查询结果
	 */
	public Pager<Score> findScore(Score searchModel, int pageNum, int pageSize) {
		
		List<Score> allScoreList = getAllScore(searchModel);
		
		Pager<Score> pager = new Pager<Score>(pageNum, pageSize, allScoreList);
		
		return pager;
	}
	
	
	/**
	 * 管理端获取所有报修表单的满意评级
	 */
	public List<Score> getAllScore(Score searchModel) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		
		List<Score> result = new ArrayList<Score>();	//查询结果集合
		List<Object> paramList = new ArrayList<Object>();	//sql语句参数集
		
		//获取查询条件
		String satisfied = searchModel.getSatisfied();
		int classState = searchModel.getScore();
		
		//sql语句
		StringBuilder sql = new StringBuilder("select * from score where 1=1");
		
		/*
		 * 满意度查询
		 * satisfied = 1, 不满意
		 * satisfied = 2, 一般
		 * satisfied = 3, 满意
		 * serType = 0, 显示所有
		 */
		if (!satisfied.equals("全部")) {	
			sql.append(" and satisfied = ?");
			paramList.add(satisfied);
		}
		
		/*
		 * 星级评分
		 * classState = 1, 一星
		 * classState = 2, 二星
		 * classState = 3, 三星
		 * classState = 4, 四星
		 * classState = 5, 五星
		 * classState = 0, 显示所有
		 */
		if (classState != 0) {	
			sql.append(" and score = ?");
			paramList.add(classState);
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
				
				Score s = new Score(map);
				result.add(s);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/*
	 * 判断是否评价过了
	 */
	public int judge(int r_ordernumber) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		DButil dbutil = DButil.getInstance();
		String sql2="SELECT * FROM score where r_ordernumber=?";
		try {
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql2);
			ptmt.setInt(1, r_ordernumber);
			ResultSet rs=ptmt.executeQuery();
			int m=0;
			if(!rs.next()) {
				String sql4="SELECT r_judgestate FROM repairform where r_ordernumber=?";
				conn = dbutil.getConnection();
				ptmt = conn.prepareStatement(sql4);
				ptmt.setInt(1, r_ordernumber);
				rs=ptmt.executeQuery();
				if(rs.next()) {
					if(rs.getInt(1)!=2) {
						return 0;
					}else {
						String sql3="insert into score (r_ordernumber) values( ?)";
						ptmt = conn.prepareStatement(sql3);
						ptmt.setInt(1, r_ordernumber);
						m = ptmt.executeUpdate();
						if(m==1) {
							return 1;
						}else {
							return 0;
						}
					}
					}
				}
				
			return 0;
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	
	/*
	 * 
	 */
	public int insert(String addressID,String suggest,String r_ordernumber,String index,String s_id,String a_id,String now){
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		DButil dbutil = DButil.getInstance();
		int m=0;
		try
		{
		conn = dbutil.getConnection();
		int number=Integer.parseInt(r_ordernumber);
		int score=Integer.parseInt(index);
		String sql1="select * from score where r_ordernumber=?";
		ptmt=conn.prepareStatement(sql1);
		ptmt.setInt(1, number);
		ResultSet rs=ptmt.executeQuery();
		if(!rs.next()) {
		System.out.println("kkk");
		String satisfied="";
		String sql = " insert into score (satisfied , suggest , score , flag , username , repairman , time,r_ordernumber) values(?,?,?,?,?,?,?,?) ";
		ptmt = conn.prepareStatement(sql);
		if(addressID.equals("1")) {
			satisfied="不满意";
		}else if(addressID.equals("2")) {
			satisfied="一般";
		}else if(addressID.equals("3")) {
			satisfied="非常满意";
		}
		ptmt.setInt(8, number);
		ptmt.setString(1, satisfied);
		ptmt.setString(2, suggest);
		ptmt.setInt(3, score);
		ptmt.setString(4, "1");
		ptmt.setString(5, s_id);
		ptmt.setString(6, a_id);
		ptmt.setString(7, now);
		m= ptmt.executeUpdate();
		}else {
			return 0;
		}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("添加满意度失败");
		}finally {
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
	 * 维修工获取自己报修评价(分页)
	 */
	public Pager<Score> findScoreByRm(String username, int pageNum, int pageSize) {
		
		List<Score> allScoreList = getScoreByRm(username);
		
		Pager<Score> pager = new Pager<Score>(pageNum, pageSize, allScoreList);
		
		return pager;
	}


	/**
	 * 维修工获取自己报修评价
	 */
	public List<Score> getScoreByRm(String username) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		
		List<Score> result = new ArrayList<Score>();	//查询结果集合
		
		try {
			String sql = "select * from score where repairman=?";
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);	
			ptmt.setString(1, username);//获取PreparedStatement
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
				
				Score s = new Score(map);
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
	 * 维修工平均的分
	 */
	public double getAvgByRm(String username) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		
		double m = 0;
		try {
			String sql = "SELECT avg(score) FROM score where repairman=? GROUP BY repairman";
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);	
			ptmt.setString(1, username);//获取PreparedStatement
			resultSet = ptmt.executeQuery();	//执行更新操作
			if(resultSet.next()) {
			m = resultSet.getDouble(1);}
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
		
		return m;
	}

}
