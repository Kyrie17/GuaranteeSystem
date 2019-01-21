package model;

import java.util.Map;

public class Build {
	//编号
	private int id;
	//栋数
	private String building;
	//区域号
	private int code;
	
	public Build(Map<String, Object> map){
		this.id = (int)map.get("id");
		this.building = (String)map.get("building");
		this.code = (int)map.get("code");
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	
}
