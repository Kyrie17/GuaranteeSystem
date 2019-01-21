package dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tools.DButil;

public class AnalyzeDaoImpl implements AnalyzeDao {

	@Override
	public int[] AnalyzeSerType() throws SQLException, ClassNotFoundException, PropertyVetoException {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		DButil dbutil = DButil.getInstance();
		conn=dbutil.getConnection();
		int[] analyze=new int[4];
		try {
		String sql1="select count(r_sertype) from repairform where r_sertype=1 ";
		String sql2="select count(r_sertype) from repairform where r_sertype=2 ";
		String sql3="select count(r_sertype) from repairform where r_sertype=3 ";
		String sql4="select count(r_sertype) from repairform where r_sertype=4 ";
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
		return analyze;
	}
	
	@Override
	public int[] AnalyzeJudgeState() throws SQLException, ClassNotFoundException, PropertyVetoException {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		DButil dbutil = DButil.getInstance();
		conn=dbutil.getConnection();
		int[] analyze=new int[3];
		try {
		String sql1="select count(r_judgestate) from repairform where r_judgestate=-1 ";
		String sql2="select count(r_judgestate) from repairform where r_judgestate=1 ";
		String sql3="select count(r_judgestate) from repairform where r_judgestate=2 ";
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
		return analyze;
	}
	
	@Override
	public int[] AnalyzeSerTime() throws SQLException, ClassNotFoundException, PropertyVetoException {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		DButil dbutil = DButil.getInstance();
		conn=dbutil.getConnection();
		int[] analyze=new int[6];
		try {
		String sql1="select  count(r_sertime) from repairform where SUBSTRING(r_sertime, 1,7)='2018-06' ";
		String sql2="select count(r_sertime) from repairform where SUBSTRING(r_sertime, 1,7)='2018-07' ";
		String sql3="select count(r_sertime) from repairform where SUBSTRING(r_sertime, 1,7)='2018-08' ";
		String sql4="select count(r_sertime) from repairform where SUBSTRING(r_sertime, 1,7)='2018-09' ";
		String sql5="select count(r_sertime) from repairform where SUBSTRING(r_sertime, 1,7)='2018-10'";
		String sql6="select count(r_sertime) from repairform where SUBSTRING(r_sertime, 1,7)='2018-11' ";
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
		return analyze;
	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException, PropertyVetoException {
		AnalyzeDaoImpl a=new AnalyzeDaoImpl();
		a.AnalyzeSerType();
	}
	
	
	@Override
	public int[] AnalyzeAdmin() throws SQLException, ClassNotFoundException, PropertyVetoException {
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		DButil dbutil = DButil.getInstance();
		conn=dbutil.getConnection();
		int[] analyze=new int[4];
		try {
		String sql1="select count(a_major) from admin where a_major=1 ";
		String sql2="select count(a_major) from admin where a_major=2 ";
		String sql3="select count(a_major) from admin where a_major=3 ";
		String sql4="select count(a_major) from admin where a_major=4 ";
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
		return analyze;
	}

}
