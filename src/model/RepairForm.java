package model;

import java.util.Map;

/**
 * Descrition 封装报修表
 * 
 * @author 11300
 *
 */
public class RepairForm {
	//序号
	private int orderNumber;
	//用户名
	private String username;
	//电话
	private String phone;
	//报修类别
	private int serType;
	//故障地址ַ
	private String serAdd;
	//报修内容
	private String serInform;
	//可接受维修时间
	private String serTime;
	//详细时间（上下午）
	private String detailTime;
	//审核状态
	private int judgeState;
	//图片路径
	private String file_path;
	//维修工
	private String repairMan;
	//维修工确定时间
	private String submitTime;
	//用户是否确定订单完成状况
	private int userConfirm;
	

	public RepairForm() {
		
	}
	
	public RepairForm(Map<String, Object> map){
		this.orderNumber = (int)map.get("r_ordernumber");
		this.username = (String)map.get("s_id");
		this.phone = (String)map.get("s_phone");
		this.serType = (int)map.get("r_sertype");
		this.serAdd = (String)map.get("r_seradd");
		this.serInform = (String)map.get("r_serinform");
		this.serTime = (String)map.get("r_sertime");
		this.judgeState = (int)map.get("r_judgestate");
		this.file_path = (String)map.get("r_filepath");
		this.repairMan = (String)map.get("a_id");
		this.detailTime = (String)map.get("r_detailtime");
		this.submitTime = (String)map.get("r_submittime");
		this.userConfirm = (int)map.get("r_userconfirm");
	}
	
	public String getRepairMan() {
		return repairMan;
	}

	public void setRepairMan(String repairMan) {
		this.repairMan = repairMan;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public int getUserConfirm() {
		return userConfirm;
	}

	public void setUserConfirm(int userConfirm) {
		this.userConfirm = userConfirm;
	}

	public RepairForm(int i, Map<String, Object> map) {
		this.repairMan = (String)map.get("a_id");
	}
	
	public String getDetailTime() {
		return detailTime;
	}

	public void setDetailTime(String detailTime) {
		this.detailTime = detailTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getOrderNumber() {
		return orderNumber;
	}
	
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getSerType() {
		return serType;
	}
	
	public void setSerType(int serType) {
		this.serType = serType;
	}
	
	public String getSerAdd() {
		return serAdd;
	}
	
	public void setSerAdd(String serAdd) {
		this.serAdd = serAdd;
	}
	
	public String getSerInform() {
		return serInform;
	}
	
	public void setSerInform(String serInform) {
		this.serInform = serInform;
	}
	
	public String getSerTime() {
		return serTime;
	}
	
	public void setSerTime(String serTime) {
		this.serTime = serTime;
	}

	public int getJudgeState() {
		return judgeState;
	}

	public void setJudgeState(int judgeState) {
		this.judgeState = judgeState;
	}

	@Override
	public String toString() {
		return "RepairForm [orderNumber=" + orderNumber + ", username=" + username + ", phone=" + phone + ", serType="
				+ serType + ", serAdd=" + serAdd + ", serInform=" + serInform + ", serTime=" + serTime + ", detailTime="
				+ detailTime + ", judgeState=" + judgeState + ", file_path=" + file_path + ", repairMan=" + repairMan
				+ ", submitTime=" + submitTime + ", userConfirm=" + userConfirm + "]";
	}

}
