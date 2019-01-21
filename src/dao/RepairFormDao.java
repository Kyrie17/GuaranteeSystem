package dao;

import java.sql.ResultSet;
import java.util.List;

import model.Pager;
import model.RepairForm;

public interface RepairFormDao {
	/**
	 * 提交报修单
	 * @param rf 报修单
	 * @return 操作几行数据
	 */
	public int submitRepairForm(RepairForm rf);
	

	/**
	 * 根据查询条件，查询报修单分页信息
	 * @param searchModel 封装查询条件
	 * @param pageNum 查询第几页数据
	 * @param pageSize 每页显示多少条记录
	 * @return 查询结果
	 */
	public Pager<RepairForm> findRepairForm(RepairForm searchModel, int pageNum,
			int pageSize);
	
	
	/**
	 * 根据查询条件查询所有符合条件的报修单（若条件为空，则为显示全部报修单）
	 */
	public List<RepairForm> getAllRepairForm(RepairForm searchModel);


	/**
	 * 获取最近几条报修信息
	 */
	public List<RepairForm> getLastestRF();
	
	
	/**
	 * 删除序号为orderNumber的保修记录
	 */
	public int removeRFInfor(String orderNumber);
	
	
	/**
	 * 修改序号为orderNumber的报修记录
	 */
	public int updateRFInfor(RepairForm searchModel);
	
	
	/**
	 * 查询序号为orderNumber报修记录的详情
	 */
	public int getDetailRFInfor(String orderNumber);
	
	/**
	 * 获取单独用户的报修信息
	 */
	public List<RepairForm> getPerInform(String username);
	public List<RepairForm> getPerInform(int orderNumber);
	
	/**
	 * 查询报修类型是？的所有维修工的名字
	 */
	public List<RepairForm> getRepairMan(int serType);
	
	
	/**
	 * 查询维修工是？的报修表单数量
	 */
	public int getRepairManNum(String repairMan);
	
	
	/**
	 * 查询没有维修工人没有确定完成的分页报修单
	 * @param pageNum 查询第几页数据
	 * @param pageSize 每页显示多少条记录
	 * @return 查询结果
	 */
	public Pager<RepairForm> findUnfinishedRF(String username, int pageNum, int pageSize);
	
	
	/**
	 * 查询维修工人已确定完成的分页报修单
	 * @param pageNum 查询第几页数据
	 * @param pageSize 每页显示多少条记录
	 * @return 查询结果
	 */
	public Pager<RepairForm> findFinishedRF(String username, int userConfirm, int pageNum, int pageSize);
	
	
	/**
	 * 查询审核状态为未审核和已审核的报修单
	 */
	public List<RepairForm> getUnfinishedRF(String username);
	
	
	/**
	 * 查询审核状态为已完成的报修单
	 */
	public List<RepairForm> getFinishedRF(String username, int userConfirm);
	
	
	/**
	 * 将报修单用户端更改为确认完成
	 */
	public int ChangeUserConfirm(int orderNumber);
	
	
	/**
	 * 根据报修工，查询报修单分页信息
	 * @param searchModel 封装查询条件
	 * @param pageNum 查询第几页数据
	 * @param pageSize 每页显示多少条记录
	 * @return 查询结果
	 */
	public Pager<RepairForm> findRFByRepairMan(String username, int pageNum, int pageSize);
	
	
	/**
	 * 查询维修工为username的报修单
	 */
	public List<RepairForm> getRFByRepairMan(String username);
	
	
	/**
	 * 改变报修单的审核状态
	 */
	public int changeJudgeState(int i, int orderNumber);
	
	
	/**
	 * 获取该学生的报修单数
	 */
	public int getSelfRFNum(String username);
}
