package model;

public class Login {
	private String stu_id;
	private String password;
	public Login(String stu_id,String password) {
		this.stu_id=stu_id;
		this.password=password;
	}
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Login [stu_id=" + stu_id + ", password=" + password + "]";
	}
	
	
}
