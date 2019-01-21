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
import tools.DButil;

public class ShowPerInformDaoImpl implements ShowPerInformDao {

	@Override
	public List<RepairForm> getPerInform(String stu_id) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		DButil dbutil = DButil.getInstance();
		List<RepairForm> result = new ArrayList<RepairForm>(); // 查询结果集合

		String sql = "SELECT * FROM repairform where s_id=?";
		try {
			conn = dbutil.getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, stu_id);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

}
