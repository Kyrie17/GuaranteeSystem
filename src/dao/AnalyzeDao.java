package dao;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.List;

import model.NoticeInform;

public interface AnalyzeDao {
	//分别统计报修的种类总数
	public int[] AnalyzeSerType() throws SQLException, ClassNotFoundException, PropertyVetoException;
	
	//分别统计审核状态
	public int[] AnalyzeJudgeState() throws SQLException, ClassNotFoundException, PropertyVetoException;
	
	//分别报修时间
		public int[] AnalyzeSerTime() throws SQLException, ClassNotFoundException, PropertyVetoException;

	//分析维修工种类
	public int[] AnalyzeAdmin() throws SQLException, ClassNotFoundException, PropertyVetoException;
}
