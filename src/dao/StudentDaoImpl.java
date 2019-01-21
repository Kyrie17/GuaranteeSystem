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
import model.Student;
import tools.DButil;

public class StudentDaoImpl implements StudentDao {
	// 验证用户名和密码是否正确
	@Override
	public int loginJudge(String stu_id, String password)
			throws SQLException, PropertyVetoException, ClassNotFoundException {
		Connection conn = null;
		PreparedStatement ptmt = null;
		int n = 0;
		try {
			conn = DButil.getInstance().getConnection();
			ResultSet rs;
			String sql = " select stu_id from student where stu_id = ? and password =? ";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, stu_id);
			ptmt.setString(2, password);
			rs = ptmt.executeQuery();
			if (rs.next()) {
				n = 1;
			} else {
				n = 0;
			}
		} catch (Exception e) {
			System.out.println("登录失败");
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return n;
	}

	// 添加注册信息
	@Override
	public int addStudent(String stu_id, String phone,  String password, Boolean boo,String timestamp)
			throws SQLException, PropertyVetoException, ClassNotFoundException {
		Connection conn = null;
		PreparedStatement ptmt = null;
		int m = 0;
		try {
			conn = DButil.getInstance().getConnection();
			String sql = " insert into student (stu_id,phone,password, register_data) values( ?,?,?,? ) ";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, stu_id);
			ptmt.setString(2, phone);
			ptmt.setString(3, password);
			ptmt.setString(4, timestamp);
			if (boo) {
				m = ptmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("添加注册信息失败");
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

	// 核验学号是否已被注册
	@Override
	public int judgeStudent(String stu_id) throws SQLException, PropertyVetoException, ClassNotFoundException {
		int n = 0;
		Connection conn = null;
		PreparedStatement ptmt = null;
		try {
			conn = DButil.getInstance().getConnection();
			String sql = " select * from student where stu_id = ? ";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, stu_id);
			ResultSet rs = ptmt.executeQuery();
			if (rs.next()) {
				n = 0;
			} else {
				n = 1;
			}
		} catch (Exception e) {
			System.out.println("学号已经被注册");
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return n;
	}

	@Override
	public int improveInform(String stu_id, String room, String sex, String area, String build) {
		System.out.println( stu_id+  room+ sex+ area+ build);
		int n = 0;
		Connection conn = null;
		PreparedStatement ptmt = null;
		try {
			conn = DButil.getInstance().getConnection();
			String sql = " update student set room=?,sex=?,area=?,build=? where stu_id=? ";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, room);
			// 男的值为1，女的值为0
			ptmt.setString(2, sex);
			if (area.equals("1")) {
				ptmt.setString(3, "北苑");
			} else {
				ptmt.setString(3, "南苑");
			}
			ptmt.setString(4, build);
			ptmt.setString(5, stu_id);
			n = ptmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("添加信息错误");
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return n;
	}

	// 检查旧密码是否输入正确
	@Override
	public int judgePassword(String old_password, String stu_id) {
		int n = 0;
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs;
		try {
			conn = DButil.getInstance().getConnection();
			String sql = "select * from student where (password=? and stu_id=?)";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, old_password);
			// 男的值为1，女的值为0
			ptmt.setString(2, stu_id);
			rs = ptmt.executeQuery();
			if (rs.next()) {
				return 1;
			}
		} catch (Exception e) {
			System.out.println("添加信息错误");
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

	//修改密码
	@Override
	public int modifyPassword(String stu_id,String new_password) {
		int n = 0;
		Connection conn = null;
		PreparedStatement ptmt = null;
		try {
			conn = DButil.getInstance().getConnection();
			String sql = " update student set password=? where stu_id=? ";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, new_password);
			// 男的值为1，女的值为0
			ptmt.setString(2, stu_id);
			n = ptmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("修改密码失败");
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return n;
	}

	@Override
	public String getTime(String stu_id) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		try {
			conn = DButil.getInstance().getConnection();
			String sql = " select register_data from student where stu_id=? ";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, stu_id);
			ResultSet rs = ptmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			System.out.println("修改密码失败");
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "0";
	}

	//获得所有学生
	public List<Student> getAllStudent() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		
		List<Student> result = new ArrayList<Student>();	//查询结果集合
		
		try {
			conn = DButil.getInstance().getConnection();
			String sql = "select stu_id from student";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
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

				Student s = new Student(map);
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

	//查看是否完善了个人信息
	@Override
	public int judgeIfFinish(String stu_id) {
		int n = 0;
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs=null;
		try {
			conn = DButil.getInstance().getConnection();
			String sql = " select area from student where stu_id=? ";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, stu_id);
			rs = ptmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1)==null) {
					return 0;
				}
			}
		} catch (Exception e) {
			System.out.println("查询错误");
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 1;
	}

}
