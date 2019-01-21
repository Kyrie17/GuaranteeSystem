package dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import model.NoticeInform;
import model.Pager;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import tools.Constant;
import tools.DButil;

public class NoticeInformDaoImpl implements NoticeInformDao {
	DButil dbutil = DButil.getInstance();
	
	/**
	 * 上传所有公告
	 */
	public int upLoadNoticeInform(NoticeInform model) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		int row = 0;
		
		try {
			conn = dbutil.getConnection();
			String sql = "INSERT INTO noticeinform(UpLoader,FilePath,FileName,DownLoadNum,UpLoadDate)VALUES(?,?,?,?,?)";  
			ptmt = conn.prepareStatement(sql);	//获取PreparedStatement
			
			ptmt.setString(1, model.getUpLoader());
			ptmt.setString(2, model.getFilePath());
			ptmt.setString(3, model.getFileName());
			ptmt.setInt(4, model.getDownLoadNum());
			ptmt.setString(5, model.getUpLoadDate());

			row = ptmt.executeUpdate();
;
		} catch (SQLException e) {
			e.printStackTrace();
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
		return row;
	}
	
	
	/**
	 * 查询所有公告
	 */
	@Override
	public List<NoticeInform> getAllNoticeInform() {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		List<NoticeInform> result = new ArrayList<NoticeInform>();// 查询结果集合

		try {
			String sql = " select * from noticeinform ";
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
				NoticeInform s = new NoticeInform(map);
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

	
	/**
	 * 查询报修单分页信息
	 * 
	 * @param pageNum  查询第几页数据
	 * @param pageSize 每页显示多少条记录
	 * @return 查询结果
	 */
	public Pager<NoticeInform> findNoticeForm(int pageNum, int pageSize) {
		List<NoticeInform> allNoticeInform = getAllNoticeInform();

		Pager<NoticeInform> pager = new Pager<NoticeInform>(pageNum, pageSize, allNoticeInform);

		return pager;
	}


	@Override
	public String findPath(String fileName) {
		String filePath="";
		Connection conn = null;
		PreparedStatement ptmt = null;
		try {
		String sql = " select * from noticeinform where fileName= ? ";
		conn = dbutil.getConnection();
		ptmt = conn.prepareStatement(sql);
		ptmt.setString(1,fileName);
		ResultSet result=ptmt.executeQuery();
		if(result.next()) {
			filePath=result.getString(2);
			int downLoadNum=result.getInt(4);
			String sql2=" UPDATE noticeinform SET downLoadNum=? where filePath = ? ";
			ptmt=conn.prepareStatement(sql2);
			ptmt.setInt(1, downLoadNum+1);
			ptmt.setString(2, filePath);
			ptmt.execute();
		}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("找不到路径");
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return filePath;
	}


	//获得最近的报修表单
	public List<NoticeInform> getLastestNI() {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		List<NoticeInform> result = new ArrayList<NoticeInform>();	//查询结果集合
		
		String sql = "SELECT * FROM noticeinform order by upLoadDate DESC limit ?";
		try {
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);	
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
		} catch (ClassNotFoundException e) {
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
		return result;
	}

}

