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

import model.NoticeInform;
import model.RepairForm;
import tools.Constant;
import tools.DButil;

public class HomePageShowDaoImpl implements HomePageShowDao {
	public List<Object> show() throws ClassNotFoundException, PropertyVetoException, SQLException{
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		DButil dbutil = DButil.getInstance();
		conn=dbutil.getConnection();
		List<Object> a=new ArrayList();
		int[] analyze=new int[7];
		List<NoticeInform> result = new ArrayList<NoticeInform>();	//查询结果集合
		List<RepairForm> result1 = new ArrayList<RepairForm>();	//查询结果集合
		try {
			String sql1="select count(r_sertype) from repairform where r_sertype=1 ";
			String sql2="select count(r_sertype) from repairform where r_sertype=2 ";
			String sql3="select count(r_sertype) from repairform where r_sertype=3 ";
			String sql4="select count(r_sertype) from repairform where r_sertype=4 ";
			String sql5="select count(r_judgestate) from repairform where r_judgestate=-1 ";
			String sql6="select count(r_judgestate) from repairform where r_judgestate=1 ";
			String sql7="select count(r_judgestate) from repairform where r_judgestate=2 ";
			ptmt = conn.prepareStatement(sql1);
			ResultSet rs=ptmt.executeQuery();
			if(rs.next()) {
				analyze[0]=Integer.parseInt(rs.getString(1));
				}
			ptmt = conn.prepareStatement(sql2);
			rs=ptmt.executeQuery();
			if(rs.next()) {
			analyze[1]=Integer.parseInt(rs.getString(1));
			}
			ptmt = conn.prepareStatement(sql3);
			rs=ptmt.executeQuery();
			if(rs.next()) {
				analyze[2]=Integer.parseInt(rs.getString(1));
				}
			ptmt = conn.prepareStatement(sql4);
			rs=ptmt.executeQuery();
			if(rs.next()) {
				analyze[3]=Integer.parseInt(rs.getString(1));
				}
			ptmt = conn.prepareStatement(sql5);
			rs=ptmt.executeQuery();
			if(rs.next()) {
				analyze[4]=Integer.parseInt(rs.getString(1));
				}
			ptmt = conn.prepareStatement(sql6);
			rs=ptmt.executeQuery();
			if(rs.next()) {
				analyze[5]=Integer.parseInt(rs.getString(1));
				}
			ptmt = conn.prepareStatement(sql7);
			rs=ptmt.executeQuery();
			if(rs.next()) {
				analyze[6]=Integer.parseInt(rs.getString(1));
				}
			
			
		String sql8 = "SELECT * FROM noticeinform order by upLoadDate DESC limit ?";
			ptmt = conn.prepareStatement(sql8);	
			ptmt.setInt(1, Constant.DEFAULT_NoticeInform_NUM);//获取PreparedStatement
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
				
				NoticeInform s = new NoticeInform(map);
				result.add(s);
			}
		
		
		
			
			
			String sql9 = "SELECT * FROM repairform order by r_ordernumber DESC limit ?";
			ptmt = conn.prepareStatement(sql9);	
			ptmt.setInt(1, Constant.DEFAULT_REPAIRFORM_NUM);//获取PreparedStatement
			resultSet = ptmt.executeQuery();	//执行更新操作
			ResultSetMetaData metaData2 = resultSet.getMetaData();
			int cols_len2 = metaData2.getColumnCount();
			while (resultSet.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < cols_len2; i++) {
					String cols_name = metaData2.getColumnName(i + 1);
					Object cols_value = resultSet.getObject(cols_name);
					if (cols_value == null) {
						cols_value = "";
					}
					map.put(cols_name, cols_value);
				}
				
				RepairForm s = new RepairForm(map);
				result1.add(s);
			}
		
		
		
		
		
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("数据分析错误");
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		a.add(0, analyze);
		a.add(1, result);
		a.add(2, result1);
		return a;
	}
}
