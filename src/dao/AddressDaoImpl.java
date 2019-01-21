package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Area;
import model.Build;
import model.RepairMan;
import tools.DButil;

public class AddressDaoImpl implements AddressDao {

	@Override
	public List<Area> getAllArea() {
		Connection conn = null;
		PreparedStatement ptmt = null;
		List<Area> result = new ArrayList<Area>(); // 查询结果集合
		try {
			conn = DButil.getInstance().getConnection();
			ResultSet rs;
			String sql = " select * from area ";
			ptmt = conn.prepareStatement(sql);
			rs = ptmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int cols_len = metaData.getColumnCount();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = metaData.getColumnName(i + 1);
					Object cols_value = rs.getObject(cols_name);
					if (cols_value == null) {
						cols_value = "";
					}
					map.put(cols_name, cols_value);
				}
				Area s = new Area(map);
				result.add(s);
			}
			
		} catch (Exception e) {
			System.out.println("获取失败");
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

	@Override
	public List<Build> getAllBuildByArea(int code) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		List<Build> result = new ArrayList<Build>(); // 查询结果集合
		try {
			conn = DButil.getInstance().getConnection();
			ResultSet rs;
			String sql = " select * from build where code =? ";
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, code);
			rs = ptmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int cols_len = metaData.getColumnCount();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = metaData.getColumnName(i + 1);
					Object cols_value = rs.getObject(cols_name);
					if (cols_value == null) {
						cols_value = "";
					}
					map.put(cols_name, cols_value);
				}
				Build s = new Build(map);
				result.add(s);
			}
			
		} catch (Exception e) {
			System.out.println("获取失败");
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
