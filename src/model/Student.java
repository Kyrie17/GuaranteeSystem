package model;

import java.util.Map;

public class Student {

	//学号
	private String stu_id;
	//电话
	private String phone;
	//密码
	private String password;
	//南北苑
	private String area;
	//楼栋
	private String build;
	//房间号
	private String room;
	//性别
	private String sex;
	//注册时间
	private String registerData;
	
	public Student(Map<String, Object> map) {
		this.stu_id = (String)map.get("stu_id");
	}
	
	public Student() {
		super();
	}
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getBuild() {
		return build;
	}
	public void setBuild(String build) {
		this.build = build;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getRegisterData() {
		return registerData;
	}
	public void setRegisterData(String registerData) {
		this.registerData = registerData;
	}
}
