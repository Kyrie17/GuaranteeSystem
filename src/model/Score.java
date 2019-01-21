package model;

import java.util.Map;

public class Score {

	//报修单序号
	private int orderNumber;
	//满意选项
	private String satisfied;
	//建议
	private String suggest;
	//对维修员工的评价
	private int score;
	//此报修单是否提交过的标记
	private int flag;
	//维修工
	private String repairMan;
	//学号
	private String username;
	//时间
	private String time;
	
	
	public Score() {
		super();
	}

	public Score(Map<String, Object> map){
		this.orderNumber = (int)map.get("r_ordernumber");
		this.satisfied = (String)map.get("satisfied");
		this.suggest = (String)map.get("suggest");
		this.score = (int)map.get("score");
		this.flag = (int)map.get("flag");
		this.repairMan = (String)map.get("repairman");
		this.username = (String)map.get("username");
		this.time = (String)map.get("time");
		
	}
	public String getRepairMan() {
		return repairMan;
	}

	public void setRepairMan(String repairMan) {
		this.repairMan = repairMan;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getFlag() {
		return flag;
	}
	
	public int getOrderNumber() {
		return orderNumber;
	}
	
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public String getSatisfied() {
		return satisfied;
	}
	
	public void setSatisfied(String satisfied) {
		this.satisfied = satisfied;
	}
	
	public String getSuggest() {
		return suggest;
	}
	
	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int isFlag() {
		return flag;
	}
	
	public void setFlag(int flag) {
		this.flag = flag;
	}
}
