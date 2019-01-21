package dao;

import java.util.List;

import model.RepairMan;

public interface RepairManDao {

	//获取某一报修类型的工作人员
	public List<RepairMan> getRepairMan(int major);

	public List<RepairMan> getAllRepairMan();
}
