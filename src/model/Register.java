package model;

public class Register {
	private String stu_id;
	private String phone;
	private String mess;
	private String password;
	private String backNews;
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
	public String getMess() {
		return mess;
	}
	public void setMess(String mess) {
		this.mess = mess;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBackNews() {
		return backNews;
	}
	public void setBackNews(String backNews) {
		this.backNews = backNews;
	}
	@Override
	public String toString() {
		return "Register [stu_id=" + stu_id + ", phone=" + phone + ", mess=" + mess + ", password=" + password
				+ ", backNews=" + backNews + "]";
	}
	
	
}
