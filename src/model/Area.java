package model;

import java.util.Map;

public class Area {
	//编号
	private int id;
	//区域名
	private String name;
	//区域号
	private int code;
	
	public Area(Map<String, Object> map){
		this.id = (int)map.get("id");
		this.name = (String)map.get("name");
		this.code = (int)map.get("code");
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	
}
