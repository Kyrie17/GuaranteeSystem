package model;

import java.util.Map;

public class RepairMan {
	//用户名
	private String username;
	//电话
	private String phone;
	//密码
	private String password;
	//工种
	private int major;
	
	
	public RepairMan(Map<String, Object> map){
		this.username = (String)map.get("a_id");
		this.phone = (String)map.get("a_phone");
		this.password = (String)map.get("a_password");
		this.major = (int)map.get("a_major");
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
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
	
	public int getMajor() {
		return major;
	}
	
	public void setMajor(int major) {
		this.major = major;
	}
	
	
	@Override
	public String toString() {
		return "RepairMan [username=" + username + ", phone=" + phone + ", password=" + password + ", major=" + major
				+ "]";
	}
	
}
