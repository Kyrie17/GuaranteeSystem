package dao;

import java.sql.SQLException;
import java.util.List;

import model.Pager;
import model.Score;

public interface ScoreDao {

	/**
	 * 根据查询条件，查询报修单评价分页信息
	 * @param searchModel 封装查询条件
	 * @param pageNum 查询第几页数据
	 * @param pageSize 每页显示多少条记录
	 * @return 查询结果
	 */
	public Pager<Score> findScore(Score searchModel, int pageNum, int pageSize);
	
	
	/**
	 * 管理端获取所有报修表单的满意评级
	 */
	public List<Score> getAllScore(Score searchModel);
	
	
	public int judge(int r_ordernumber);
	
	
	public int insert(String addressID,String suggest,String r_ordernumber,String index,String s_id,String a_id,String now); 


	/**
	 * 维修工获取自己报修评价（分页）
	 */
	public Pager<Score> findScoreByRm(String username, int pageNum, int pageSize);
	
	
	/**
	 * 维修工获取自己报修评价
	 */
	public List<Score> getScoreByRm(String username);
	
	
	/**
	 * 维修工平均的分
	 */
	public double getAvgByRm(String username);
}
