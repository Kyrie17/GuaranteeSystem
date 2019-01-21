package dao;

import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Student;

public interface StudentDao {
	//验证用户名和密码是否正确
	public int loginJudge(String stu_id,String password)throws SQLException, PropertyVetoException, ClassNotFoundException;
	//添加注册信息
	public int addStudent(String stu_id, String phone,  String password, Boolean boo,String timestamp) throws SQLException, PropertyVetoException, ClassNotFoundException;	
	//核验学号是否已被注册
	public int judgeStudent(String stu_id) throws SQLException, PropertyVetoException, ClassNotFoundException;
	//完善用户个人信息
	public int improveInform(String stu_id,String room,String sex,String area,String build);
	//检查旧密码是否输入正确
	public int judgePassword(String old_password,String stu_id);
	//修改密码
	public int modifyPassword(String stu_id,String new_password);
	//获得对应学号的时间戳
	public String getTime(String stu_id);
	//获得所有学生
	public List<Student> getAllStudent();
	//查看是否完善了个人信息
	public int judgeIfFinish(String stu_id);
	
}
