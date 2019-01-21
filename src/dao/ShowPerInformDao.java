package dao;

import java.util.List;

import model.RepairForm;

public interface ShowPerInformDao {
	//获取单独学生的报修信息
	public List<RepairForm> getPerInform(String stu_id);
}
