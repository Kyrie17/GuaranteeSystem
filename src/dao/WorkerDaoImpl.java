package dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tools.DButil;

public class WorkerDaoImpl implements WorkerDao {

	@Override
	public int WorkerloginJudge(String username, String password) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		int n = 0;
		try {
			conn = DButil.getInstance().getConnection();
			ResultSet rs;
			String sql = " select worker_id from Worker where worker_id = ? and worker_password =? ";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, username);
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
}
